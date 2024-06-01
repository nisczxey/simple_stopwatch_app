package com.example.simple_stopwatch_app

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class StopwatchTimer() {

    private var time: Long = 0L
    private var _isActivated = false
    val isActivated: Boolean
        get() = _isActivated

    fun startTimer() {
        _isActivated = true
    }

    fun stopTimer() {
        if (_isActivated) {
            _isActivated = false
        }
    }

    fun resetTimer() {
        time = 0L
    }

    fun tickerFlow(period: kotlin.time.Duration): Flow<Unit> = flow {
        while (_isActivated) {
            emit(Unit)
            delay(period)
            time++
        }
    }

    fun getFormattedTime(): String {
        val unFormattedTime = time
        val hours = unFormattedTime / 3600
        val minutes = (unFormattedTime % 3600) / 60
        val seconds = time % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
