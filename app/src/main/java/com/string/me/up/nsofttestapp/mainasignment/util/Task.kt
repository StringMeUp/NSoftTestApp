package com.string.me.up.nsofttestapp.mainasignment.util

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.string.me.up.nsofttestapp.R
import java.util.concurrent.TimeUnit

class Task(
    val name: Int? = null,
    private val delay: Long,
    private val context: Context? = null
) : Runnable {
    override fun run() {
        name?.let {
            TimeUnit.MILLISECONDS.sleep(delay)
            val handler = android.os.Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
            }
            Log.d(
                context?.getString(R.string.runnable_event),
                "${context?.getString(name)} ${context?.getString(R.string.delay_string)} $delay"
            )
        }
    }
}