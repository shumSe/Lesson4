package ru.mirea.shumikhin.data_thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mirea.shumikhin.data_thread.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        initThreads()
        initExplanation()
        setContentView(binding.root)
    }

    private fun initThreads() {
        val runn1 = Runnable { binding.tvInfo.setText("runn1") }
        val runn2 = Runnable { binding.tvInfo.setText("runn2") }
        val runn3 = Runnable { binding.tvInfo.setText("runn3") }
        val t = Thread {
            try {
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(runn1)
                TimeUnit.SECONDS.sleep(1)
                binding.tvInfo.postDelayed(runn3, 2000)
                binding.tvInfo.post(runn2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        t.start()
    }
    private fun initExplanation() {
        binding.tvExplanation.text = """
            Последовательность выполнения процессов будет следующей:
            Задержка выполнения потока t на 2 секунды.
            Выполнение объекта runn1 на главном UI-потоке.
            Задержка выполнения потока t на 1 секунду.
            Выполнение объекта runn2 на главном UI-потоке.
            Запуск объекта runn3 на главном UI-потоке с задержкой в 2 секунды.
            
            Метод runOnUiThread() запускает переданный ему объект Runnable на главном UI-потоке.
            Метод postDelayed() позволяет запланировать выполнение переданного ему объекта Runnable на потоке handler'a с задержкой
            Метод post() позволяет запустить переданный ему объект Runnable на потоке handler'a в ближайшее свободное время.
        """
    }
}