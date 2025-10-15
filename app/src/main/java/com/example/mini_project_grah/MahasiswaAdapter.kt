package com.example.mini_project_grah

import android.content.Intent
import com.example.mini_project_grah.databinding.CardMahasiswaBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


class MahasiswaAdapter() : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MahasiswaViewHolder {
        val binding = CardMahasiswaBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return MahasiswaViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: MahasiswaViewHolder,
        position: Int
    ) {
        holder.binding.imgMahasiswa.setImageResource(dataMahasiswa.arrMahasiswa[position].imageId)
        holder.binding.txtNama.text = dataMahasiswa.arrMahasiswa[position].nama
        holder.binding.txtNRP.text =dataMahasiswa.arrMahasiswa[position].nrp
        holder.binding.txtJurusan.text = dataMahasiswa.arrMahasiswa[position].jurusan

        holder.binding.cardView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("question_index", position)
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return dataMahasiswa.arrMahasiswa.size
    }

    class MahasiswaViewHolder(val binding: CardMahasiswaBinding):RecyclerView.ViewHolder(binding.root)





}