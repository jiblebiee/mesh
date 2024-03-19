package com.meshcentral.agent

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Point
import android.hardware.input.InputManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.MotionEvent
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.RequiresApi
import java.util.Timer
import java.util.TimerTask

class AccessService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) { }

    override fun onInterrupt() { }

    @SuppressLint("NewApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var x = intent?.getIntExtra("PosX", 0)
        var y = intent?.getIntExtra("PosY", 0)
        var stX = intent?.getIntExtra("stX", 0)
        var stY = intent?.getIntExtra("stY", 0)
        var enX = intent?.getIntExtra("enX", 0)
        var enY = intent?.getIntExtra("enY", 0)
        if (x == null) x = 0;
        if (y == null) y = 0;
        if (stX == null) stX = 0;
        if (stY == null) stY = 0;
        if (enX == null) enX = 0;
        if (enY == null) enY = 0;

        if(x != 0 || y != 0) {
            val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val displaySize = Point()
            display.getSize(displaySize)
            val resX = displaySize.x
            val resY = displaySize.y
            y = y * displaySize.y / (displaySize.y - 150)
            println("====X:$x===Y:$y===ResX:$resX===ResY:$resY====")

//            if(y < resY - 140) {
                val path = Path()
                path.moveTo(x.toFloat(), y.toFloat())
                val gestureBuilder = GestureDescription.Builder()
                gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 50))
                dispatchGesture(gestureBuilder.build(), null, null)
//            }
//            else {
//                val downTime = SystemClock.uptimeMillis()
//                val eventTime = SystemClock.uptimeMillis()
//                val motionEventDown = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x.toFloat(),  y.toFloat(), 0)
//                val motionEventUp = MotionEvent.obtain(downTime, eventTime + 100, MotionEvent.ACTION_UP, x.toFloat(), y.toFloat(), 0)
//            }
        }
        else {
            println("====stX:$stX===stY:$stY===enX:$enX===enY:$enY====")

            val path = Path()
            path.moveTo(stX.toFloat(), stY.toFloat())
            path.lineTo(enX.toFloat(), enY.toFloat())
            val gestureBuilder = GestureDescription.Builder()
            gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 100))
            dispatchGesture(gestureBuilder.build(), null, null)
        }
        stopSelf();

        // If you want the service to continue running until explicitly stopped,
        // return START_STICKY. Otherwise, return START_NOT_STICKY or START_REDELIVER_INTENT.
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onServiceConnected() {
        super.onServiceConnected()

//        val timer = Timer()
//        var cnt = 0;

        // Schedule a task to execute every 5 seconds
//        timer.scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                cnt++;
//                val path = Path()
//                path.moveTo(50f, 50f)
//                val gestureBuilder = GestureDescription.Builder()
//                gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 50))
//                dispatchGesture(gestureBuilder.build(), null, null)
//
//                if(cnt >= 2) {
//                    timer.cancel()
//                    timer.purge()
//                }
//            }
//        }, 0, 1000)
    // Change the second parameter to the desired interval in milliseconds
    }
}