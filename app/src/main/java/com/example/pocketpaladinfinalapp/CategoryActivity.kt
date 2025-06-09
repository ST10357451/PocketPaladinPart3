package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DatePickerDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import android.widget.Toast


class CategoryActivity : AppCompatActivity() {

    private lateinit var navHome: ImageButton
    private lateinit var navExpenses: ImageButton
    private lateinit var navBudgetGoals: ImageButton
    private lateinit var navSettings: ImageButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var pieChart: PieChart
    private lateinit var db: FirebaseFirestore
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = mutableListOf<Category>()
    private lateinit var firestoreRepo: FirestoreRepo



    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        db = FirebaseFirestore.getInstance()

        firestoreRepo = FirestoreRepo()


        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navBudgetGoals = findViewById(R.id.navBudgetGoals)
        navSettings = findViewById(R.id.navSettings)

        setupNavigation()

        recyclerView = findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(categoryList)
        recyclerView.adapter = categoryAdapter

        pieChart = PieChart(this)
        loadCategories()

        // Add button opens AddCategoryActivity
        findViewById<ImageButton>(R.id.addButton).setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        // Filter button (you can later add filtering logic)
        findViewById<ImageButton>(R.id.filterButton).setOnClickListener {
           // showMonthPickerDialog()

        }
    }
//    //error starts here
//    private fun showMonthPickerDialog() {
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//
//        val dialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, _ ->
//            filterByMonth(selectedYear, selectedMonth)
//        }, year, month, 1)
//
//        // Hide the day picker
//        dialog.datePicker.findViewById<View>(
//            resources.getIdentifier("day", "id", "android")
//        )?.visibility = View.GONE
//
//        dialog.show()
//    }
//
//    private fun filterByMonth(year: Int, month: Int) {
//        val filtered = allCategories.filter { category ->
//            val categoryCalendar = Calendar.getInstance().apply {
//                timeInMillis = category.timestamp // Make sure `timestamp` is a Long
//            }
//            categoryCalendar.get(Calendar.YEAR) == year &&
//                    categoryCalendar.get(Calendar.MONTH) == month
//        }
//
//        recyclerView.adapter = CategoryAdapter(filtered)
//    }
//    //error ends here



    override fun onResume() {
        super.onResume()
        loadCategories()
    }


    private fun loadCategories() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestoreRepo.getAllCategories(userId)
            .get() // <-- this is important!
            .addOnSuccessListener { documents ->
                val categoryList = documents.mapNotNull { it.toObject(Category::class.java) }
                recyclerView.adapter = CategoryAdapter(categoryList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load categories", Toast.LENGTH_SHORT).show()
            }
    }


    private fun updatePieChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, "Spending")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val data = PieData(dataSet)
        data.setValueTextSize(12f)
        data.setValueTextColor(android.graphics.Color.BLACK)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    private fun setupNavigation() {
        navHome.setOnClickListener {
            // Navigate to View expense
            val intent = Intent(this, ViewExpenseActivity::class.java) // chage this to settigs activity
            startActivity(intent)
            finish()
        }
        navExpenses.setOnClickListener {
            // Navigate to add Expenses
            val intent = Intent(this, AddExpenseActivity::class.java) // chage this to settigs activity
            startActivity(intent)
        }
        navBudgetGoals.setOnClickListener {
            // Navigate to Budget Goals
            val intent = Intent(this, MainActivity::class.java) // chage this to settigs activity
            startActivity(intent)
        }
        navSettings.setOnClickListener {
            // Navigate to settings screen
            val intent = Intent(this, MainActivity::class.java) // chage this to settigs activity
            startActivity(intent)
        }
    }
}