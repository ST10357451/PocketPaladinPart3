package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ViewExpenseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirestoreRepo
    private lateinit var adapter: ExpenseAdapter
    private lateinit var backbtn: ImageView
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        auth = FirebaseAuth.getInstance()
        repo = FirestoreRepo()

        backbtn = findViewById(R.id.btnBack)
        val userId = auth.currentUser?.uid ?: return

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        repo.getAllExpenses(userId).addOnSuccessListener { result ->
            Log.d("EXPENSE_DEBUG", "Documents fetched: ${result.size()}")
            expenses.clear()
            for (doc in result) {
                Log.d("EXPENSE_DEBUG", "Raw doc: ${doc.data}")
                val expense = doc.toObject(Expense::class.java)
                expense?.let {
                    Log.d("EXPENSE_DEBUG", "Parsed Expense: ${it.description}, ${it.amount}")
                    expenses.add(it)
                }
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Log.e("EXPENSE_DEBUG", "Error fetching expenses", it)
        }


        backbtn.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
