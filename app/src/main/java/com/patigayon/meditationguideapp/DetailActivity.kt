package com.patigayon.meditationguideapp

import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
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

        Glide.with(this).load(meditationPhotoUrl).into(binding.meditationImage)
    }
}
