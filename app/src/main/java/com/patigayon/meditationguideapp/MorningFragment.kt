package com.patigayon.meditationguideapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.patigayon.meditationguideapp.databinding.FragmentMorningBinding

class MorningFragment : Fragment() {

    private var _binding: FragmentMorningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMorningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize your UI components here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
