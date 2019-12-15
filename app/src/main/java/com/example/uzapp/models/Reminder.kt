package com.example.uzapp.models

import java.io.Serializable
import java.util.*

data class Reminder(
    val reminderTitle:String? = null,
    val reminderBody: String? = null,
    val reminderDate: String? = null,
    val reminderHour: String? = null,
    var selected: Boolean = false
):Serializable