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
        MeditationTechnique(R.drawable.mindfulness_meditation_image, "Mindfulness Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.spiritual_meditation_image, "Spiritual Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.focused_meditation_image, "Focused Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.movement_meditation_image, "Movement Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.mantra_meditation_image, "Mantra Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.transcendental_meditation_image, "Transcendental Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.progressive_relaxation_image, "Progressive Relaxation", "Morning Routine"),
        MeditationTechnique(R.drawable.loving_kindness_meditation_image, "Loving-kindness Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.visualization_meditation_image, "Visualization Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.vipassana_meditation_image, "Vipassana Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.zen_meditation_image, "Zen Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.yoga_meditation_image, "Yoga Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.chakra_meditation_image, "Chakra Meditation", "Morning Routine"),
        MeditationTechnique(R.drawable.qigong_meditation_image, "Qigong Meditation", "Evening Routine"),
        MeditationTechnique(R.drawable.christian_contemplative_prayer_image, "Christian Contemplative Prayer", "Morning Routine")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNavigationView()
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(meditationTechniques) { technique ->
            // Now 'technique' is a MeditationTechnique object
            val intent = Intent(this, MeditationDetails::class.java).apply {
                putExtra("meditation_name", technique.title) // Use 'title' from MeditationTechnique
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

