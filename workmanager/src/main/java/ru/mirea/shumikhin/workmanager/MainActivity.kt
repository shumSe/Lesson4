package ru.mirea.shumikhin.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.mirea.shuimkhin.workmanager.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val workRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        WorkManager
            .getInstance(this)
            .enqueue(workRequest)
    }
}