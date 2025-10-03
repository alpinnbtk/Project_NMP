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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mahasiswa_list)

        binding = ActivityMahasiswaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recMahasiswa.layoutManager = LinearLayoutManager(this)
        binding.recMahasiswa.setHasFixedSize(true)
        binding.recMahasiswa.adapter = MahasiswaAdapter()

    }
}