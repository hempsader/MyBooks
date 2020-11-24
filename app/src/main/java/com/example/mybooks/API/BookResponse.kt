package com.example.mybooks.API

import com.google.gson.annotations.SerializedName


class BookResponse (
                    @SerializedName("numFound")
                    val numFound: Int? = 0,
                    @SerializedName("docs")
                    val books: List<Book>? = emptyList()){
    data class Book(val title_suggest: String, val author_name: List<String>?, @SerializedName("subject") val subjects: List<String>?,@SerializedName("publisher") val publishers: List<String>?)
}

data class Subject( val subject: String)
data class Published( val publisher: String)