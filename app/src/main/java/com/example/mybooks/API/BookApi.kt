package com.example.mybooks.API

import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    companion object{
        fun instance(): BookApi{
          val retro =   Retrofit.Builder()
                .baseUrl("http://openlibrary.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retro.create(BookApi::class.java)
        }
    }

    @GET("search.json/")
    fun getBooks(@Query("q") q: String?, @Query("page") page: Int): io.reactivex.Single<retrofit2.Response<BookResponse>>
}