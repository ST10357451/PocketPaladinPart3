package com.example.pocketpaladinfinalapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var editTextCategoryName: EditText
    private lateinit var saveCategoryButton: Button
    private lateinit var firestoreRepo: FirestoreRepo
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        // Initialize views
        backIcon = findViewById(R.id.backIcon)
        editTextCategoryName = findViewById(R.id.categoryNameEditText)
        saveCategoryButton = findViewById(R.id.saveCategoryButton)

        // Initialize Firebase Auth and Repo
        auth = FirebaseAuth.getInstance()
        firestoreRepo = FirestoreRepo()

        backIcon.setOnClickListener {
            finish()
        }

        saveCategoryButton.setOnClickListener {
            saveCategory()
        }
    }

    private fun saveCategory() {
        val categoryName = editTextCategoryName.text.toString().trim()
        val userId = auth.currentUser?.uid

        // Validation
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val newCategory = Category(
            categoryId = "", // FirestoreRepo will assign it
            userId = userId,
            categoryName = categoryName,
            categoryTotal = 0.0,
            month = getCurrentMonth()
        )

        // Save category via FirestoreRepo
        firestoreRepo.addCategory(userId, newCategory)
            .addOnSuccessListener {
                Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
                finish() // Return to previous screen
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save category: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCurrentMonth(): String {
        val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return formatter.format(Date())
    }
}
