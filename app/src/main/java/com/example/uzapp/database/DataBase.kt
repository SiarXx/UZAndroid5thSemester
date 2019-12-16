package com.example.uzapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uzapp.entities.PhoneBookEntity
import com.example.uzapp.interfaces.PhoneBookDao

@Database(
    entities =[
        PhoneBookEntity::class
            ],version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun PhoneBookDao():PhoneBookDao

    companion object{
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase?{
            if(instance == null){
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"appDataBase.db").build()
                }
            }
            return instance
        }
        fun destroyInstance(){
            instance = null
        }
    }
}

