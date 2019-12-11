package com.example.uzapp.models

import java.io.Serializable

data class Reminder(
    val reminderTitle:String? = null,
    val reminderBody: String? = null,
    var selected: Boolean = false
):Serializable