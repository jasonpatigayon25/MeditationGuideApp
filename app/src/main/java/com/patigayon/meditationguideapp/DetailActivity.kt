package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.patigayon.meditationguideapp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meditationName = intent.getStringExtra("name")
        val meditationRoutine = intent.getStringExtra("routine")
        val meditationPhotoUrl = intent.getStringExtra("photo")
        val meditationDescription = intent.getStringExtra("description")
        binding.meditationDescription.text = meditationDescription
        binding.meditationName.text = meditationName
        binding.meditationRoutine.text = meditationRoutine

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.buttonStartMeditation.setOnClickListener {
            val intent = Intent(this, StartingSessionActivity::class.java)
            startActivity(intent)
        }

        Glide.with(this).load(meditationPhotoUrl).into(binding.meditationImage)
    }
}
