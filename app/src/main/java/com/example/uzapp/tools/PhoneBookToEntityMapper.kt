package com.example.uzapp.tools

import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.models.PhoneBook

class PhoneBookToEntityMapper {
    fun map(pB:PhoneBook):PhoneBookEntity{
        return PhoneBookEntity()
    }
}