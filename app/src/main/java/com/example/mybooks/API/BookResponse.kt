package com.example.mybooks.API

import com.google.gson.annotations.SerializedName


class BookResponse (
                    @SerializedName("numFound")
                    val numFound: Int? = 0,
                    @SerializedName("docs")
                    val books: List<Book>? = emptyList()){
    data class Book(@SerializedName("last_modified_i") val id: Long , val title_suggest: String, val author_name: List<String>?, @SerializedName("subject") val subjects: List<String>?, @SerializedName("publisher") val publishers: List<String>?)
}

