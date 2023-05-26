package ru.mirea.shumikhin.serviceapp

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import ru.mirea.shumikhin.serviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PermissionCode = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)
        if (ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(MainActivity::class.java.simpleName.toString(), "Разрешения получены")
        } else {
            Log.d(MainActivity::class.java.simpleName.toString(), "Нет разрешений!")
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(POST_NOTIFICATIONS, FOREGROUND_SERVICE), PermissionCode
            )
        }

        binding.tvSongName.text = "After Dark"

        if (binding.btnPlay.tag == null) {
            binding.btnPlay.tag = R.drawable.baseline_play_arrow_24
        }

        binding.btnPlay.setOnClickListener {
            when (binding.btnPlay.tag) {
                R.drawable.baseline_pause_24 -> {
                    binding.btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                    binding.btnPlay.tag = R.drawable.baseline_play_arrow_24
                    stopService(
                        Intent(this@MainActivity, PlayerService::class.java)
                    )
                }

                R.drawable.baseline_play_arrow_24 -> {
                    binding.btnPlay.setImageResource(R.drawable.baseline_pause_24)
                    binding.btnPlay.tag = R.drawable.baseline_pause_24
                    val serviceIntent = Intent(this@MainActivity, PlayerService::class.java)
                    ContextCompat.startForegroundService(this@MainActivity, serviceIntent)
                }
            }
        }
    }
}