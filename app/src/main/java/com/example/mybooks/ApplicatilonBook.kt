package com.example.mybooks

import android.app.Application
import com.example.mybooks.logic.Repository

class ApplicationBook : Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.getInstance(this)
    }
}