package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private var meditationTechniques = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchMeditationsFromFirestore()
        setupCategoryRecyclerView()
    }

    private fun onCategoryClicked(category: Category) {
        val fragment = CategoryMeditationsFragment.newInstance(category.title)
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(emptyList(), this::onCategoryClicked)
        binding.recyclerCategories.adapter = categoryAdapter
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("name", technique.name)
                putExtra("routine", technique.routine)
                putExtra("photo", technique.photo)
                putExtra("description", technique.description)
                putExtra("category", technique.category)
            }
            startActivity(intent)
        }
        binding.recyclerMeditationTechniques.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}