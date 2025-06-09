package com.example.pocketpaladinfinalapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        // val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto) // if using photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]

        holder.tvDescription.text = expense.description
        holder.tvCategory.text = "Category Name: ${expense.categoryId}"  // change to category name later
        holder.tvDate.text = "Date: ${expense.date}"
        holder.tvTime.text = "Time: ${expense.startTime} - ${expense.endTime}"
        holder.tvAmount.text = "R%.2f".format(expense.amount)

        Log.d("ADAPTER_DEBUG", "Binding expense: ${expense.description}, amount: ${expense.amount}")
    }

    override fun getItemCount(): Int = expenses.size
}
