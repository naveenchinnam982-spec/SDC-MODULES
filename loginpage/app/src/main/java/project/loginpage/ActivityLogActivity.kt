package project.loginpage

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ActivityLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_log)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val activities = arrayOf(
            "Logged in from Android Device - 10:00 AM",
            "Updated profile picture - Yesterday",
            "Changed account password - 2 days ago",
            "Enabled push notifications - 3 days ago",
            "Shared app with a friend - 1 week ago",
            "First time registration - 1 month ago"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, activities)
        val listView = findViewById<ListView>(R.id.lvActivities)
        listView.adapter = adapter
    }
}
