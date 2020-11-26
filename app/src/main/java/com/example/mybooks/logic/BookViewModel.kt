package com.example.mybooks.logic

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.mybooks.API.BookTransform
import com.example.mybooks.view.BookBoundaryCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class BookViewModel(private val repository: Repository) : ViewModel(){
    private val disposable = CompositeDisposable()
     val resultSearch = MutableLiveData<PagedList<BookTransform.BookPojo>>()
     val favouriteLiveData = MutableLiveData<PagedList<BookTransform.BookPojo>>()
     val alreadyReadLiveData = MutableLiveData<PagedList<BookTransform.BookPojo>>()

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        disposable.add(RxPagedListBuilder(repository.getAllBooks(),config)
            .setBoundaryCallback(BookBoundaryCallback("The lord of the rings", repository))
            .buildObservable()
            .doOnError { Log.d("aaa", it.message.toString()) }
            .subscribe(resultSearch::postValue))

       disposable.add(RxPagedListBuilder(repository.getFavourites(),config)
           .setBoundaryCallback(BookBoundaryCallback("The lord of the rings", repository))
            .buildObservable()
            .subscribe(favouriteLiveData::postValue))

       disposable.add(RxPagedListBuilder(repository.alreadyRead(),config)
           .setBoundaryCallback(BookBoundaryCallback("The lord of the rings", repository))
            .buildObservable()
            .subscribe(alreadyReadLiveData::postValue))

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



    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}