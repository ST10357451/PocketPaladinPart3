package com.example.pocketpaladinfinalapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val userName: String,
    val email: String,
    val password: String
)
