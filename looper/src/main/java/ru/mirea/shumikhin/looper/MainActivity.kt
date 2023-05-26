package ru.mirea.shumikhin.looper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.looper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val mainThreadHandler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d(
                    MainActivity::class.java.simpleName,
                    "Task result: " + msg.data.getString("result")
                )
            }
        }
        val myLooper = MyLooper(mainThreadHandler)
        myLooper.start()
        binding.btnSend.setOnClickListener {
            val age = binding.etAge.text.toString().toIntOrNull() ?: return@setOnClickListener
            val workPlace = binding.etWorkPlace.text.toString()
            val msg = Message.obtain()
            val bundle = Bundle()
            bundle.putString("WORK_PLACE", workPlace)
            bundle.putInt("AGE", age)
            msg.data = bundle
            myLooper.currentHandler!!.sendMessage(msg)
        }

        setContentView(binding.root)
    }
}