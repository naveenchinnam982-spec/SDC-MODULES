package project.master_detailflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<Item>("item")

        val name = findViewById<TextView>(R.id.name)
        val description = findViewById<TextView>(R.id.description)
        val subItemsList = findViewById<TextView>(R.id.subItemsList)

        name.text = item?.name
        description.text = item?.description

        // Display sub-items list as a formatted string
        if (item != null && item.subItems.isNotEmpty()) {
            val formattedSubItems = item.subItems.joinToString("\n") { "• $it" }
            subItemsList.text = formattedSubItems
        } else {
            subItemsList.text = "No additional models available."
        }
    }
}