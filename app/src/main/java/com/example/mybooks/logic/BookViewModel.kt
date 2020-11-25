package com.example.mybooks.logic

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybooks.API.BookTransform
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BookViewModel(private val repository: Repository) : ViewModel(){
    private val disposable = CompositeDisposable()
    private val resultSearch = MutableLiveData<List<BookTransform.BookPojo>>()
    private val favouriteLiveData = MutableLiveData<List<BookTransform.BookPojo>>()
    private val alreadyReadLiveData = MutableLiveData<List<BookTransform.BookPojo>>()

    init {
        val bookApi = repository.bookApi("Lord of the rings")
            .retryWhen {
                it.delay(5, TimeUnit.SECONDS)
            }
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {list->
                repository.insertBooks(list)
            }
            .andThen(repository.getAllBooks())
            .startWith(repository.getAllBooks().take(1))
            .share()


         disposable.add(repository.getAllBooks()
            .subscribeOn(Schedulers.io())
            .map {
                it.filter { it.favourite }
            }
            .subscribe{
                favouriteLiveData.postValue(it)
            })

        disposable.add(repository.getAllBooks()
            .subscribeOn(Schedulers.io())
            .map {
                it.filter { it.alreadyRead }
            }
            .subscribe{
                alreadyReadLiveData.postValue(it)
            })




        disposable.add(bookApi
            .doOnError {
                Log.d("aa", it.message.toString())
            }
            .subscribe {
                Log.d("aaa", it.size.toString())
                resultSearch.postValue(it)
            })

    }

    fun favouriteClick(book: BookTransform.BookPojo){
       disposable.add(repository.updatePhoto(book.copy(favourite = !book.favourite))
            .subscribeOn(Schedulers.io())
            .subscribe())
    }

    fun alreadyReadClick(book: BookTransform.BookPojo){
        disposable.add(repository.updatePhoto(book.copy(alreadyRead = !book.alreadyRead))
            .subscribeOn(Schedulers.io())
            .subscribe())
    }

    fun getResultSearch(): LiveData<List<BookTransform.BookPojo>> = resultSearch
    fun getFavourite(): LiveData<List<BookTransform.BookPojo>> = favouriteLiveData
    fun getAlreadtRead(): LiveData<List<BookTransform.BookPojo>> = alreadyReadLiveData

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}