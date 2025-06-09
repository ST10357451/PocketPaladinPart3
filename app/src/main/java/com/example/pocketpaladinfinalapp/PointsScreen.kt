package com.example.pocketpaladinfinalapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PointsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.points_screen)

        val totalSpent = intent.getDoubleExtra("totalSpent", 0.0)
        val maxBudget = intent.getDoubleExtra("maxBudget", 0.0)
        val score = ((maxBudget - totalSpent) * 10 / 2).coerceAtLeast(0.0).toInt()

        findViewById<TextView>(R.id.pointsText).text = "You scored $score points!"
    }
}
