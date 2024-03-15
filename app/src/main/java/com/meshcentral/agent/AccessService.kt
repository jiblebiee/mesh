package com.meshcentral.agent

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.RequiresApi
import java.util.Timer
import java.util.TimerTask

class AccessService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) { }

    override fun onInterrupt() { }

    @SuppressLint("NewApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var x = intent?.getIntExtra("PosX", 0);
        var y = intent?.getIntExtra("PosY", 0);

        if (x == null) x = 0;
        if (y == null) y = 0;

        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 50))
        dispatchGesture(gestureBuilder.build(), null, null)

        stopSelf();

        // You can perform any desired operations here

        // If you want the service to continue running until explicitly stopped,
        // return START_STICKY. Otherwise, return START_NOT_STICKY or START_REDELIVER_INTENT.
        return START_STICKY
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onServiceConnected() {
        super.onServiceConnected()

        val timer = Timer()
        var cnt = 0;

        // Schedule a task to execute every 5 seconds
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                cnt++;
                val path = Path()
                path.moveTo(50f, 50f)
                val gestureBuilder = GestureDescription.Builder()
                gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 50))
                dispatchGesture(gestureBuilder.build(), null, null)

                if(cnt >= 2) {
                    timer.cancel()
                    timer.purge()
                }
            }
        }, 0, 1000) // Change the second parameter to the desired interval in milliseconds
    }
}