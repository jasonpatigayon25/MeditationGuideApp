package com.patigayon.meditationguideapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patigayon.meditationguideapp.databinding.ItemMeditationBinding

class MeditationAdapter(
    private var techniques: List<MeditationTechnique>,
    private val onClick: (MeditationTechnique) -> Unit
) : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    fun updateTechniques(newTechniques: List<MeditationTechnique>) {
        techniques = newTechniques
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meditation, parent, false)
        return MeditationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
        val technique = techniques[position]
        holder.bind(technique, onClick)
    }

    override fun getItemCount(): Int = techniques.size

    class MeditationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameView: TextView = itemView.findViewById(R.id.meditationName)
        private val routineView: TextView = itemView.findViewById(R.id.meditationRoutine)
        private val imageView: ImageView = itemView.findViewById(R.id.meditationImage)

        fun bind(technique: MeditationTechnique, onClick: (MeditationTechnique) -> Unit) {
            nameView.text = technique.name
            routineView.text = technique.routine
            Glide.with(itemView.context).load(technique.photo).into(imageView)
            itemView.setOnClickListener { onClick(technique) }
        }
    }
}

