package project.loginpage

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etName = findViewById<TextInputEditText>(R.id.etProfileName)
        val etEmail = findViewById<TextInputEditText>(R.id.etProfileEmail)
        val tvUid = findViewById<TextView>(R.id.tvUid)
        val tvCreatedDate = findViewById<TextView>(R.id.tvCreatedDate)
        val tvLastSignIn = findViewById<TextView>(R.id.tvLastSignIn)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateProfile)
        val btnBack = findViewById<Button>(R.id.btnBackToDashboard)

        val user = auth.currentUser
        
        if (user != null) {
            // Basic Info
            etName.setText(user.displayName)
            etEmail.setText(user.email)
            etEmail.isEnabled = false

            // Total Information (Metadata)
            tvUid.text = "User UID: ${user.uid}"
            
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            
            val createTimestamp = user.metadata?.creationTimestamp ?: 0
            if (createTimestamp > 0) {
                tvCreatedDate.text = "Account Created: ${sdf.format(Date(createTimestamp))}"
            }

            val lastSignInTimestamp = user.metadata?.lastSignInTimestamp ?: 0
            if (lastSignInTimestamp > 0) {
                tvLastSignIn.text = "Last Login: ${sdf.format(Date(lastSignInTimestamp))}"
            }
        }

        btnUpdate.setOnClickListener {
            val newName = etName.text.toString().trim()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // 1. Update Firebase Auth Profile
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 2. Also update Firestore to keep it in sync
                            val userId = user.uid
                            db.collection("users").document(userId)
                                .update("name", newName)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Profile Updated Everywhere!", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "Update Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
