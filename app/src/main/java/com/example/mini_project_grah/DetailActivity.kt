package com.example.mini_project_grah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mini_project_grah.databinding.ActivityDetailBinding
import com.example.mini_project_grah.databinding.ActivityMahasiswaListBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("question_index", 0)
        binding.txtNameDetail.setText("Nama : " + dataMahasiswa.arrMahasiswa[index].nama)
        binding.txtNRPDetail.setText("NRP : " + dataMahasiswa.arrMahasiswa[index].nrp)
        binding.txtJurusanDetail.setText("Jurusan : " + dataMahasiswa.arrMahasiswa[index].jurusan)

        binding.imgDetail.setImageResource(dataMahasiswa.arrMahasiswa[index].imageId)
        if(dataMahasiswa.arrMahasiswa[index].jurusan=="DSAI") {
            binding.rdoDSAI.isChecked = true
        } else if(dataMahasiswa.arrMahasiswa[index].jurusan=="NCS")  {
            binding.rdoNCS.isChecked = true
        }
        else if(dataMahasiswa.arrMahasiswa[index].jurusan=="IMES")  {
            binding.rdoIMES.isChecked = true
        }
        else if(dataMahasiswa.arrMahasiswa[index].jurusan=="NCS")  {
            binding.rdoDMT.isChecked = true
        }
        else  {
            binding.rdoGD.isChecked = true
        }





    }
}