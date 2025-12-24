package com.example.mini_project_grah

import android.os.Bundle
import android.util.Log
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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mini_project_grah.databinding.ActivityDetailBinding
import com.example.mini_project_grah.databinding.ActivityMahasiswaListBinding
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import org.json.JSONObject


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nrp = intent.getStringExtra("nrp") ?: return
        loadDetailMahasiswa(nrp)

        binding.btnBack.setOnClickListener { finish() }

        val menuItems = listOf("About Me", "My Course", "My Experience")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            menuItems
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTampilan.adapter = spinnerAdapter

        binding.btnRequest.setOnClickListener {
            requestFriend()
        }
    }

    private fun setJurusanEnabled(selected: String) {
        binding.rdoDSAI.isEnabled = selected == "DSAI"
        binding.rdoNCS.isEnabled  = selected == "NCS"
        binding.rdoIMES.isEnabled = selected == "IMES"
        binding.rdoDMT.isEnabled  = selected == "DMT"
        binding.rdoGD.isEnabled   = selected == "GD"
    }


    private fun loadDetailMahasiswa(nrp: String) {
        Log.e("NRP", nrp ?: "NRP NULL")
        val url = "http://10.0.2.2/project_nmp/get_student_id.php?nrp=$nrp"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.getString("result") == "SUCCESS") {
                    val dataArray = response.getJSONArray("data")
                    val obj = dataArray.getJSONObject(0)

                    val photoPath = obj.getString("photo_url")
                    val imageUrl = "http://10.0.2.2/project_nmp/$photoPath"

                    val imageRequest = ImageRequest(
                        imageUrl,
                        { bitmap ->
                            binding.imgDetail.setImageBitmap(bitmap)
                        },
                        0,
                        0,
                        ImageView.ScaleType.CENTER_CROP,
                        null,
                        { error ->
                            Log.e("IMAGE", "Gagal load gambar", error)
                        }
                    )

                    Volley.newRequestQueue(this).add(imageRequest)
                    binding.txtNameDetail.text = obj.getString("nama")
                    binding.txtNRPDetail.text = "NRP : ${obj.getString("nrp")}"
                    binding.txtJurusanDetail.text = "Jurusan : ${obj.getString("program")}"

                    val program = obj.getString("program")
                    when (program) {
                        "DSAI" -> binding.rdoDSAI.isChecked = true
                        "NCS" -> binding.rdoNCS.isChecked = true
                        "IMES" -> binding.rdoIMES.isChecked = true
                        "DMT" -> binding.rdoDMT.isChecked = true
                        "GD" -> binding.rdoGD.isChecked = true
                    }
                    setJurusanEnabled(program)

                    val aboutMe = obj.getString("about_me")
                    binding.txtDeskripsiDetail.text = aboutMe
                    val myCourse = obj.getString("my_course")
                    val myExperience = obj.getString("my_experiences")

                    binding.spinnerTampilan.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                binding.txtDeskripsiDetail.text = when (position) {
                                    0 -> aboutMe
                                    1 -> myCourse
                                    else -> myExperience
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                }
            },
            { error ->
                Log.e("API", "Gagal load detail", error)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    private fun requestFriend() {
        val nrp = binding.txtNRPDetail.text.toString()
            .replace("NRP : ", "")

        val url = "http://10.0.2.2/project_nmp/insert_friend.php?nrp=$nrp"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val result = response.optString("result")
                val totalFriend = response.optInt("total_friend", 0)

                when (result) {
                    "SUCCESS" -> {
                        binding.btnRequest.isEnabled = false
                        showDialog(
                            "Friend Request",
                            "Berhasil menambahkan ${binding.txtNameDetail.text} sebagai friend.\nTotal Friend: $totalFriend"
                        )
                    }

                    "ALREADY" -> {
                        binding.btnRequest.isEnabled = false
                        showDialog(
                            "Info",
                            "Sudah menjadi friend.\nTotal Friend: $totalFriend"
                        )
                    }

                    else -> {
                        showDialog("Error", response.optString("message", "Terjadi kesalahan"))
                    }
                }
            },
            { error ->
                Log.e("FRIEND", "Error", error)
                showDialog("Error", "Gagal menghubungi server")
            }
        )

        Volley.newRequestQueue(this).add(request)
    }



    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }


}
