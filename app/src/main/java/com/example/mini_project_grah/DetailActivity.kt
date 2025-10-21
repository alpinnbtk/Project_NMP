package com.example.mini_project_grah

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val menuItems = listOf("About Me", "My Course", "My Experience")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            menuItems
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTampilan.adapter = adapter

        val index = intent.getIntExtra("question_index", 0)
        binding.txtNameDetail.setText(dataMahasiswa.arrMahasiswa[index].nama)
        binding.txtNRPDetail.setText("NRP : " + dataMahasiswa.arrMahasiswa[index].nrp)
        binding.txtJurusanDetail.setText("Jurusan : " + dataMahasiswa.arrMahasiswa[index].jurusan)

        if (dataMahasiswa.arrMahasiswa[index].isFriend) {
            binding.btnRequest.isEnabled = false
            binding.btnRequest.text = "Friend Added"
        }

        binding.imgDetail.setImageResource(dataMahasiswa.arrMahasiswa[index].imageId)
        if(dataMahasiswa.arrMahasiswa[index].jurusan=="DSAI") {
            binding.rdoDSAI.isChecked = true
            binding.rdoNCS.isEnabled = false
            binding.rdoDMT.isEnabled = false
            binding.rdoGD.isEnabled = false
            binding.rdoIMES.isEnabled = false
        } else if(dataMahasiswa.arrMahasiswa[index].jurusan=="NCS")  {
            binding.rdoNCS.isChecked = true
            binding.rdoDSAI.isEnabled = false
            binding.rdoDMT.isEnabled = false
            binding.rdoGD.isEnabled = false
            binding.rdoIMES.isEnabled = false
        }
        else if(dataMahasiswa.arrMahasiswa[index].jurusan=="IMES")  {
            binding.rdoIMES.isChecked = true
            binding.rdoNCS.isEnabled = false
            binding.rdoDMT.isEnabled = false
            binding.rdoGD.isEnabled = false
            binding.rdoDSAI.isEnabled = false
        }
        else if(dataMahasiswa.arrMahasiswa[index].jurusan=="DMT")  {
            binding.rdoDMT.isChecked = true
            binding.rdoNCS.isEnabled = false
            binding.rdoDSAI.isEnabled = false
            binding.rdoGD.isEnabled = false
            binding.rdoIMES.isEnabled = false
        }
        else  {
            binding.rdoGD.isChecked = true
            binding.rdoNCS.isEnabled = false
            binding.rdoDMT.isEnabled = false
            binding.rdoDSAI.isEnabled = false
            binding.rdoIMES.isEnabled = false
        }

        binding.spinnerTampilan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> binding.txtDeskripsiDetail.text = dataMahasiswa.arrMahasiswa[index].aboutMe
                    1 -> binding.txtDeskripsiDetail.text = dataMahasiswa.arrMahasiswa[index].myCourse
                    2 -> binding.txtDeskripsiDetail.text = dataMahasiswa.arrMahasiswa[index].myExperience
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        binding.btnRequest.setOnClickListener {
            if (!dataMahasiswa.arrMahasiswa[index].isFriend) {
                FriendData.totalFriend++
                dataMahasiswa.arrMahasiswa[index].isFriend = true

                AlertDialog.Builder(this)
                    .setTitle("Friend Request")
                    .setMessage(
                        "Sukses menambah " + dataMahasiswa.arrMahasiswa[index].nama + " sebagai friend.\n" +
                                "Jumlah friend anda saat ini: ${FriendData.totalFriend}"
                    )
                    .setPositiveButton("OK", null)
                    .show()

                binding.btnRequest.isEnabled = false
                binding.btnRequest.text = "Friend Added"
            }
        }

    }





}