package com.example.mybooks.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookTransform
import com.example.mybooks.R

class FragmentDiscovered : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discovery,container,false)
        val recycler = view.findViewById<RecyclerView>(R.id.list)
        recycler.apply {
            layoutManager= LinearLayoutManager(requireContext())
            BookApi.instance()
                .getBooks("lord of the rings")
                .map {
                    it.body()?.let { it1 -> BookTransform.transform(it1) }
                }
                .doOnError {
                    Log.d("aa", it.message.toString())
                }
                .subscribe { it->
                    adapter = it?.let { it1 -> BookRecycler(it1) }
                }

        }
        return view
    }
}