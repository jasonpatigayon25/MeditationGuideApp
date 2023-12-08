package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.ActivityMainBinding
import com.patigayon.meditationguideapp.databinding.CategoryCardBinding

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityMainBinding
    private var meditationTechniques = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchMeditationsFromFirestore()
        setupBottomNavigationView()
        setupCategoryButtons()
    }

    private fun setupCategoryButtons() {
        val categories = listOf("Stress", "Anxiety", "Rest", "Self-Esteem", "Chill", "Other")
        val categoryColors = mapOf(
            // Make sure these color resources exist in your colors.xml file
            "Stress" to R.color.stress_color,
            "Anxiety" to R.color.anxiety_color,
            "Rest" to R.color.rest_color,
            "Self-Esteem" to R.color.self_esteem_color,
            "Chill" to R.color.chill_color,
            "Other" to R.color.other_color
        )

        categories.forEach { category ->
            val categoryBinding = CategoryCardBinding.inflate(layoutInflater)
            categoryBinding.categoryButton.apply {
                text = category
                setBackgroundColor(categoryColors[category] ?: R.color.other_color)
                setOnClickListener {
                    // Handle category button click
                }
            }
            binding.layoutCategories.addView(categoryBinding.root)
        }
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(meditationTechniques) { technique ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("name", technique.name)
                putExtra("routine", technique.routine)
                putExtra("photo", technique.photo)
                putExtra("description", technique.description)
            }
            startActivity(intent)
        }
        binding.recyclerMeditationTechniques.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMeditationTechniques.adapter = meditationAdapter
    }

    private fun fetchMeditationsFromFirestore() {
        db.collection("meditations")
            .get()
            .addOnSuccessListener { documents ->
                meditationTechniques.clear()
                meditationTechniques.addAll(documents.mapNotNull { it.toObject(MeditationTechnique::class.java) })
                meditationAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Log or handle the exception as necessary
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