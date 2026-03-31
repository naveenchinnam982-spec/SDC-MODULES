package project.quizappwithmultiplescreens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val total = intent.getIntExtra("TOTAL_QUESTIONS", 0)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        tvResult.text = "You scored $finalScore out of $total"

        val btnRestart = findViewById<Button>(R.id.btnRestart)
        btnRestart.setOnClickListener {
            // Start the Quiz again
            val intent = Intent(this, MainActivity::class.java)
            // Clear activity stack so back button doesn't go back to result screen
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}