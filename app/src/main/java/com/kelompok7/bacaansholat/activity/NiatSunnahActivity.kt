package com.kelompok7.bacaansholat.activity
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok7.bacaansholat.R

import com.kelompok7.bacaansholat.adapter.NiatSunnahAdapter
import com.kelompok7.bacaansholat.model.ModelBacaan
import kotlinx.android.synthetic.main.activity_niat_shalat.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class NiatSunnahActivity : AppCompatActivity() {
    var niatSunnahAdapter: NiatSunnahAdapter? = null
    var modelBacaan: MutableList<ModelBacaan> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_niat_sunnah)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        toolbar.setTitle(null)
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        niatSunnahAdapter = NiatSunnahAdapter(modelBacaan)

        rvNiatShalat.setLayoutManager(LinearLayoutManager(this))
        rvNiatShalat.setHasFixedSize(true)
        rvNiatShalat.setAdapter(niatSunnahAdapter)

        getDataNiatShalat()
    }

    private fun getDataNiatShalat() {
        try {
            val stream = assets.open("niatsunnah.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strResponse = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonArray = JSONArray(strResponse)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val dataModel = ModelBacaan()

                    dataModel.name = jsonObject.getString("name")
                    dataModel.arabic = jsonObject.getString("arabic")
                    dataModel.latin = jsonObject.getString("latin")
                    dataModel.terjemahan = jsonObject.getString("terjemahan")
                    modelBacaan.add(dataModel)
                }
                niatSunnahAdapter?.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

}