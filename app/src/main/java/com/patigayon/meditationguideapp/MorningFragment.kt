package com.patigayon.meditationguideapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.patigayon.meditationguideapp.databinding.FragmentMorningBinding

class MorningFragment : Fragment() {

    private var _binding: FragmentMorningBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private var morningMeditations = mutableListOf<MeditationTechnique>()
    private lateinit var meditationAdapter: MeditationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMorningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchMorningMeditationsFromFirestore()
    }

    private fun setupRecyclerView() {
        meditationAdapter = MeditationAdapter(morningMeditations) { technique ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("name", technique.name)
                putExtra("routine", technique.routine)
                putExtra("photo", technique.photo)
                putExtra("description", technique.description)
            }
            startActivity(intent)
        }
        binding.recyclerMorningMeditations.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMorningMeditations.adapter = meditationAdapter
    }


    private fun fetchMorningMeditationsFromFirestore() {
        db.collection("meditations")
            .whereEqualTo("routine", "Morning Routine")
            .get()
            .addOnSuccessListener { documents ->
                morningMeditations.clear()
                morningMeditations.addAll(documents.mapNotNull { it.toObject(MeditationTechnique::class.java) })
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
