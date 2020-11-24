package com.example.mybooks.logic

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybooks.API.BookTransform
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookViewModel(private val repository: Repository) : ViewModel(){
    private val disposable = CompositeDisposable()
    private val resultSearch = MutableLiveData<List<BookTransform.BookPojo>>()

    init {
      val bookApi = repository.bookApi("Lord of the rings")
          ?.doOnSuccess {
              Log.d("aa", it.toString())
              resultSearch.value = it
          }
          ?.flatMapCompletable {
             repository.insertBooks(it)
          }
          ?.subscribe()


    }

    fun getResultSearch(): LiveData<List<BookTransform.BookPojo>> = resultSearch

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}