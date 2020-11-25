package com.example.mybooks.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.API.BookTransform
import com.example.mybooks.R
import com.example.mybooks.logic.Repository
import java.util.zip.Inflater

class BookRecycler(private val bookList: List<BookTransform.BookPojo>, val repository: Repository) : RecyclerView.Adapter<BookRecycler.BookHolder>(){
    inner class BookHolder(val itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private  var title: TextView
        private  var author: TextView
        private  var publisher: TextView
        private  var subject: TextView
        private  var favorite: ImageView

        init {
            title = itemView.findViewById(R.id.title)
            author = itemView.findViewById(R.id.author)
            publisher = itemView.findViewById(R.id.publisher)
            subject = itemView.findViewById(R.id.subject)
            favorite = itemView.findViewById(R.id.favorite)
            favorite.setOnClickListener(this)
        }
        fun bind(book: BookTransform.BookPojo){
            title.text = book.bookTitle
            author.text = book.author
            publisher.text = book.publisher
            subject.text = book.subject
        }

        override fun onClick(v: View?) {
            
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_book_row,parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(bookList.get(position))
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}