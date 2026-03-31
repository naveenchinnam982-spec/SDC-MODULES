package secondYears.excercise

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import secondYears.excercise.components.StyledButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnPrimary: StyledButton
    private lateinit var btnSecondary: StyledButton
    private lateinit var btnDanger: StyledButton
    private lateinit var btnToggleLoading: StyledButton
    private lateinit var btnSuccess: StyledButton
    private lateinit var btnWarning: StyledButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views by ID
        btnPrimary = findViewById(R.id.btnPrimary)
        btnSecondary = findViewById(R.id.btnSecondary)
        btnDanger = findViewById(R.id.btnDanger)
        btnToggleLoading = findViewById(R.id.btnToggleLoading)
        btnSuccess = findViewById(R.id.btnSuccess)
        btnWarning = findViewById(R.id.btnWarning)

        // Set button colors
        btnPrimary.setButtonColor(Color.BLUE)
        btnSecondary.setButtonColor(Color.LTGRAY)
        btnDanger.setButtonColor(Color.RED)
        btnToggleLoading.setButtonColor(Color.LTGRAY)
        btnSuccess.setButtonColor(Color.GREEN)
        btnWarning.setButtonColor(Color.parseColor("#FF9800")) // Orange

        // Set button texts
        btnPrimary.setText("Primary Button")
        btnSecondary.setText("Secondary Button")
        btnDanger.setText("Danger Button")
        btnToggleLoading.setText("Toggle Loading")
        btnSuccess.setText("Success Button")
        btnWarning.setText("Warning Button")

        // Set secondary button text color to black (for visibility)
        btnSecondary.findViewById<android.widget.Button>(0)?.setTextColor(Color.BLACK)
        btnToggleLoading.findViewById<android.widget.Button>(0)?.setTextColor(Color.BLACK)

        setupButtons()
    }

    private fun setupButtons() {
        // Primary button
        btnPrimary.setOnButtonClickListener {
            Toast.makeText(this, "Primary Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Secondary button
        btnSecondary.setOnButtonClickListener {
            Toast.makeText(this, "Secondary Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Danger button
        btnDanger.setOnButtonClickListener {
            Toast.makeText(this, "Danger Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Toggle loading button
        btnToggleLoading.setOnButtonClickListener {
            val isLoading = btnDanger.isLoading
            btnDanger.setLoading(!isLoading)

            if (!isLoading) {
                Toast.makeText(this, "Loading started", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Loading stopped", Toast.LENGTH_SHORT).show()
            }
        }

        // Success button
        btnSuccess.setOnButtonClickListener {
            Toast.makeText(this, "Success Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Warning button
        btnWarning.setOnButtonClickListener {
            Toast.makeText(this, "Warning Button Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}