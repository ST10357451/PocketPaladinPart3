package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewExpenseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirestoreRepo
    private lateinit var adapter: ExpenseAdapter
    private lateinit var backbtn: ImageView
    private lateinit var recyclerView: RecyclerView

    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        auth = FirebaseAuth.getInstance()
        repo = FirestoreRepo()

        backbtn = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.recyclerViewExpenses)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        loadExpenses()

        // Navigate back to CategoryActivity
        backbtn.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun loadExpenses() {
        val userId = auth.currentUser?.uid ?: return

        repo.getAllExpenses(userId)
            .addOnSuccessListener { result ->
                Log.d("EXPENSE_DEBUG", "Documents fetched: ${result.size()}")
                expenses.clear()
                for (doc in result) {
                    val expense = doc.toObject(Expense::class.java)
                    if (expense != null) {
                        Log.d("EXPENSE_DEBUG", "Loaded expense: ${expense.description}, ${expense.amount}")
                        expenses.add(expense)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("EXPENSE_DEBUG", "Error loading expenses", it)
                Toast.makeText(this, "Failed to load expenses", Toast.LENGTH_SHORT).show()
            }
    }
}
