package ru.mirea.shumikhin.looper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class MyLooper(
    val mainHandler: Handler,
) : Thread() {
    var currentHandler: Handler? = null

    override fun run() {
        Log.d("MyLooper", "run")
        Looper.prepare()
        currentHandler = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                val age = msg.data.getInt("AGE")
                val workPlace: String = msg.data.getString("WORK_PLACE") ?: ""
                Log.d("MyLooper WORKPLACE: ", workPlace)
                val message = Message()
                val bundle = Bundle()
                bundle.putString(
                    "result",
                    "Delay is ($age) seconds"
                )
                message.data = bundle
                sleep(age * 1000L)
                mainHandler.sendMessage(message)
            }
        }
        Looper.loop()
    }
}