package project.casestudy2

class MainActivitypackage project.casestudy2

import android.os.Bundle
import android.text.util.Linkify
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val link = findViewById<TextView>(R.id.linkText)
        Linkify.addLinks(link, Linkify.WEB_URLS)
    }
} {
}