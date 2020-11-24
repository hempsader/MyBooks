package com.example.mybooks.API

import androidx.room.Entity
import androidx.room.PrimaryKey


class BookTransform {
    @Entity(tableName = "books")
    data class BookPojo(@PrimaryKey val id: Long = 0, val bookTitle: String?, val author: String?, val publisher: String?, val subject: String?)
    companion object{
        fun transform(response: BookResponse): List<BookPojo>{
            var author = "Unknown Author"
            var bookTitle = "Unknown Title"
            var publisher = "Unknown Publisher"
            var subject = "Unknown Subject"
            var lastModified: Long = 0
            val listBooks = mutableListOf<BookPojo>()
            response.books
                ?.map {
                    if(it.author_name != null){
                        author = it.author_name[0]
                    }
                    if(it.title_suggest != null){
                        bookTitle = it.title_suggest
                    }
                    if(it.publishers != null){
                        publisher = it.publishers[0]
                    }
                    if(it.subjects != null){
                        subject = it.subjects[0]
                    }
                    lastModified = it.id
                    listBooks.add(BookPojo(lastModified,bookTitle,author,publisher,subject))
                }
            return listBooks
        }
    }

}