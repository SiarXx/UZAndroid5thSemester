package com.example.uzapp.tools

import com.example.uzapp.models.Reminder

class Formatter{
    fun stringToReminderDate(string:String):String{
        val day = string.substring(0,2)
        val month = string.substring(2,4)
        val year = string.substring(4,string.length)
        return day + "/" + month + "/" + year
    }
    fun calendarViewDataToReminderDate(day:Int,month:Int,year:Int):String{
        var dateString = ""
        dateString += if(day<10){
            "0"+ day.toString() + (month+1).toString() + year.toString()
        } else{
            day.toString() + (month+1).toString() + year.toString()
        }
        return dateString
    }
    fun reminderToString(reminder: Reminder):String{
        return (reminder.reminderHour + reminder.reminderMinute + reminder.reminderDate + reminder.reminderTitle)
    }
}