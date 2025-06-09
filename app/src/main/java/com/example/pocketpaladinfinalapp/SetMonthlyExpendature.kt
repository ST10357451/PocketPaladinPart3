package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SetMonthlyExpendature : AppCompatActivity() {

    private lateinit var Back: ImageButton
    private lateinit var LimitInput: EditText
    private lateinit var SaveLimit: Button
    private lateinit var CurrentLimit: TextView

    private var currentLimits: Double? = null
    private val currentMonth = "May 2025" // This could be dynamic if needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_monthly_expendature_page) // Adjust if your layout file has a different name

        // Initialize views
        Back = findViewById(R.id.btnBack)
        LimitInput = findViewById(R.id.etLimitInput)
        SaveLimit = findViewById(R.id.btnSaveLimit)
        CurrentLimit = findViewById(R.id.tvCurrentLimit)


        //Back button functionality
        Back.setOnClickListener {
            val intent = Intent(this, ViewExpenses::class.java)
            startActivity(intent)
            finish() // Optional: closes current activity so user can't go back with hardware back button
        }


        // Save limit logic
        SaveLimit.setOnClickListener {
            val limitText = LimitInput.text.toString().trim()
            if (limitText.isEmpty()) {
                Toast.makeText(this, "Please enter a valid limit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                currentLimits = limitText.toDouble()
                updateCurrentLimitDisplay()
                Toast.makeText(this, "Limit saved successfully", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
            }
        }

        // Optional: Load previously saved limit here (e.g., from SharedPreferences)
    }

    private fun updateCurrentLimitDisplay() {
        CurrentLimit.text = "Current Limit for $currentMonth: \$${"%.2f".format(currentLimits)}"
    }


}