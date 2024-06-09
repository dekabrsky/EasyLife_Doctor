package ru.dekabrsky.easylife.profile.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dekabrsky.easylife.profile.databinding.ActivityDailyBinding

class DailyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}