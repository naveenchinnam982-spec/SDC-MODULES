package project.hands_2casestudy_1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var isLoginMode = true

    private lateinit var tvTitle: TextView
    private lateinit var layoutName: TextInputLayout
    private lateinit var layoutEmail: TextInputLayout
    private lateinit var layoutPassword: TextInputLayout
    private lateinit var edtName: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnAction: MaterialButton
    private lateinit var btnToggleMode: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tv_title)
        layoutName = findViewById(R.id.layout_name)
        layoutEmail = findViewById(R.id.layout_email)
        layoutPassword = findViewById(R.id.layout_password)
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnAction = findViewById(R.id.btn_action)
        btnToggleMode = findViewById(R.id.btn_toggle_mode)
    }

    private fun setupListeners() {
        btnToggleMode.setOnClickListener {
            toggleMode()
        }

        btnAction.setOnClickListener {
            if (validateInputs()) {
                val message = if (isLoginMode) "Login Success!" else "Registration Success!"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        setupTextWatchers()
    }

    private fun toggleMode() {
        isLoginMode = !isLoginMode
        if (isLoginMode) {
            tvTitle.text = "Login"
            layoutName.visibility = View.GONE
            btnAction.text = "Login"
            btnToggleMode.text = "Don't have an account? Register"
        } else {
            tvTitle.text = "Register"
            layoutName.visibility = View.VISIBLE
            btnAction.text = "Register"
            btnToggleMode.text = "Already have an account? Login"
        }
        clearErrors()
    }

    private fun setupTextWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearErrors()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        edtName.addTextChangedListener(watcher)
        edtEmail.addTextChangedListener(watcher)
        edtPassword.addTextChangedListener(watcher)
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (!isLoginMode) {
            val name = edtName.text.toString().trim()
            if (name.isEmpty()) {
                layoutName.error = "Name is required"
                isValid = false
            }
        }

        val email = edtEmail.text.toString().trim()
        if (email.isEmpty()) {
            layoutEmail.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.error = "Enter a valid email address"
            isValid = false
        }

        val password = edtPassword.text.toString().trim()
        if (password.isEmpty()) {
            layoutPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 8) {
            layoutPassword.error = "Password must be at least 8 characters long"
            isValid = false
        }

        return isValid
    }

    private fun clearErrors() {
        layoutName.error = null
        layoutEmail.error = null
        layoutPassword.error = null
    }
}
