package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetapp.com.example.pocketpaladinapp.SettingsActivity


class ExpensesActivity : AppCompatActivity() {

    private lateinit var buttonBack: ImageButton
    private lateinit var buttonAddExpense: ImageButton
    private lateinit var buttonEditBudget: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textCurrentSpent: TextView
    private lateinit var textMaxBudget: TextView

    private lateinit var navHome: ImageButton
    private lateinit var navBudgetGoals: ImageButton
    private lateinit var navExpenses: ImageButton
    private lateinit var navPoints: ImageButton
    private lateinit var navSettings: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense_progress_bar)

        // Top bar buttons
        buttonBack = findViewById(R.id.buttonBack)
        buttonAddExpense = findViewById(R.id.buttonAddExpense)

        // Budget progress section
        buttonEditBudget = findViewById(R.id.buttonEditBudget)
        progressBar = findViewById(R.id.budgetProgressBar)
        textCurrentSpent = findViewById(R.id.textCurrentSpent)
        textMaxBudget = findViewById(R.id.textMaxBudget)

        // Bottom navigation buttons
        navHome = findViewById(R.id.navHome)
        navBudgetGoals = findViewById(R.id.navBudgetGoals)
        navExpenses = findViewById(R.id.navExpenses)
        navPoints = findViewById(R.id.navPoints)
        navSettings = findViewById(R.id.navSettings)

        setupListeners()
    }



    private fun setupListeners() {
        buttonBack.setOnClickListener {
            finish()
        }

        // Add button → Add Expense Page
        buttonAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Edit Budget button → Activity Points Screen
        buttonEditBudget.setOnClickListener {
            startActivity(Intent(this, PointsScreen::class.java))
        }

        // Bottom Nav: Home (finish this screen)
        navHome.setOnClickListener {
            finish()
        }

        // Budget Goals → Expense Category Page
        navBudgetGoals.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        // Points → Activity Points Screen
        navPoints.setOnClickListener {
            startActivity(Intent(this, PointsScreen::class.java))
        }

        // Settings → Settings Screen
        navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
