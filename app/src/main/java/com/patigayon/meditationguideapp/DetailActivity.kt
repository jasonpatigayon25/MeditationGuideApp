package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.patigayon.meditationguideapp.databinding.ActivityDetailBinding
import jp.wasabeef.glide.transformations.BlurTransformation
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val meditationName = intent.getStringExtra("name")
        val meditationRoutine = intent.getStringExtra("routine")
        val meditationPhotoUrl = intent.getStringExtra("photo")
        val meditationDescription = intent.getStringExtra("description")

        binding.meditationName.text = meditationName
        binding.meditationRoutine.text = meditationRoutine
        binding.meditationDescription.text = meditationDescription

        meditationPhotoUrl?.let { url ->

            Glide.with(this)
                .load(url)
                .into(binding.meditationImage)

            Glide.with(this)
                .load(url)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))) // apply blur effect
                .into(binding.backgroundImageView)

            binding.backgroundImageView.visibility = View.VISIBLE
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.buttonStartMeditation.setOnClickListener {
            startActivity(Intent(this, StartingSessionActivity::class.java))
        }
    }
}