package com.example.module_3handson_2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge for a modern, immersive look
        enableEdgeToEdge()
        
        setContentView(R.layout.activity_main)
        
        // Apply window insets to handle system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply bottom padding to avoid navigation bar overlap
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the Extended Floating Action Button
        val fab: ExtendedFloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            Toast.makeText(this, getString(R.string.task_created), Toast.LENGTH_SHORT).show()
        }

        // Set up the Start button click listener
        val btnStart: Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            Toast.makeText(this, "Starting your next challenge...", Toast.LENGTH_SHORT).show()
        }
    }
}
