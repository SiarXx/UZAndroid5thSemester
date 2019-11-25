package com.example.uzapp.models

import java.io.Serializable

data class Note(
    val title:String? = null,
    val noteBody: String? = null,
    var selected: Boolean = false
):Serializable