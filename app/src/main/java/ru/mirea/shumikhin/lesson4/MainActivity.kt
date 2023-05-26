package ru.mirea.shumikhin.lesson4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mirea.shumikhin.lesson4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.editTextMirea.text = "Мой номер по списку №28"
        setContentView(binding.root)
    }
}