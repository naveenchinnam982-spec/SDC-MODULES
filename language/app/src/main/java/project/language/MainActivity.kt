package kanoj.language_support

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        displayCurrentLocale()
    }

    private fun setupUI() {
        val changeLanguageButton = findViewById<Button>(R.id.changeLanguageButton)
        changeLanguageButton.setOnClickListener {
            showLanguageChangeDialog()
        }
    }

    private fun displayCurrentLocale() {
        val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0]
        } else {
            resources.configuration.locale
        }

        val languageTextView = findViewById<TextView>(R.id.languageTextView)
        val currentText = getString(R.string.current_language)
        val languageName = currentLocale.displayName

        // Update the text to show actual language name
        languageTextView.text = "$currentText\n($languageName)"
    }

    private fun showLanguageChangeDialog() {
        val languages = arrayOf("English", "Español", "Français")

        AlertDialog.Builder(this)
            .setTitle("Select Language")
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> setAppLocale("en")
                    1 -> setAppLocale("es")
                    2 -> setAppLocale("fr")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

        // Restart activity to apply language change
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Refresh UI when configuration changes
        displayCurrentLocale()
    }
}