package com.example.mybooks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.API.BookTransform
import com.example.mybooks.R
import kotlinx.android.synthetic.main.adapter_book_row.view.*

class BookRecycler(private val setAlreadyRead: IAlreadyRead, private val favourite: IFavourite) :
    PagedListAdapter<BookTransform.BookPojo ,BookRecycler.BookHolder>(DiffUtill) {

    interface IFavourite {
        fun setBook(book: BookTransform.BookPojo)
    }

    interface IAlreadyRead {
        fun setBookRead(book: BookTransform.BookPojo)
    }

    inner class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_book_row,parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = getItem(position)?: return
        holder.itemView.author.text = book.author ?: "Unknown Author"
        holder.itemView.title.text = book.bookTitle ?: "Unknown Book Title"
        holder.itemView.publisher.text = book.publisher ?: "Unknown Publisher"
        holder.itemView.subject.text = book.subject ?: "Unknown Subject"
        holder.itemView.favorite.setOnClickListener { favourite.setBook(book) }
        holder.itemView.already_read.setOnClickListener { setAlreadyRead.setBookRead(book) }

        if(book.favourite) holder.itemView.favorite.setImageResource(R.drawable.ic_star_black_24dp) else holder.itemView.favorite.setImageResource(R.drawable.ic_star_border_black_24dp)
        if(book.alreadyRead) holder.itemView.already_read.setImageResource(R.drawable.ic_markunread_black_24dp) else holder.itemView.already_read.setImageResource(R.drawable.ic_mail_outline_black_24dp)
    }
}

object DiffUtill: DiffUtil.ItemCallback<BookTransform.BookPojo>(){
    override fun areItemsTheSame(
        oldItem: BookTransform.BookPojo,
        newItem: BookTransform.BookPojo
    ): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(
        oldItem: BookTransform.BookPojo,
        newItem: BookTransform.BookPojo
    ): Boolean {
      return  oldItem == newItem
    }
}