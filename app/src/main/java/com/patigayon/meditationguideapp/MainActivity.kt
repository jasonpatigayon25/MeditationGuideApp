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
        MeditationTechnique(
            R.drawable.mindfulness_meditation_image,
            "Mindfulness Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Awareness", "Maintaining moment-by-moment awareness."),
                MeditationStage("Breathing", "Focusing on the breath."),
                MeditationStage("Observation", "Observing thoughts without judgment.")
            )
        ),
        MeditationTechnique(
            R.drawable.spiritual_meditation_image,
            "Spiritual Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Silence", "Finding silence and solitude."),
                MeditationStage("Connection", "Connecting with the divine."),
                MeditationStage("Reflection", "Reflecting on spiritual values.")
            )
        ),
        MeditationTechnique(
            R.drawable.focused_meditation_image,
            "Focused Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Concentration", "Concentrating on a single object."),
                MeditationStage("Sensory Focus", "Using a sensory object for focus."),
                MeditationStage("Attention", "Directing full attention to the object.")
            )
        ),
        MeditationTechnique(
            R.drawable.movement_meditation_image,
            "Movement Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Body Movement", "Focusing on gentle movements."),
                MeditationStage("Breath Synchronization", "Synchronizing movement with breath."),
                MeditationStage("Mindfulness", "Practicing mindfulness through movement.")
            )
        ),
        MeditationTechnique(
            R.drawable.mantra_meditation_image,
            "Mantra Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Mantra Recitation", "Repeating a sacred word or phrase."),
                MeditationStage("Focus", "Focusing on the mantra's vibrations."),
                MeditationStage("Continuity", "Maintaining a continuous rhythmic pattern.")
            )
        ),
        MeditationTechnique(
            R.drawable.transcendental_meditation_image,
            "Transcendental Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Mantra Use", "Using a personal mantra silently."),
                MeditationStage("Effortlessness", "Practicing effortless meditation."),
                MeditationStage("Transcendence", "Achieving a state of inner peace.")
            )
        ),
        MeditationTechnique(
            R.drawable.progressive_relaxation_image,
            "Progressive Relaxation",
            "Morning Routine",
            listOf(
                MeditationStage("Body Scan", "Gradually scanning and relaxing each body part."),
                MeditationStage("Tension Release", "Identifying and releasing tension."),
                MeditationStage("Deep Relaxation", "Achieving deep relaxation state.")
            )
        ),
        MeditationTechnique(
            R.drawable.loving_kindness_meditation_image,
            "Loving-kindness Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Cultivating Love", "Focusing on feelings of love and compassion."),
                MeditationStage("Directed Thoughts", "Sending positive thoughts to others."),
                MeditationStage("Self-Love", "Directing loving-kindness towards oneself.")
            )
        ),
        MeditationTechnique(
            R.drawable.visualization_meditation_image,
            "Visualization Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Mental Imagery", "Forming peaceful and positive images."),
                MeditationStage("Sensory Engagement", "Engaging all senses in the imagery."),
                MeditationStage("Deep Immersion", "Fully immersing in the visualized scene.")
            )
        ),
        MeditationTechnique(
            R.drawable.vipassana_meditation_image,
            "Vipassana Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Self-Observation", "Observing the self in a non-attached way."),
                MeditationStage("Insight", "Gaining insight into the nature of reality."),
                MeditationStage("Mindfulness of Breath", "Focusing on the breath as an anchor.")
            )
        ),
        MeditationTechnique(
            R.drawable.zen_meditation_image,
            "Zen Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Sitting Quietly", "Sitting in quiet contemplation."),
                MeditationStage("Mind Emptiness", "Emptying the mind of thoughts."),
                MeditationStage("Present Moment", "Focusing on the present moment.")
            )
        ),
        MeditationTechnique(
            R.drawable.yoga_meditation_image,
            "Yoga Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Yogic Postures", "Practicing specific yoga postures."),
                MeditationStage("Breath Control", "Controlling the breath to calm the mind."),
                MeditationStage("Mind-Body Connection", "Enhancing the mind-body connection.")
            )
        ),
        MeditationTechnique(
            R.drawable.chakra_meditation_image,
            "Chakra Meditation",
            "Morning Routine",
            listOf(
                MeditationStage("Energy Centers", "Focusing on the body's energy centers."),
                MeditationStage("Balancing", "Balancing each of the chakras."),
                MeditationStage("Visualization", "Visualizing energy flow.")
            )
        ),
        MeditationTechnique(
            R.drawable.qigong_meditation_image,
            "Qigong Meditation",
            "Evening Routine",
            listOf(
                MeditationStage("Gentle Movements", "Performing gentle qigong movements."),
                MeditationStage("Energy Flow", "Focusing on the flow of energy."),
                MeditationStage("Breathing Techniques", "Using specific breathing techniques.")
            )
        ),
        MeditationTechnique(
            R.drawable.christian_contemplative_prayer_image,
            "Christian Contemplative Prayer",
            "Morning Routine",
            listOf(
                MeditationStage("Silent Prayer", "Engaging in silent, contemplative prayer."),
                MeditationStage("Scriptural Focus", "Focusing on scripture or spiritual texts."),
                MeditationStage("Connection with God", "Seeking a deeper connection with God.")
            )
        )
        // ... add more techniques if needed ...
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

