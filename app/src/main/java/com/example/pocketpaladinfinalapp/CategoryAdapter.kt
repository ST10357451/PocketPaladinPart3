package com.example.pocketpaladinfinalapp

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


class CategoryAdapter(
    private val categoryList: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.categoryName)
        val totalTextView: TextView = itemView.findViewById(R.id.categoryTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.nameTextView.text = category.categoryName
        holder.totalTextView.text = "R ${"%.2f".format(category.categoryTotal)}"
    }

    override fun getItemCount(): Int = categoryList.size
}
