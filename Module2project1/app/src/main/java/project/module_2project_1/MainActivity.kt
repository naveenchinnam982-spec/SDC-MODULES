package project.module_2project_1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabSubmit: FloatingActionButton
    private lateinit var adapter: FormAdapter
    private lateinit var formFields: List<FormField>
    // Create Gson with Pretty Printing
    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fabSubmit = findViewById(R.id.fabSubmit)

        // Updated JSON definition with Name, Age, and Gmail
        val jsonDefinition = """
            {
                "fields": [
                    {
                        "id": "1",
                        "label": "Name",
                        "field_type": "text",
                        "hint": "Enter your name",
                        "required": true
                    },
                    {
                        "id": "2",
                        "label": "Age",
                        "field_type": "text",
                        "hint": "Enter your age",
                        "required": true
                    },
                    {
                        "id": "3",
                        "label": "Gmail",
                        "field_type": "text",
                        "hint": "Enter your gmail",
                        "required": true
                    }
                ]
            }
        """.trimIndent()

        // Parse JSON to Data Model
        val formData = gson.fromJson(jsonDefinition, FormData::class.java)
        formFields = formData.fields

        // Setup RecyclerView
        adapter = FormAdapter(formFields)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabSubmit.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        if (validateForm()) {
            // Collect final data: use labels as keys for the output
            val results = formFields.associate { it.label to it.value }
            // This will now generate a line-by-line formatted string
            val resultJson = gson.toJson(results)
            
            Log.d("FormData", "Final Form Data:\n$resultJson")

            // Show output in a Dialog
            AlertDialog.Builder(this)
                .setTitle("Form Submitted Successfully")
                .setMessage("Generated JSON Output:\n\n$resultJson")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun validateForm(): Boolean {
        for (field in formFields) {
            if (field.required) {
                if (field.value.isEmpty()) {
                    Toast.makeText(this, "${field.label} is required", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }
}
