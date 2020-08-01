package com.example.prm_02

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {
    var id = 1
    override fun onCreate()
    {
        super.onCreate();

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var r = intent!!.getStringExtra("radius")
        var path = intent!!.getStringExtra("path")
        var i = Intent(this,ResultActivity::class.java)
        i.putExtra("path",path)
        var pendingIntent = PendingIntent.getActivity(this,1,i,PendingIntent.FLAG_UPDATE_CURRENT)
        val noti = NotificationCompat.Builder(this, "com.example.prm_02.channel.NOTI_CHANNEL")
            .setSmallIcon(R.mipmap.message_img)
            .setContentTitle("You made a photo $r meters away!")
            .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        id++
        startForeground(id, noti)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
