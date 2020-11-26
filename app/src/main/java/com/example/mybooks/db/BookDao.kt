package com.example.mybooks.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.mybooks.API.BookTransform
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface BookDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun addBooks(books: List<BookTransform.BookPojo>): Completable

    @Query("delete  from books")
    fun deleteAll(): Completable

    @Query("select * from books")
    fun getAllBooks(): DataSource.Factory<Int,BookTransform.BookPojo>

    @Query("select * from books where favourite = 1 order by bookTitle")
    fun favouriteStream(): DataSource.Factory<Int, BookTransform.BookPojo>

    @Query("select * from books where alreadyRead = 1 order by alreadyRead")
    fun alreadtRead(): DataSource.Factory<Int, BookTransform.BookPojo>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBook(book: BookTransform.BookPojo): Single<Int>


}