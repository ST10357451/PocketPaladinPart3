package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val emailField = findViewById<EditText>(R.id.emailEditText)
        val passwordField = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val registerLink = findViewById<TextView>(R.id.registerLink)
        val forgotPassLink = findViewById<TextView>(R.id.forgotPasswordLink)

        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please, Enter Credentials!", Toast.LENGTH_LONG).show()
            }
            else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // If sign in succeeds, display a message to the user.
                            Toast.makeText(this,"Welcome!",Toast.LENGTH_SHORT,).show()
                            //redirect to dashboard
                            val intent = Intent(this, MainActivity::class.java)
                            //intent.putExtra("userId", user.userId)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Authentication failed.",Toast.LENGTH_SHORT,).show()
                        }
                    }
            }
        }

        forgotPassLink.setOnClickListener {
            val toast = Toast.makeText(this, "Sorry, this is not available at the moment", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }

        registerLink.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}