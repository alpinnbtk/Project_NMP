package com.example.mini_project_grah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mini_project_grah.databinding.ActivityMainBinding
import com.example.mini_project_grah.databinding.CardMahasiswaBinding
import kotlin.collections.get

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    val fragments: ArrayList<androidx.fragment.app.Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        fragments.add(HomeFragment())
        fragments.add(MyFriendFragment())
        fragments.add(SettingFragment())

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
            }
        })

        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.itemHome -> 0
                R.id.itemMyFriend -> 1
                R.id.itemSetting -> 2
                else -> 0 // default to home
            }
            true
        }



    }
}