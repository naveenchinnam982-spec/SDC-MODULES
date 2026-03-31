package project.loginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val cardProfile = findViewById<MaterialCardView>(R.id.cardProfile)
        val cardSettings = findViewById<MaterialCardView>(R.id.cardSettings)
        val cardActivity = findViewById<MaterialCardView>(R.id.cardActivity)
        val cardReports = findViewById<MaterialCardView>(R.id.cardReports)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val user = auth.currentUser
        val name = user?.displayName ?: "User"
        tvWelcome.text = "Welcome, $name!"

        cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        cardSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        cardActivity.setOnClickListener {
            startActivity(Intent(this, ActivityLogActivity::class.java))
        }

        cardReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}
