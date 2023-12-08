package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityMainBinding
    private var meditationTechniques = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        categoryAdapter = CategoryAdapter(emptyList(), this::onCategoryClicked)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchMeditationsFromFirestore()
        setupBottomNavigationView()
        setupCategoryRecyclerView()
    }

    private fun onCategoryClicked(category: Category) {
        Toast.makeText(this, "Category Clicked: ${category.title}", Toast.LENGTH_SHORT).show()
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            Toast.makeText(this, "Clicked on ${category.title}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerCategories.adapter = categoryAdapter
        binding.recyclerCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        fetchCategoriesFromFirestore()
    }

    private fun fetchCategoriesFromFirestore() {
        db.collection("categories")
            .get()
            .addOnSuccessListener { documents ->
                val categories = documents.mapNotNull { it.toObject(Category::class.java) }
                categoryAdapter.updateCategories(categories)
                categoryAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching categories", exception)
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
                Log.e(TAG, "Error fetching meditations", exception)
            }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_discover -> {
                    replaceFragment(DiscoverFragment())
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