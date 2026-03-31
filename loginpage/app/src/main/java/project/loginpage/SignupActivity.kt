package project.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val nameField = findViewById<TextInputEditText>(R.id.etName)
        val emailField = findViewById<TextInputEditText>(R.id.etEmail)
        val passwordField = findViewById<TextInputEditText>(R.id.etPassword)
        val confirmPasswordField = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val loadingLayout = findViewById<FrameLayout>(R.id.loadingLayout)

        registerBtn.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val pass = passwordField.text.toString().trim()
            val confirmPass = confirmPasswordField.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (pass.length < 6) {
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show full screen loading overlay
            loadingLayout.visibility = View.VISIBLE

            // 1. Create User with Firebase Auth
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        
                        // 2. Save User details to Firestore (to see them in Firebase Console)
                        val userMap = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "password" to pass,
                            "uid" to userId
                        )
                        
                        // We save to Firestore but move forward regardless to prevent hanging
                        db.collection("users").document(userId)
                            .set(userMap)

                        // 3. Update Auth Profile Display Name and Finish
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        auth.currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                loadingLayout.visibility = View.GONE
                                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                    } else {
                        loadingLayout.visibility = View.GONE
                        // Handle specific "Email already in use" error
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "This email is already registered. Please log in.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}
