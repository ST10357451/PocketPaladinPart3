package com.example.pocketpaladinfinalapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userOwnerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userOwnerId"), Index("categoryOwnerId")]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) val expenseId: Int = 0,
    val userOwnerId: Int,
    val categoryOwnerId: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val amount: Double,
    val description: String,
    val photo: String? // URI or filepath
)
