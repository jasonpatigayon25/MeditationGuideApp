package com.patigayon.meditationguideapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityMainBinding
    private var meditationTechniques = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())  // Load HomeFragment as default
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
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
