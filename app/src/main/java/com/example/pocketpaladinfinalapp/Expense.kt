package com.example.pocketpaladinfinalapp

data class Expense(
    val expenseId: String = "",
    val date: String = "",         // ISO or timestamp
    val startTime: String = "",
    val endTime: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val photoUrl: String? = null,
    val categoryId: String = ""
)