package com.example.pocketpaladinfinalapp

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreRepo(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private fun userRef(userId: String) = db.collection("users").document(userId)

    // ========== USERS ==========
    fun createUser(user: User): Task<Void> {
        return userRef(user.userId).set(user)
    }

    fun getUser(userId: String): DocumentReference {
        return userRef(userId)
    }

    // ========== CATEGORIES ==========

    fun addCategory(userId: String, category: Category): Task<Void> {
        val categoryId = db.collection("dummy").document().id // Generate ID
        val updatedCategory = category.copy(categoryId = categoryId)
        return userRef(userId)
            .collection("categories")
            .document(categoryId)
            .set(updatedCategory)
    }

    fun getAllCategories(userId: String): CollectionReference {
        return userRef(userId).collection("categories")
    }

    fun getCategoriesForMonth(userId: String, month: String): Query {
        return userRef(userId)
            .collection("categories")
            .whereEqualTo("month", month)
    }

    fun updateCategory(userId: String, category: Category): Task<Void> {
        return userRef(userId)
            .collection("categories")
            .document(category.categoryId)
            .set(category)
    }

    fun deleteCategory(userId: String, categoryId: String): Task<Void> {
        return userRef(userId)
            .collection("categories")
            .document(categoryId)
            .delete()
    }

    // ========== EXPENSES ==========

    fun addExpense(userId: String, expense: Expense): Task<Void> {
        val expenseId = db.collection("dummy").document().id // Generate ID
        val updatedExpense = expense.copy(expenseId = expenseId)
        return userRef(userId)
            .collection("expenses")
            .document(expenseId)
            .set(updatedExpense)
    }

    fun getAllExpenses(userId: String): CollectionReference {
        return userRef(userId).collection("expenses")
    }

    fun getExpensesForMonth(userId: String, month: String): Query {
        return userRef(userId)
            .collection("expenses")
            .whereEqualTo("month", month)
    }

    fun getExpensesForCategory(userId: String, categoryId: String): Query {
        return userRef(userId)
            .collection("expenses")
            .whereEqualTo("categoryOwnerId", categoryId)
    }

    fun deleteExpense(userId: String, expenseId: String): Task<Void> {
        return userRef(userId)
            .collection("expenses")
            .document(expenseId)
            .delete()
    }

    fun updateExpense(userId: String, expense: Expense): Task<Void> {
        return userRef(userId)
            .collection("expenses")
            .document(expense.expenseId)
            .set(expense)
    }
}
