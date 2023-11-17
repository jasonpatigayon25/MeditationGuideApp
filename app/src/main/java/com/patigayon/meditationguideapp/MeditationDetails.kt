package com.patigayon.meditationguideapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MeditationDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meditation_detail)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_meditation_details)
        val tvMeditationName = findViewById<TextView>(R.id.tv_meditation_name)
        val tvMeditationDescription = findViewById<TextView>(R.id.tv_meditation_description)
        val ivMeditationImage = findViewById<ImageView>(R.id.iv_meditation_image)
        val btnStartMeditation = findViewById<Button>(R.id.btn_start_meditation)

        val meditationName = intent.getStringExtra("meditation_name") ?: ""

        tvMeditationName.text = meditationName
        tvMeditationDescription.text = getMeditationDescription(meditationName)
        ivMeditationImage.setImageResource(getMeditationImage(meditationName))

        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnStartMeditation.setOnClickListener {
            showDurationPicker()
        }
    }

    private fun showDurationPicker() {
        val durations = arrayOf("5 minutes", "10 minutes", "15 minutes", "20 minutes")
        AlertDialog.Builder(this)
            .setTitle("Choose duration")
            .setItems(durations) { dialog, which ->
                val selectedDuration = when (which) {
                    0 -> 5 * 60 * 1000L
                    1 -> 10 * 60 * 1000L
                    2 -> 15 * 60 * 1000L
                    3 -> 20 * 60 * 1000L
                    else -> 0L
                }
                startMeditationSession(selectedDuration)
                incrementMeditationCounter()
            }
            .show()
    }

    private fun startMeditationSession(durationInMillis: Long) {
        // TODO: Start a countdown timer or meditation session with the specified duration
    }

    private fun incrementMeditationCounter() {
        val prefs = getSharedPreferences("meditation_prefs", Context.MODE_PRIVATE)
        val sessionsCompleted = prefs.getInt("sessions_completed", 0)
        prefs.edit().putInt("sessions_completed", sessionsCompleted + 1).apply()
    }

    private fun scheduleDailyReminder() {
        val intent = Intent(applicationContext, ReminderBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeToStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeToStart,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun getMeditationImage(meditationName: String): Int {
        // Replace with actual drawable resources
        return when (meditationName) {
            "Mindfulness Meditation" -> R.drawable.mindfulness_image
            // ... other meditation techniques
            else -> R.drawable.default_meditation_image
        }
    }

    private fun getMeditationDescription(meditationName: String): String {
        return when (meditationName) {
            "Mindfulness Meditation" -> "Mindfulness involves focusing on the present moment without judgment."
            "Spiritual Meditation" -> "Spiritual meditation is a practice that revolves around reflections on silence and the seeking of a deeper connection with a higher power or the universe."
            "Focused Meditation" -> "Focused meditation involves concentration using any of the five senses. For example, you can focus on something internal, like your breath, or you can bring in external influences to help focus your attention."
            "Movement Meditation" -> "This practice may include walking through the woods, gardening, qigong, and other gentle forms of motion. It’s an active form of meditation where the movement guides you."
            "Mantra Meditation" -> "Mantra meditation is prominent in many teachings, including Hindu and Buddhist traditions. This type of meditation uses a repetitive sound to clear the mind."
            "Transcendental Meditation" -> "Transcendental Meditation is a simple, natural technique practiced 20 minutes twice each day while sitting comfortably with the eyes closed."
            "Progressive Relaxation" -> "Also known as body scan meditation, progressive relaxation is a practice aimed at reducing tension in the body and promoting relaxation."
            "Loving-kindness Meditation" -> "Loving-kindness meditation is designed to promote feelings of compassion and love, both for others and oneself."
            "Visualization Meditation" -> "This technique involves visualizing positive scenes, images, or scenarios to improve mood and relaxation."
            "Vipassana Meditation" -> "Vipassana is one of India's most ancient techniques of meditation. It teaches complete mindfulness of breathing."
            "Zen Meditation" -> "Zen meditation, also known as Zazen, is a meditation technique rooted in Buddhist psychology. The goal is to regulate attention."
            "Yoga Meditation" -> "Yoga meditation involves practicing a series of postures and controlled breathing exercises to promote a more flexible body and a calm mind."
            "Chakra Meditation" -> "Chakra meditation involves focus on seven specific energy centers (chakras) in the body to bring balance and enhance well-being."
            "Qigong Meditation" -> "Qigong is a traditional Chinese medicine practice that combines meditation, controlled breathing, and movement exercises."
            "Christian Contemplative Prayer" -> "This form of prayer is similar to meditation and involves thinking deeply about the words of a prayer and speaking to God in a personal way."
            else -> "No description available."
        }
    }

}

