package com.example.mybooks.view

import androidx.paging.PagedList
import com.example.mybooks.API.BookApi
import com.example.mybooks.API.BookTransform
import com.example.mybooks.logic.PagingRequestHelper
import com.example.mybooks.logic.Repository
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BookBoundaryCallback(private val search: String, private val repository: Repository): PagedList.BoundaryCallback<BookTransform.BookPojo>() {
    private val executor = Executors.newSingleThreadExecutor()
    private var currentPage = 1
    private val helper =PagingRequestHelper(executor)

    override fun onItemAtEndLoaded(itemAtEnd: BookTransform.BookPojo) {
        loadItems(PagingRequestHelper.RequestType.AFTER)
    }
    override fun onZeroItemsLoaded() {
        loadItems(PagingRequestHelper.RequestType.INITIAL)
    }

    private fun loadItems(requestType: PagingRequestHelper.RequestType){
        helper.runIfNotRunning(requestType){
            repository.bookApi(search,currentPage)
                .flatMapCompletable {
                        repository.insertBooks(it)
                }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    currentPage++
                    it.recordSuccess()
                }
        }
    }
}