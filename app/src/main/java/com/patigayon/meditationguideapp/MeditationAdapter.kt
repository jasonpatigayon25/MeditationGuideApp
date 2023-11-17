package com.patigayon.meditationguideapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.patigayon.meditationguideapp.databinding.ItemMeditationBinding

class MeditationAdapter(
    private val meditationTechniques: List<String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val binding = ItemMeditationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
        val meditationName = meditationTechniques[position]
        holder.bind(meditationName)
    }

    override fun getItemCount(): Int = meditationTechniques.size

    inner class MeditationViewHolder(private val binding: ItemMeditationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }

        fun bind(meditationName: String) {
            binding.textViewMeditationName.text = meditationName
        }
    }
}
