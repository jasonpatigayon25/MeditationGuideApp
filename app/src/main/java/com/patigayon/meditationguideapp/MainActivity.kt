package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.patigayon.meditationguideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var meditationAdapter: MeditationAdapter
    private val meditationTechniques = mutableListOf(
        "Mindfulness Meditation", "Spiritual Meditation", "Focused Meditation",
        "Movement Meditation", "Mantra Meditation", "Transcendental Meditation",
        "Progressive Relaxation", "Loving-kindness Meditation", "Visualization Meditation",
        "Vipassana Meditation", "Zen Meditation", "Yoga Meditation",
        "Chakra Meditation", "Qigong Meditation", "Christian Contemplative Prayer"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNavigationView()
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(meditationTechniques) { position ->
            val intent = Intent(this, MeditationDetails::class.java).apply {
                putExtra("meditation_name", meditationTechniques[position])
            }
            startActivity(intent)
        }
        binding.recyclerMeditationTechniques.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = meditationAdapter
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_discover -> {
                    true
                }
                R.id.navigation_my_meditations -> {
                    true
                }
                R.id.navigation_morning -> {
                    true
                }
                R.id.navigation_evening -> {
                    true
                }
                else -> false
            }
        }
    }
}
