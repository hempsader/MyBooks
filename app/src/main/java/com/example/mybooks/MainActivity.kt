package com.example.mybooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookResponse
import com.example.mybooks.API.BookTransform
import com.example.mybooks.view.FragmentAlreadyRead
import com.example.mybooks.view.FragmentDiscovered
import com.example.mybooks.view.FragmentFavourites
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.schedulers.Schedulers

private const val NUM_PAGES  = 3
class MainActivity : AppCompatActivity() {

    private lateinit var viePager: ViewPager2

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viePager = findViewById(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viePager.adapter = object: FragmentStateAdapter(this){
            override fun getItemCount(): Int = NUM_PAGES
            override fun createFragment(position: Int): Fragment {
                return  when(position){
                    0 -> FragmentDiscovered()
                    1 -> FragmentFavourites()
                    2 -> FragmentAlreadyRead()
                    else -> FragmentDiscovered()
                }
            }

        }
        TabLayoutMediator(tabLayout,viePager){tab, position ->
            tab.text = "Object ${position+1}"
        }.attach()

    }
}