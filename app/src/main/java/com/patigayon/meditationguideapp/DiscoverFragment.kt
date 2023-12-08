package com.patigayon.meditationguideapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.patigayon.meditationguideapp.databinding.FragmentDiscoverBinding
import com.google.firebase.firestore.FirebaseFirestore

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}