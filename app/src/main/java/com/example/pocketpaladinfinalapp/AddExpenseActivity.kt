package com.example.pocketpaladinfinalapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirestoreRepo
    private var selectedPhotoUri: Uri? = null
    private val calendar = Calendar.getInstance()
    private lateinit var categoryMap: Map<String, String> // name to id

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

        //*// Load categories into spinner
        //        repo.getAllCategories(userId).get().addOnSuccessListener { result ->
        //            val categories = result.toObjects(Category::class.java)
        //            categoryMap = categories.associate { it.name to it.categoryId }
        //            val categoryNames = categories.map { it.name }
        //            spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        //        }*//

        // Date picker
        etDate.setOnClickListener {
            val dp = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    etDate.setText("$dayOfMonth/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dp.show()
        }

        // Time pickers
        etStartTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                etStartTime.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        etEndTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                etEndTime.setText(String.format("%02d:%02d", hour, minute))
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        // Image picker
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
            val categoryName = spinnerCategory.selectedItem.toString()
            val categoryId = categoryMap[categoryName] ?: return@setOnClickListener

            val expense = Expense(
                date = date,
                startTime = startTime,
                endTime = endTime,
                amount = amount,
                description = description,
                categoryId = categoryId,
                photoUrl = selectedPhotoUri?.toString()
            )

            repo.addExpense(userId, expense).addOnSuccessListener {
                Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            selectedPhotoUri = data?.data
        }
    }
}
