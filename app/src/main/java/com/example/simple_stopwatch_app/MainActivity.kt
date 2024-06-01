package com.example.simple_stopwatch_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.simple_stopwatch_app.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val timer = StopwatchTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.btnStart.setOnClickListener {
            toggleTimer()
        }
        binding.btnReset.setOnClickListener {
            resetTime()
        }
    }

    private fun toggleTimer() {
        if (!timer.isActivated) {
            startTimeGoing()
        } else {
            stopTimer()
        }
    }

    private fun stopTimer() {
        binding.btnStart.text = "start"
        timer.stopTimer()
    }

    private fun startTimeGoing() {
        timer.startTimer()
        binding.btnStart.text = "stop"
        timer.tickerFlow(1.seconds)
            .map { timer.getFormattedTime() }
            .distinctUntilChanged()
            .onEach { binding.tvTime.text = it }
            .launchIn(lifecycleScope)
    }

    private fun resetTime() {
        timer.resetTimer()
        binding.tvTime.text = timer.getFormattedTime()
    }

}