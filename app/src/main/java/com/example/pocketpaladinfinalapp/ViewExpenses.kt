package com.example.pocketpaladinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.pocketpaladinfinalapp.ExpenseAdapter

class ViewExpenseActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var db: AppDatabase
    private lateinit var expenseDao: ExpenseDao
    private var userId: Int = 1 // Replace with actual user ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        recyclerView = findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = ExpenseAdapter(emptyList())
        recyclerView.adapter = expenseAdapter

        db = AppDatabase.getDatabase(this)
        expenseDao = db.expenseDao()

        loadExpenses()
    }

    private fun loadExpenses() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allExpenses = db.expenseDao().getAllExpensesOrderedByCategory(userId)
            launch(Dispatchers.Main) {
                expenseAdapter.updateData(allExpenses)
            }
        }
    }
}