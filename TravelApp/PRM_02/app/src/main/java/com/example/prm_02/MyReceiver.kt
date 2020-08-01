package com.example.prm_02

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intent2 = Intent(context, MyService::class.java)
        intent2.putExtra("radius",intent.getStringExtra("radius"))
        intent2.putExtra("path",intent.getStringExtra("path"))
        context.startForegroundService(intent2)
    }
}