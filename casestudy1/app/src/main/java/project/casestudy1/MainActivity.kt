package project.casestudy1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Linear Layout Section
        val tvLinear = findViewById<TextView>(R.id.tvLinear)
        findViewById<Button>(R.id.btnLinear).setOnClickListener {
            tvLinear.text = "Hello from Linear Layout!"
        }

        // Relative Layout Section
        val tvRelative = findViewById<TextView>(R.id.tvRelative)
        findViewById<Button>(R.id.btnRelative).setOnClickListener {
            tvRelative.text = "Hello from Relative Layout!"
        }

        // Constraint Layout Section
        val tvConstraint = findViewById<TextView>(R.id.tvConstraint)
        findViewById<Button>(R.id.btnConstraint).setOnClickListener {
            tvConstraint.text = "Hello from Constraint Layout!"
        }
    }
}
