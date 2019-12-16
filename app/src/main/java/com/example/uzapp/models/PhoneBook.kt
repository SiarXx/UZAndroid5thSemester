package com.example.uzapp.models

import java.io.Serializable

data class PhoneBook(
    val name: String?,
    val lastName: String?,
    val email: String?,
    val sex: String?,
    val number: String?,
    val avatar: String
):Serializable