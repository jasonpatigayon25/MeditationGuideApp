package com.patigayon.meditationguideapp

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.patigayon.meditationguideapp.databinding.ActivityStartingSessionBinding

class StartingSessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartingSessionBinding
    private var countDownTimer: CountDownTimer? = null
    private var timerRunning = false
    private var timeLeftInMillis: Long = 5 * 60000
    private var colorAnimation: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityStartingSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimePicker()

        binding.startButton.setOnClickListener {
            if (!timerRunning) {
                val selectedTime = getSelectedTimeFromPicker()
                timeLeftInMillis = selectedTime * 60000L
                startTimer(timeLeftInMillis)
                animateMeditationIcon()
            }
        }

        binding.stopButton.setOnClickListener {
            countDownTimer?.cancel()
            timerRunning = false
            updateButtons()
            stopMeditationIconAnimation()
        }

        updateButtons()
    }

    private fun setupTimePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.timePicker.apply {
                setIs24HourView(true)
                hour = 0
                minute = 5
            }
        } else {
            @Suppress("DEPRECATION")
            binding.timePicker.apply {
                setIs24HourView(true)
                currentHour = 0
                currentMinute = 5
            }
        }
    }

    private fun getSelectedTimeFromPicker(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.timePicker.minute
        } else {
            @Suppress("DEPRECATION")
            binding.timePicker.currentMinute
        }
    }

    private fun animateMeditationIcon() {
        colorAnimation = ObjectAnimator.ofArgb(
            binding.meditationIcon.drawable.mutate(),
            "tint",
            ContextCompat.getColor(this, R.color.start_color),
            ContextCompat.getColor(this, R.color.mid_color),
            ContextCompat.getColor(this, R.color.end_color)
        ).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        colorAnimation?.start()
    }

    private fun stopMeditationIconAnimation() {
        colorAnimation?.cancel()
        binding.meditationIcon.clearColorFilter()
    }

    private fun startTimer(millisInFuture: Long) {
        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText(timeLeftInMillis)
            }

            override fun onFinish() {
                timerRunning = false
                updateButtons()
                updateCountDownText(0)
            }
        }.start()
        timerRunning = true
        updateButtons()
    }

    private fun updateCountDownText(timeInMillis: Long) {
        val minutes = (timeInMillis / 1000) / 60
        val seconds = (timeInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timeFormatted
    }

    private fun updateButtons() {
        if (timerRunning) {
            binding.startButton.visibility = View.GONE
            binding.stopButton.visibility = View.VISIBLE
        } else {
            binding.startButton.visibility = View.VISIBLE
            binding.stopButton.visibility = View.GONE
        }
    }
}
