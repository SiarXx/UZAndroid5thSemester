package com.example.uzapp.models

import java.io.Serializable
import java.util.*

data class Reminder(
    val reminderTitle:String? = null,
    val reminderBody: String? = null,
    val reminderDate: Date,
    var selected: Boolean = false
):Serializable