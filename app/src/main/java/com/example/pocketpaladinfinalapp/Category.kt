package com.example.pocketpaladinfinalapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userOwnerId")]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
    val userOwnerId: Int,
    val categoryName: String,
    val categoryTotal: Double
)
