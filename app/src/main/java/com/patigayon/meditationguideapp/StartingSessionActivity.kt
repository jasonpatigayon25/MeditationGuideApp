package com.patigayon.meditationguideapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.patigayon.meditationguideapp.databinding.ActivityStartingSessionBinding

class StartingSessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartingSessionBinding
    private var countDownTimer: CountDownTimer? = null
    private var timerRunning = false
    private var timeLeftInMillis: Long = 5 * 60000 // Default to 5 minutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartingSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timePicker.setIs24HourView(true) // Use 24 hour view to show minutes only
        binding.timePicker.hour = 0 // Set default hour to 0
        binding.timePicker.minute = 5 // Set default minute to 5

        // Initial states of buttons
        binding.startButton.visibility = View.VISIBLE
        binding.stopButton.visibility = View.GONE

        // Configure back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Configure next button
        binding.nextButton.setOnClickListener {
            // Handle the click for the Next button here
        }

        // Set up the start button
        binding.startButton.setOnClickListener {
            // Get minutes from TimePicker and convert to milliseconds
            timeLeftInMillis = binding.timePicker.minute.toLong() * 60000
            startTimer(timeLeftInMillis)

            timerRunning = true
            binding.startButton.visibility = View.GONE
            binding.stopButton.visibility = View.VISIBLE
        }

        // Set up the stop button
        binding.stopButton.setOnClickListener {
            countDownTimer?.cancel()
            updateCountDownText(timeLeftInMillis) // Reset the text to the original time left
            timerRunning = false
            binding.startButton.visibility = View.VISIBLE
            binding.stopButton.visibility = View.GONE
        }
    }

    private fun startTimer(millisInFuture: Long) {
        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText(timeLeftInMillis)
            }

            override fun onFinish() {
                timerRunning = false
                binding.startButton.visibility = View.VISIBLE
                binding.stopButton.visibility = View.GONE
                updateCountDownText(0) // Clear the text
            }
        }.start()
    }

    private fun updateCountDownText(timeInMillis: Long) {
        val minutes = (timeInMillis / 1000) / 60
        val seconds = (timeInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timeFormatted
    }
}
