package com.example.pocketpaladinfinalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val display = findViewById<TextView>(R.id.display)
        val logoutButton = findViewById<Button>(R.id.logout)

        val user = auth.getCurrentUser();
        if (user == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
            display.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}