package com.example.mybooks.API


class BookTransform {
    data class BookPojo(val bookTitle: String?,  val author: String?,  val publisher: String?,  val subject: String?)
    companion object{
        fun transform(response: BookResponse): List<BookPojo>{
            var author = "Unknown Author"
            var bookTitle = "Unknown Title"
            var publisher = "Unknown Publisher"
            var subject = "Unknown Subject"
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
                    listBooks.add(BookPojo(bookTitle,author,publisher,subject))
                }
            return listBooks
        }
    }

}