package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.FragmentEveningBinding

class EveningFragment : Fragment() {

    private var _binding: FragmentEveningBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private var eveningMeditations = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEveningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchEveningMeditationsFromFirestore()
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(eveningMeditations) { technique ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("name", technique.name)
                putExtra("routine", technique.routine)
                putExtra("photo", technique.photo)
                putExtra("description", technique.description)
                putExtra("category", technique.category)
            }
            startActivity(intent)
        }
        binding.recyclerEveningMeditations.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerEveningMeditations.adapter = meditationAdapter
    }

    private fun fetchEveningMeditationsFromFirestore() {
        db.collection("meditations")
            .whereEqualTo("routine", "Evening Routine") // Filter by the 'routine' field
            .get()
            .addOnSuccessListener { documents ->
                eveningMeditations.clear()
                eveningMeditations.addAll(documents.mapNotNull { it.toObject(MeditationTechnique::class.java) })
                meditationAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                //
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
