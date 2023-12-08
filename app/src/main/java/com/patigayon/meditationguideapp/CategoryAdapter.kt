package com.patigayon.meditationguideapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patigayon.meditationguideapp.databinding.CategoryCardBinding

class CategoryAdapter(private var categories: List<Category>, private val onClick: (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    fun updateData(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(private val binding: CategoryCardBinding, val onClick: (Category) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.categoryTitle.text = category.title
            Glide.with(binding.root.context)
                .load(category.photo)
                .into(binding.categoryImage)

            itemView.setOnClickListener { onClick(category) }
        }
    }
}