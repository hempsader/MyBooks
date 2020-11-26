package com.example.mybooks.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.API.BookTransform
import com.example.mybooks.R
import com.example.mybooks.logic.Repository
import java.util.zip.Inflater

class BookRecycler(private val setBook: IFavourite, private val setAlreadyRead: IAlreadyRead) :
    PagedListAdapter<BookTransform.BookPojo ,BookRecycler.BookHolder>(DiffUtill){

    interface IFavourite {
        fun setBook(book: BookTransform.BookPojo)
    }

    interface IAlreadyRead{
        fun setBookRead(book: BookTransform.BookPojo)
    }

    inner class BookHolder( itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private  var title: TextView
        private  var author: TextView
        private  var publisher: TextView
        private  var subject: TextView
        private  var favorite: ImageView
        private  var alreadyRead: ImageView

        init {
            title = itemView.findViewById(R.id.title)
            author = itemView.findViewById(R.id.author)
            publisher = itemView.findViewById(R.id.publisher)
            subject = itemView.findViewById(R.id.subject)
            favorite = itemView.findViewById(R.id.favorite)
            alreadyRead = itemView.findViewById(R.id.already_read)

            favorite.setOnClickListener(this)
            alreadyRead.setOnClickListener(this)
        }
        fun bind(book: BookTransform.BookPojo){
            title.text = book.bookTitle
            author.text = book.author
            publisher.text = book.publisher
            subject.text = book.subject
            when(book.favourite){
                true -> favorite.setImageResource(R.drawable.ic_star_black_24dp)
                false -> favorite.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
            when(book.alreadyRead){
                true -> alreadyRead.setImageResource(R.drawable.ic_markunread_black_24dp)
                false -> alreadyRead.setImageResource(R.drawable.ic_mail_outline_black_24dp)
            }
        }

        override fun onClick(v: View?) {
            when(v?.id){
                R.id.already_read -> setAlreadyRead.setBookRead(bookList[adapterPosition])
                R.id.favorite ->   setBook.setBook(bookList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_book_row,parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = getItem(position)?: return
        holder.bind(book)
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