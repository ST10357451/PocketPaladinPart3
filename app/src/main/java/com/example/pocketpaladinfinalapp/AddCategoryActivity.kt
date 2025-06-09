package com.example.pocketpaladinfinalapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class AddCategoryActivity : AppCompatActivity() {

    private lateinit var editTextCategoryName: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category) // Replace with your actual layout file

        editTextCategoryName = findViewById(R.id.categoryNameEditText) // Update with actual ID
        db = FirebaseFirestore.getInstance()

        val saveButton = findViewById<Button>(R.id.saveCategoryButton) // Update with actual ID
        saveButton.setOnClickListener {
            saveCategory()
        }
    }

    private fun saveCategory() {
        val categoryName = editTextCategoryName.text.toString().trim()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val category = Category(
            categoryId = "", // Firestore will auto-generate
            userId = userId,
            categoryName = categoryName,
            categoryTotal = 0.0,
            month = getCurrentMonth()
        )

        db.collection("categories").add(category)
            .addOnSuccessListener {
                Toast.makeText(this, "Category Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save category", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCurrentMonth(): String {
        val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return formatter.format(Date())
    }
}
