package project.contactslist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.familycircle.app.SplashActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // This is the default MainActivity that Android Studio created.
        // We redirect it to our new Family Circle Splash screen.
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}