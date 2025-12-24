package com.example.mini_project_grah

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.mini_project_grah.databinding.CardMahasiswaBinding

class MahasiswaAdapter(
    private val listMahasiswa: ArrayList<mahasiswa>
) : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    inner class MahasiswaViewHolder(val binding: CardMahasiswaBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val binding = CardMahasiswaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MahasiswaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mhs = listMahasiswa[position]

        holder.binding.txtNama.text = mhs.nama
        holder.binding.txtNRP.text = mhs.nrp
        holder.binding.txtJurusan.text = mhs.jurusan

        val imageUrl = "http://10.0.2.2/project_nmp/${mhs.photoUrl}"

        val imageRequest = ImageRequest(
            imageUrl,
            { bitmap: Bitmap ->
                holder.binding.imgMahasiswa.setImageBitmap(bitmap)
            },
            0,
            0,
            null,
            { error ->
                error.printStackTrace()
            }
        )

        Volley.newRequestQueue(holder.itemView.context).add(imageRequest)

        holder.binding.cardView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("nrp", mhs.nrp)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listMahasiswa.size
}
