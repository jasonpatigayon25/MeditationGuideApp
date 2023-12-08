package com.patigayon.meditationguideapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.FragmentCategoryMeditationsBinding

class CategoryMeditationsFragment : Fragment() {

    private var _binding: FragmentCategoryMeditationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var meditationAdapter: MeditationAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryMeditationsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryTitle = arguments?.getString("category_title") ?: ""
        binding.tvCategoryTitle.text = "Category: $categoryTitle"
        setupRecyclerView()
        fetchMeditationsFromCategory(categoryTitle)

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(listOf()) { technique ->
            // When a meditation is clicked, start DetailActivity with the details of the clicked meditation
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("name", technique.name)
                putExtra("routine", technique.routine)
                putExtra("photo", technique.photo)
                putExtra("description", technique.description)
                // If sessions or any other data needs to be passed to DetailActivity, add them here
            }
            startActivity(intent)
        }
        binding.recyclerCategoryMeditations.adapter = meditationAdapter
        binding.recyclerCategoryMeditations.layoutManager = LinearLayoutManager(context)
    }

    private fun fetchMeditationsFromCategory(categoryTitle: String) {
        db.collection("meditations")
            .whereEqualTo("category", categoryTitle)
            .get()
            .addOnSuccessListener { documents ->
                val meditations = documents.mapNotNull { it.toObject(MeditationTechnique::class.java) }
                meditationAdapter.updateTechniques(meditations) // Assuming you have the updateData method in your adapter
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching meditations for category", exception)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(categoryTitle: String): CategoryMeditationsFragment {
            val fragment = CategoryMeditationsFragment()
            val args = Bundle()
            args.putString("category_title", categoryTitle)
            fragment.arguments = args
            return fragment
        }
    }
}
