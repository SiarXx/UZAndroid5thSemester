package com.example.uzapp.repositories

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.uzapp.database.AppDatabase
import com.example.uzapp.entities.PhoneBookEntity

class PhoneBookRepository (application: Application){
    val dB = AppDatabase.getInstance(application)
    val phoneBookDao = dB!!.PhoneBookDao()
    val allPhoneBooks = phoneBookDao.selectAll()

    @WorkerThread
    suspend fun insertPhoneBooks(phoneBooks: List<PhoneBookEntity>) {
        phoneBookDao.insertPhoneBooks(*phoneBooks.toTypedArray())
    }
}