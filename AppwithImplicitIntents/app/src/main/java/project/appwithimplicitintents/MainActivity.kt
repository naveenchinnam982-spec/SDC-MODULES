package project.appwithimplicitintents

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : package com.example.implicitintents

// Import required libraries
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect XML layout with Kotlin
        setContentView(R.layout.activity_main)

        // Linking buttons from XML
        val btnWebsite = findViewById<Button>(R.id.btnWebsite)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val btnEmail = findViewById<Button>(R.id.btnEmail)

        // ===============================
        // 1️⃣ OPEN WEBSITE
        // ===============================
        btnWebsite.setOnClickListener {

            // Create Intent with ACTION_VIEW
            val intent = Intent(Intent.ACTION_VIEW)

            // Provide website URL
            intent.data = Uri.parse("https://www.google.com")

            // Start activity (opens browser)
            startActivity(intent)
        }

        // ===============================
        // 2️⃣ SHARE TEXT
        // ===============================
        btnShare.setOnClickListener {

            // Create Intent with ACTION_SEND
            val intent = Intent(Intent.ACTION_SEND)

            // Set type of data
            intent.type = "text/plain"

            // Add text to share
            intent.putExtra(Intent.EXTRA_TEXT, "Hello! This is my app sharing text.")

            // Show app chooser (WhatsApp, etc.)
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        // ===============================
        // 3️⃣ SEND EMAIL
        // ===============================
        btnEmail.setOnClickListener {

            // Create Intent with ACTION_SENDTO
            val intent = Intent(Intent.ACTION_SENDTO)

            // Use mailto:
            intent.data = Uri.parse("mailto:")

            // Add email details
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("example@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Test Email")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello from my app!")

            // Start email app
            startActivity(intent)
        }
    }
}ppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}