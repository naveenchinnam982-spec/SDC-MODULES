package secondYears.excercise.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar

class StyledButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private var isLoadingState: Boolean = false

    init {
        initView()
    }

    private fun initView() {
        // Create the button
        button = Button(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            isAllCaps = false
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.BLUE) // Default color
        }

        // Create the progress bar
        progressBar = ProgressBar(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            visibility = GONE
        }

        // Add views to this FrameLayout
        addView(button)
        addView(progressBar)
    }

    fun setLoading(loading: Boolean) {
        isLoadingState = loading
        if (isLoadingState) {
            button.visibility = GONE
            progressBar.visibility = VISIBLE
            isEnabled = false
        } else {
            button.visibility = VISIBLE
            progressBar.visibility = GONE
            isEnabled = true
        }
    }

    val isLoading: Boolean
        get() = isLoadingState

    fun setText(text: String) {
        button.text = text
    }

    fun setOnButtonClickListener(listener: OnClickListener) {
        button.setOnClickListener(listener)
    }

    fun setButtonColor(color: Int) {
        button.setBackgroundColor(color)
    }
}