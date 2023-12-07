package com.patigayon.meditationguideapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityMainBinding
    private var meditationTechniques = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchMeditationsFromFirestore() // Fetch meditation data from Firestore
        setupBottomNavigationView()
        setupCategoryButtons()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupCategoryButtons() {
        val categories = listOf("Stress", "Anxiety", "Rest", "Self-Esteem", "Chill", "Other")
        val categoryColors = mapOf(
            "Stress" to R.color.stress_color,
            "Anxiety" to R.color.anxiety_color,
            "Rest" to R.color.rest_color,
            "Self-Esteem" to R.color.self_esteem_color,
            "Chill" to R.color.chill_color,
            "Other" to R.color.other_color
        )

        categories.forEach { category ->
            val cardView = layoutInflater.inflate(R.layout.category_card, binding.layoutCategories, false) as CardView
            val button: Button = cardView.findViewById(R.id.category_button)
            button.text = category
            button.setTextColor(ContextCompat.getColor(this, R.color.maroon))
            val colorResId = categoryColors[category] ?: R.color.other_color
            button.setBackgroundColor(ContextCompat.getColor(this, colorResId))
            button.setOnClickListener {
                // Handle category button click
            }
            binding.layoutCategories.addView(cardView)
        }
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(meditationTechniques) { technique ->
            // Handle click on meditation technique
        }
        binding.recyclerMeditationTechniques.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = meditationAdapter
        }
    }

    private fun fetchMeditationsFromFirestore() {
        db.collection("meditations")
            .get()
            .addOnSuccessListener { documents ->
                val newTechniques = documents.map { document ->
                    document.toObject(MeditationTechnique::class.java)
                }
                meditationAdapter.updateTechniques(newTechniques)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_discover -> {
                    replaceFragment(EveningFragment())
                    true
                }
                R.id.navigation_my_meditations -> {
                    replaceFragment(MyMeditationFragment())
                    true
                }
                R.id.navigation_morning -> {
                    replaceFragment(MorningFragment())
                    true
                }
                R.id.navigation_evening -> {
                    replaceFragment(EveningFragment())
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
