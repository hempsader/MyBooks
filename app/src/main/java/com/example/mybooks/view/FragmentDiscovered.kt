package com.example.mybooks.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookTransform
import com.example.mybooks.R
import com.example.mybooks.logic.BookViewModel
import com.example.mybooks.logic.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FragmentDiscovered : Fragment(), BookRecycler.IFavourite, BookRecycler.IAlreadyRead{

    private val viewModel by lazy {
        ViewModelProvider(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BookViewModel(Repository.getInstance(requireContext())!!) as T
            }
        })[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discovery,container,false)
        val recycler = view.findViewById<RecyclerView>(R.id.list)
        recycler.apply {
            layoutManager= LinearLayoutManager(requireContext())
        }

        viewModel.getResultSearch().observe(viewLifecycleOwner,{
            recycler.adapter = BookRecycler(it,this, this)
        })


        return view
    }

    override fun setBook(book: BookTransform.BookPojo) {
        viewModel.favouriteClick(book)
    }

    override fun setBookRead(book: BookTransform.BookPojo) {
        viewModel.alreadyReadClick(book)
    }
}