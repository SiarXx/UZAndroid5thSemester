package com.example.uzapp.tools

import com.example.uzapp.models.Reminder
import java.io.File
import java.util.*

class FileToReminderMapper{
    //File Name is created like this HHmmDDMMYYYYName.txt
    fun mapReminders(file: File): Reminder {
        return Reminder(
            file.name.substring(12,file.name.length-4),
            file.readText(),
            null,
            false
        )
    }
}