package com.patigayon.meditationguideapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.patigayon.meditationguideapp.databinding.ItemMeditationBinding

class MeditationAdapter(
    private val meditationTechniques: List<MeditationTechnique>,
    private val onItemClick: (MeditationTechnique) -> Unit
) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val binding = ItemMeditationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
        holder.bind(meditationTechniques[position])
    }

    override fun getItemCount(): Int = meditationTechniques.size

    class MeditationViewHolder(
        private val binding: ItemMeditationBinding,
        private val onItemClick: (MeditationTechnique) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(technique: MeditationTechnique) {
            binding.imageViewMeditation.setImageResource(technique.imageResource)
            binding.textViewMeditationTitle.text = technique.title
            binding.textViewMeditationCategory.text = technique.category
            binding.root.setOnClickListener { onItemClick(technique) }
        }
    }
}
