package ru.mirea.shumikhin.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.mirea.shumikhin.thread.databinding.ActivityMainBinding
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        init()

        setContentView(binding.root)
    }

    private fun init() {
        binding.btnCalculate.setOnClickListener {
            val countOfLessons =
                binding.edCountLessons.text.toString().toIntOrNull() ?: return@setOnClickListener
            val countOfStudyDays =
                binding.edCountStudyDays.text.toString().toIntOrNull() ?: return@setOnClickListener

            if (countOfStudyDays == 0) return@setOnClickListener

            binding.tvResult.text = "Среднее число пар ${countOfLessons / countOfStudyDays}"
        }

        val info = binding.tvCurrentThreadName
        val mainThread = Thread.currentThread()
        info.text = "Имя текущего потока: " + mainThread.name
        mainThread.name =
            "МОЙ НОМЕР ГРУППЫ: БСБО-06-21, НОМЕР ПО СПИСКУ: 28, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Человек Дождя"
        info.append("\nНовое имя потока: ${mainThread.name}")
        Log.d(
            MainActivity::class.java.simpleName,
            "Stack: " + Arrays.toString(mainThread.stackTrace)
        )

        val lock = Object()
        var counter = 0
        binding.btnMirea.setOnClickListener {
            Thread {
                val numberThread: Int = counter++
                val endTime = System.currentTimeMillis() + 10 * 1000
                while (System.currentTimeMillis() < endTime) {
                    synchronized(lock) {
                        try {
                            lock.wait(endTime - System.currentTimeMillis())
                        } catch (e: Exception) {
                            throw RuntimeException(e)
                        }
                    }
                }
                Log.d("ThreadProject", "Выполнен поток № $numberThread");
            }.start()
        }
    }
}