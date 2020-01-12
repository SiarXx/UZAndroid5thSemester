package com.example.uzapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uzapp.database.AppDatabase
import com.example.uzapp.tools.DBWorkerThread

class MainActivity : AppCompatActivity() {

    lateinit var dbWorker: DBWorkerThread
    var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create Database On Start
        dbWorker = DBWorkerThread("dbWorker")
        dbWorker.start()
        mDb = AppDatabase.getInstance(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("2137", "kremowkowenotyfikacje", importance).apply {
                description = "MlekoMaciejEasterEgg"
            }


            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
