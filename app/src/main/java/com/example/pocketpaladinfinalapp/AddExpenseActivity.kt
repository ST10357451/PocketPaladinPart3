package com.example.pocketpaladinfinalapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirestoreRepo
    private var selectedPhotoUri: Uri? = null
    private val calendar = Calendar.getInstance()
    private lateinit var categoryMap: Map<String, String> // name -> id
    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        auth = FirebaseAuth.getInstance()
        repo = FirestoreRepo()

        val userId = auth.currentUser?.uid ?: return
        val spinnerCategory: Spinner = findViewById(R.id.spinnerCategory)
        val etDate: EditText = findViewById(R.id.etDate)
        val etStartTime: EditText = findViewById(R.id.etStartTime)
        val etEndTime: EditText = findViewById(R.id.etEndTime)
        val btnSelectPhoto: ImageButton = findViewById(R.id.btnSelectPhoto)
        val btnSave: Button = findViewById(R.id.btnSaveExpense)

        // Load categories into spinner
        repo.getAllCategories(userId).get().addOnSuccessListener { result ->
            val categories = result.toObjects(Category::class.java)
            categoryMap = categories.associate { it.categoryName to it.categoryId }
            val categoryNames = categories.map { it.categoryName }
            spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        }

        // Date picker
        etDate.setOnClickListener {
            val dp = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    etDate.setText("$dayOfMonth/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )
            dp.show()
        }

        // Start time picker
        etStartTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                etStartTime.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        // End time picker
        etEndTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                etEndTime.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        // Photo picker
        btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 101)
        }

        // Save expense
        btnSave.setOnClickListener {
            val description = findViewById<EditText>(R.id.etDescription).text.toString()
            val date = etDate.text.toString()
            val startTime = etStartTime.text.toString()
            val endTime = etEndTime.text.toString()
            val amount = findViewById<EditText>(R.id.etAmount).text.toString().toDoubleOrNull() ?: 0.0
            val categoryName = spinnerCategory.selectedItem?.toString() ?: return@setOnClickListener
            val categoryId = categoryMap[categoryName] ?: return@setOnClickListener

            if (selectedPhotoUri != null) {
                uploadPhotoAndSaveExpense(
                    userId, categoryId, date, startTime, endTime, amount, description, selectedPhotoUri!!
                )
            } else {
                saveExpense(
                    userId, categoryId, date, startTime, endTime, amount, description, null
                )
            }
        }
    }

    private fun uploadPhotoAndSaveExpense(
        userId: String,
        categoryId: String,
        date: String,
        startTime: String,
        endTime: String,
        amount: Double,
        description: String,
        photoUri: Uri
    ) {
        val photoRef = storage.reference.child("expenses/$userId/${UUID.randomUUID()}.jpg")
        photoRef.putFile(photoUri).addOnSuccessListener {
            photoRef.downloadUrl.addOnSuccessListener { uri ->
                saveExpense(userId, categoryId, date, startTime, endTime, amount, description, uri.toString())
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to get image URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveExpense(
        userId: String,
        categoryId: String,
        date: String,
        startTime: String,
        endTime: String,
        amount: Double,
        description: String,
        photoUrl: String?
    ) {
        val expense = hashMapOf(
            "date" to date,
            "startTime" to startTime,
            "endTime" to endTime,
            "amount" to amount,
            "description" to description,
            "photoUrl" to photoUrl,
            "userOwnerId" to userId
        )

        firestore.collection("users")
            .document(userId)
            .collection("categories")
            .document(categoryId)
            .collection("expenses")
            .add(expense)
            .addOnSuccessListener {
                Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            selectedPhotoUri = data?.data
            Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show()
        }
    }
}
