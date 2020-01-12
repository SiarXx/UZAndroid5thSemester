package com.example.uzapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.repositories.PhoneBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneBookViewModel (application: Application): ViewModel(){
    private val phoneBookRepository = PhoneBookRepository(application)
    fun getAllPhoneBooks() = phoneBookRepository.allPhoneBooks

    fun putAllPlayers(phoneBooks: List<PhoneBookEntity>) = viewModelScope.launch(Dispatchers.IO){
        phoneBookRepository.insertPhoneBooks(phoneBooks)
    }
}