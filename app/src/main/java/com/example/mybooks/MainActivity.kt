package com.example.mybooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookResponse
import com.example.mybooks.API.BookTransform
import com.example.mybooks.view.FragmentDiscovered
import io.reactivex.rxjava3.schedulers.Schedulers

private const val NUM_PAGES  = 3
class MainActivity : FragmentActivity() {

    private lateinit var viePager: ViewPager2

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viePager = findViewById(R.id.pager)
        val pagerAdapter = BookPagerAdapter(this)
        viePager.adapter = pagerAdapter
    }



    inner class BookPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
          return  FragmentDiscovered()
        }

    }
}