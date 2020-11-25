package com.example.mybooks.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybooks.API.BookTransform

@Database(entities = [BookTransform.BookPojo::class], version = 2, exportSchema = false)
abstract class BookDatabase : RoomDatabase(){
    abstract fun bookDao(): BookDao

    companion object{
        @Volatile
        private var instance: BookDatabase? = null


         fun getInstance(context: Context): BookDatabase? {
            return instance ?: synchronized(this){
                val INSTANCE = Room.databaseBuilder(context,BookDatabase::class.java,"books_database").build()
                instance = INSTANCE
                instance
            }
        }
    }
}