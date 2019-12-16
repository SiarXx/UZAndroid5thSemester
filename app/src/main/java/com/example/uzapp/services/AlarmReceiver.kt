package com.example.uzapp.services


import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.uzapp.R


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notification =NotificationCompat.Builder(context,"2137")
            .setContentTitle("Siemanko Masz Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Treść"))
            .build()
        NotificationManagerCompat.from(context).notify(2137,notification)
    }
}