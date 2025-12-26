    package com.example.mini_project_grah

    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.android.volley.Request
    import com.android.volley.toolbox.JsonObjectRequest
    import com.android.volley.toolbox.Volley
    import com.example.mini_project_grah.databinding.FragmentMyFriendBinding

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"

    /**
     * A simple [Fragment] subclass.
     * Use the [MyFriendFragment.newInstance] factory method to
     * create an instance of this fragment.
     */
    class MyFriendFragment : Fragment() {


        private lateinit var binding: FragmentMyFriendBinding
        private lateinit var adapter: MahasiswaAdapter
        private val listMahasiswa = ArrayList<mahasiswa>()




        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
    //        return inflater.inflate(R.layout.fragment_my_friend, container, false)
            binding = FragmentMyFriendBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            adapter = MahasiswaAdapter(listMahasiswa, true)

            binding.recFriends.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = this@MyFriendFragment.adapter
            }


            loadFriends()
        }


        private fun loadFriends() {
            val url = "http://10.0.2.2/project_nmp/get_friends.php"

            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        if (response.has("data")) {
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
                            adapter.notifyDataSetChanged()
                        } else {
                            listMahasiswa.clear()
                            adapter.notifyDataSetChanged()
                            Log.d("DATA", "Data kosong setelah reset")
                        }
                    } catch (e: Exception) {
                        Log.e("API", "Error parsing JSON: ${e.message}")
                    }
                },
                { error ->
                    Log.e("API", "Gagal ambil data", error)
                }
            )
            Volley.newRequestQueue(requireContext()).add(request)
        }
    }