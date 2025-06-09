package com.example.pocketpaladinfinalapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketpaladinfinalapp.R

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etDescription: EditText
    private lateinit var etCategory: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_page)

        etDescription = findViewById(R.id.etDescription)
        etCategory = findViewById(R.id.etCategory)
        etAmount = findViewById(R.id.etAmount)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            val description = etDescription.text.toString()
            val category = etCategory.text.toString()
            val amount = etAmount.text.toString().toDoubleOrNull()
            val date = etDate.text.toString()
            val time = etTime.text.toString()

            if (description.isNotEmpty() && category.isNotEmpty() && amount != null && date.isNotEmpty() && time.isNotEmpty()) {
                val expense = Expense(description, category, amount, date, time)
                val resultIntent = Intent()
                resultIntent.putExtra("expense", expense)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
