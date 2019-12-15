package com.example.uzapp.tools

import com.example.uzapp.models.Reminder
import java.io.File

class FileToReminderMapper{
    //File Name is created like this HHmmDDMMYYYYName.txt
    fun mapReminders(file: File): Reminder {
        return Reminder(
            file.name.substring(12,file.name.length-4),
            file.readText(),
            file.name.substring(4,12),
            file.name.substring(0,2),
            file.name.substring(2,4)
        )
    }
}