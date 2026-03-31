package project.module_2project_1

import com.google.gson.annotations.SerializedName

data class FormField(
    val id: String,
    val label: String,
    @SerializedName("field_type") val fieldType: String,
    val hint: String? = null,
    val options: List<String>? = null, // For Spinner
    val required: Boolean = false,
    var value: String = "" // To store the user input
)

data class FormData(
    val fields: List<FormField>
)
