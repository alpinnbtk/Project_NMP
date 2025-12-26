package com.example.mini_project_grah

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mini_project_grah.databinding.FragmentMyFriendBinding
import com.example.mini_project_grah.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding di sini sebelum digunakan di tempat lain
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Pasang click listener di onViewCreated karena view sudah pasti siap
        binding.btnReset.setOnClickListener {
            resetFriends()
        }

        // Listener untuk Switch Night Mode
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Aktifkan Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Kembali ke Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
    private fun resetFriends() {
        val url = "http://10.0.2.2/project_nmp/reset_friends.php"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val result = response.getString("result")
                    if (result == "SUCCESS") {
                        Toast.makeText(requireContext(), "Data berhasil direset!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("API", "Error JSON: ${e.message}")
                }
            },
            { error ->
                Log.e("API", "Gagal koneksi ke server: ${error.message}")
                Toast.makeText(requireContext(), "Gagal reset data", Toast.LENGTH_SHORT).show()
            }
        )

        // Tambahkan ke antrean Volley
        Volley.newRequestQueue(requireContext()).add(request)
    }


}