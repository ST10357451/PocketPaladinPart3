package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth


class SettingActivity : AppCompatActivity()  {
    private lateinit var emailField: EditText
    private lateinit var themeSwitch: Switch
    private lateinit var logoutButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var changePasswordButton: Button
    private lateinit var deleteAccountButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // View bindings
        emailField = findViewById(R.id.emailField)
        themeSwitch = findViewById(R.id.themeSwitch)
        logoutButton = findViewById(R.id.logoutButton)
        backButton = findViewById(R.id.backButton)
        changePasswordButton = findViewById(R.id.changePasswordButton)
        deleteAccountButton = findViewById(R.id.deleteAccountButton)

        // Load saved theme preference
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveThemePreference(isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            recreate()
        }


        // Set current user email (update with Firebase/Auth logic)
        loadUserEmail()

        // Theme switcher
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveThemePreference(isChecked)
            applyTheme(isChecked)
        }

        // Back button
        backButton.setOnClickListener {
        finish()// Go back to previous screen
        }

        // Logout logic (replace with actual Auth logout if needed)
        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Change password
        changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Change password feature coming soon.", Toast.LENGTH_SHORT).show()
        }

        // Delete account
        deleteAccountButton.setOnClickListener {
            Toast.makeText(this, "Delete account feature coming soon.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val email = user.email
            emailField.setText(email)
        } else {
            emailField.setText("No user logged in")
        }
    }


    private fun saveThemePreference(enabled: Boolean) {
        val editor: SharedPreferences.Editor = getSharedPreferences("settings", MODE_PRIVATE).edit()
        editor.putBoolean("dark_mode", enabled)
        editor.apply()
    }

    private fun applyTheme(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}