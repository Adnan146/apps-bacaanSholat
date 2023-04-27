package com.kelompok7.bacaansholat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok7.bacaansholat.R
import com.kelompok7.bacaansholat.model.ModelDataDzikir
import kotlinx.android.synthetic.main.list_bacaan_shalat.view.*

class DzikirAdapter(private val modelDzikir: List<ModelDataDzikir>):
    RecyclerView.Adapter<DzikirAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_dzikir, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, i: Int) {
        val dzikirModel = modelDzikir[i]
        listViewHolder.txtArabic.text = dzikirModel.arabic
        listViewHolder.txtLatin.text = dzikirModel.latin
        listViewHolder.txtTerjemahan.text = dzikirModel.Terjemahan
    }

    override fun getItemCount(): Int {
        return modelDzikir.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtArabic: TextView
        var txtLatin: TextView
        var txtTerjemahan: TextView

        init {

            txtArabic = itemView.txtArabic
            txtLatin = itemView.txtLatin
            txtTerjemahan = itemView.txtTerjemahan
        }
    }
}