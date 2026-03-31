package project.loginpage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Initialize Views
        val llChangePassword = findViewById<LinearLayout>(R.id.llChangePassword)
        val llDevicePermissions = findViewById<LinearLayout>(R.id.llDevicePermissions)
        val switchNotifications = findViewById<SwitchMaterial>(R.id.switchNotifications)
        val switchDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)
        val tvDeviceModel = findViewById<TextView>(R.id.tvDeviceModel)
        val tvAndroidVersion = findViewById<TextView>(R.id.tvAndroidVersion)
        val llShareApp = findViewById<LinearLayout>(R.id.llShareApp)
        val llAboutUs = findViewById<LinearLayout>(R.id.llAboutUs)
        val btnDeleteAccount = findViewById<Button>(R.id.btnDeleteAccount)

        // 1. Gather & Display Phone/Device Information
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val version = Build.VERSION.RELEASE
        val apiLevel = Build.VERSION.SDK_INT
        
        tvDeviceModel.text = "Device: $manufacturer $model"
        tvAndroidVersion.text = "Android Version: $version (API $apiLevel)"

        // 2. Open System Settings (Device Permissions)
        llDevicePermissions.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = android.net.Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            Toast.makeText(this, "Opening App Settings", Toast.LENGTH_SHORT).show()
        }

        // 3. Share App Feature
        llShareApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Login Page App")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, download this cool app from the Play Store!")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        // 4. Other Features
        llChangePassword.setOnClickListener {
            Toast.makeText(this, "Redirecting to Reset Password...", Toast.LENGTH_SHORT).show()
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Dark Mode applied", Toast.LENGTH_SHORT).show()
        }

        llAboutUs.setOnClickListener {
            Toast.makeText(this, "App Version 1.0.0 by project.loginpage", Toast.LENGTH_LONG).show()
        }

        btnDeleteAccount.setOnClickListener {
            Toast.makeText(this, "Account deletion request sent", Toast.LENGTH_SHORT).show()
        }
    }
}
