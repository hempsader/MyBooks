package com.example.mybooks

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import io.reactivex.Observable


fun  SearchView.readText(): Observable<String> {
    return Observable.create<String> {
        val editText = object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                it.onNext(query?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                it.onNext(newText?: "")
                return true
            }

        }
        setOnQueryTextListener(editText)
    }
}