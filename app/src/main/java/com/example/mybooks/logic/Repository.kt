package com.example.mybooks.logic

import android.content.Context
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookTransform
import com.example.mybooks.db.BookDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository (private val context: Context){


    private val bookDb = BookDatabase.getInstance(context)

    private val dao = bookDb?.bookDao()


    fun insertBooks(books: List<BookTransform.BookPojo>): Completable? = dao?.addBooks(books)?.subscribeOn(Schedulers.io())

    fun deleteBooks(): Completable? = dao?.deleteAll()

    fun getAllBooks(): Observable<List<BookTransform.BookPojo>>? = dao?.getAllBooks()

    fun bookApi(title: String? = ""): Single<List<BookTransform.BookPojo>>? {
        return BookApi.instance().getBooks(title)
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body()?.let { it1 -> BookTransform.transform(it1) }
            }
    }

    companion object{
        @Volatile
        private var instance: Repository? = null

        fun getInstance(context: Context): Repository?{
            return instance ?: synchronized(this){
                val INSTANCE = Repository(context)
                instance = INSTANCE
                instance
            }
        }
    }
}