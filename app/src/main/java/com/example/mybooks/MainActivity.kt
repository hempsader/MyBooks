package com.example.mybooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mybooks.API.BookApi
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BookApi.instance()
            .getBooks("Lord of the rings")
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .doOnError {
                Log.d("aa", it.message.toString())
            }
            .map {
                it.body()
            }
            .subscribe { it->
                it?.books?.forEach {
                    Log.d("aa", "${it.title_suggest} - ${if(it.author_name?.get(0) != null) it.author_name[0] else " "} -  ${it.publishers?.get(0)} - ${it.subjects?.get(0)}")
                }
            }
    }
}