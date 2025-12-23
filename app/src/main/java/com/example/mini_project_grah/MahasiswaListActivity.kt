package com.example.mini_project_grah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_project_grah.databinding.ActivityMahasiswaListBinding

class MahasiswaListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMahasiswaListBinding
    private lateinit var adapter: MahasiswaAdapter
    private val listMahasiswa = ArrayList<mahasiswa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMahasiswaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MahasiswaAdapter(listMahasiswa)

        binding.recMahasiswa.apply {
            layoutManager = LinearLayoutManager(this@MahasiswaListActivity)
            setHasFixedSize(true)
            adapter = this@MahasiswaListActivity.adapter
        }

        loadMahasiswa() // kalau mau ambil dari DB
    }

    private fun loadMahasiswa() {
        // BISA COPY dari HomeFragment (Volley JSON)
        // listMahasiswa.add(...)
        // adapter.notifyDataSetChanged()
    }
}