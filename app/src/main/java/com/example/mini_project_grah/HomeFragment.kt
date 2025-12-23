package com.example.mini_project_grah

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mini_project_grah.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: MahasiswaAdapter
    private val listMahasiswa = ArrayList<mahasiswa>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MahasiswaAdapter(listMahasiswa)

        binding.recMahasiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@HomeFragment.adapter
        }

        loadMahasiswa()
    }

    private fun loadMahasiswa() {
        val url = "http://10.0.2.2/project_nmp/get_all_student.php"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val data = response.getJSONArray("data")
                listMahasiswa.clear()

                for (i in 0 until data.length()) {
                    val obj = data.getJSONObject(i)
                    listMahasiswa.add(
                        mahasiswa(
                            obj.getString("nama"),
                            obj.getString("nrp"),
                            obj.getString("program"),   
                            obj.getString("photo_url")
                        )
                    )
                }

                Log.d("DATA", "Jumlah mahasiswa: ${listMahasiswa.size}")
                adapter.notifyDataSetChanged()
            },
            { error ->
                Log.e("API", "Gagal ambil data", error)
            }
        )

        Volley.newRequestQueue(requireContext()).add(request)
    }
}
