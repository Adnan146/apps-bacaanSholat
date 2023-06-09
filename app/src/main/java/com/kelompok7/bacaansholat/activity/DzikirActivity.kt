package com.kelompok7.bacaansholat.activity

import android.app.Activity
import android.graphics.Color
import android.os.Build

import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import android.os.Bundle
import com.kelompok7.bacaansholat.R
import com.kelompok7.bacaansholat.adapter.DzikirAdapter
import com.kelompok7.bacaansholat.model.ModelDataDzikir

import kotlinx.android.synthetic.main.activity_bacaan_shalat.*
import kotlinx.android.synthetic.main.activity_bacaan_shalat.toolbar
import kotlinx.android.synthetic.main.activity_dzikir.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class DzikirActivity : AppCompatActivity() {
    var dzikirAdapter: DzikirAdapter? = null
    var modelDzikir: MutableList<ModelDataDzikir> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dzikir)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        toolbar.setTitle(null)
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dzikirAdapter = DzikirAdapter(modelDzikir)

        rvdzikir.setLayoutManager(LinearLayoutManager(this))
        rvdzikir.setHasFixedSize(true)
        rvdzikir.setAdapter(dzikirAdapter)

        getDataDzikir()
    }

    private fun getDataDzikir() {
        try {
            val stream = assets.open("dzikir.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strResponse = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonArray = JSONArray(strResponse)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val dzikirModel = ModelDataDzikir()
                    dzikirModel.arabic = jsonObject.getString("arabic")
                    dzikirModel.latin = jsonObject.getString("latin")
                    dzikirModel.Terjemahan = jsonObject.getString("Terjemahan")
                    modelDzikir.add(dzikirModel)
                }
                dzikirAdapter?.notifyDataSetChanged()
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