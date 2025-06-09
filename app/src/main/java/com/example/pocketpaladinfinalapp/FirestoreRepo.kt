package com.example.pocketpaladinfinalapp

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreRepo (
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun userRef(userId: String) = db.collection("users").document(userId)

    fun createUser(user: User) =
        userRef(user.userId).set(user)

    fun addCategory(userId: String, category: Category) =
        userRef(userId)
            .collection("categories")
            .document(category.categoryId)
            .set(category)

    fun addExpense(userId: String, expense: Expense) =
        userRef(userId)
            .collection("expenses")
            .document(expense.expenseId)
            .set(expense)

    fun getCategories(userId: String) =
        userRef(userId)
            .collection("categories")

    fun getExpenses(userId: String) =
        userRef(userId)
            .collection("expenses")
}