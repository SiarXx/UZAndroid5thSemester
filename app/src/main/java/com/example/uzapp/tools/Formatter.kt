package com.example.uzapp.tools

class Formatter{
    fun stringToReminderDate(string:String):String{
        val day = string.substring(0,2)
        val month = string.substring(2,4)
        val year = string.substring(4,string.length)
        return day + "/" + month + "/" + year
    }
}