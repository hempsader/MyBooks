package com.example.mybooks.db

import androidx.room.*
import com.example.mybooks.API.BookTransform
import io.reactivex.Completable
import io.reactivex.Observable


@Dao
interface BookDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun addBooks(books: List<BookTransform.BookPojo>): Completable

    @Query("delete  from books")
    fun deleteAll(): Completable

    @Query("select * from books")
    fun getAllBooks(): Observable<List<BookTransform.BookPojo>>


}