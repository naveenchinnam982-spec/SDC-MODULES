package project.handson2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val barChart = findViewById<BarChart>(R.id.barChart)
        if (barChart != null) {
            setupBarChart(barChart)
        }
    }

    private fun setupBarChart(barChart: BarChart) {
        // Create Data
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, 80f))
        entries.add(BarEntry(2f, 65f))
        entries.add(BarEntry(3f, 90f))
        entries.add(BarEntry(4f, 75f))
        entries.add(BarEntry(5f, 85f))

        // Create Dataset
        val barDataSet = BarDataSet(entries, "Marks")
        barDataSet.color = Color.BLUE
        barDataSet.valueTextSize = 14f

        // Set Data
        val barData = BarData(barDataSet)
        barChart.data = barData

        // Customize Chart
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.animateY(1000)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        barChart.invalidate()
    }
}
