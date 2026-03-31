package project.module_2project_1

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormAdapter(private val fields: List<FormField>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_EDIT_TEXT = 0
        private const val TYPE_SPINNER = 1
        private const val TYPE_CHECKBOX = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (fields[position].fieldType) {
            "text" -> TYPE_EDIT_TEXT
            "spinner" -> TYPE_SPINNER
            "checkbox" -> TYPE_CHECKBOX
            else -> TYPE_EDIT_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EDIT_TEXT -> EditTextViewHolder(
                inflater.inflate(R.layout.item_edit_text, parent, false)
            )
            TYPE_SPINNER -> SpinnerViewHolder(
                inflater.inflate(R.layout.item_spinner, parent, false)
            )
            TYPE_CHECKBOX -> CheckboxViewHolder(
                inflater.inflate(R.layout.item_checkbox, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val field = fields[position]
        when (holder) {
            is EditTextViewHolder -> holder.bind(field)
            is SpinnerViewHolder -> holder.bind(field)
            is CheckboxViewHolder -> holder.bind(field)
        }
    }

    override fun getItemCount(): Int = fields.size

    inner class EditTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.labelTextView)
        private val inputLayout: TextInputLayout = itemView.findViewById(R.id.textInputLayout)
        private val editText: TextInputEditText = itemView.findViewById(R.id.inputEditText)
        private var textWatcher: TextWatcher? = null

        fun bind(field: FormField) {
            label.text = field.label
            inputLayout.hint = field.hint
            
            // Remove old watcher to avoid multiple callbacks during recycling
            textWatcher?.let { editText.removeTextChangedListener(it) }
            
            editText.setText(field.value)
            
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    field.value = s.toString()
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            editText.addTextChangedListener(textWatcher)
        }
    }

    inner class SpinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.labelTextView)
        private val spinner: Spinner = itemView.findViewById(R.id.spinner)

        fun bind(field: FormField) {
            label.text = field.label
            val options = field.options ?: emptyList()
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            
            val currentIndex = options.indexOf(field.value)
            if (currentIndex >= 0) {
                spinner.setSelection(currentIndex, false)
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    field.value = options[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    inner class CheckboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(field: FormField) {
            checkbox.text = field.label
            
            // Unset listener before setting value to avoid trigger
            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = field.value.toBoolean()
            
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                field.value = isChecked.toString()
            }
        }
    }
}
