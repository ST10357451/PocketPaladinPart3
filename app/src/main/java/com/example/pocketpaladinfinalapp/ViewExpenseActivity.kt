package com.example.pocketpaladinfinalapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ViewExpenseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirestoreRepo
    private lateinit var adapter: ExpenseAdapter
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        auth = FirebaseAuth.getInstance()
        repo = FirestoreRepo()
        val userId = auth.currentUser?.uid ?: return

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        repo.getAllExpenses(userId).get().addOnSuccessListener { result ->
            expenses.clear()
            for (doc in result.documents) {
                val expense = doc.toObject(Expense::class.java)
                expense?.let { expenses.add(it) }
            }
            adapter.notifyDataSetChanged()
        }

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
