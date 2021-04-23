package com.apps.bacon.pomodorotimer

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.apps.bacon.pomodorotimer.databinding.ActivityUserProfileBinding
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreference = this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val userName = sharedPreference.getString("USER_NAME", getString(R.string.user)) as String
        setStatsHelloText(userName)
        initChart(binding.pomodoroChart)

        binding.alarmSoundButton.setOnClickListener {
            //startActivity
        }

        binding.sessionTimeButton.setOnClickListener {
            //startActivity
        }

    }

    private fun initChart(chart: CombinedChart) {
        val data = CombinedData()
        data.setData(initBarData())
        data.setData(initLineData())

        chart.apply {
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(context, R.color.peach_puff))
            setDrawGridBackground(false)
            drawOrder = arrayOf(DrawOrder.BAR, DrawOrder.LINE)
            setData(data)
        }

        chart.legend.apply {
            isWordWrapEnabled = true
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
        }

        chart.axisRight.apply {
            setDrawAxisLine(false)
            setDrawLabels(false)
            setDrawGridLines(false)
        }

        chart.axisLeft.apply {
            setDrawGridLines(true)
            axisMinimum = 0f
            axisMaximum = data.yMax + 1
        }

        //first e is empty cuz line start from 0.5 so first bar is on position 1 so -> days[1] is title for it
        val days = listOf(
            "",
            getString(R.string.monday_shortcut),
            getString(R.string.tuesday_shortcut),
            getString(R.string.wednesday_shortcut),
            getString(R.string.thursday_shortcut),
            getString(R.string.friday_shortcut),
            getString(R.string.saturday_shortcut),
            getString(R.string.sunday_shortcut)
        )

        chart.xAxis.apply {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            axisMinimum = 0.5f
            granularity = 1f
            axisMaximum = data.xMax + 0.5f
            valueFormatter = IndexAxisValueFormatter(days)
        }

        chart.invalidate()
    }

    private fun initBarData(): BarData {
        val entries: ArrayList<BarEntry> = ArrayList()

        val dataSet = BarDataSet(entries, getString(R.string.running_sessions))
        dataSet.apply {
            color = ContextCompat.getColor(this@UserProfileActivity, R.color.light_blue)
            setDrawValues(false)
        }

        return BarData(dataSet)
    }

    private fun initLineData(): LineData {
        val entries: ArrayList<Entry> = ArrayList()

        val dataSet = LineDataSet(entries, getString(R.string.completed_sessions))
        dataSet.apply {
            color = ContextCompat.getColor(this@UserProfileActivity, R.color.green)
            lineWidth = 4f
            setCircleColor(ContextCompat.getColor(this@UserProfileActivity, R.color.green))
            circleRadius = 5f
            fillColor = ContextCompat.getColor(this@UserProfileActivity, R.color.green)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawValues(false)
        }

        return LineData(dataSet)
    }

    @SuppressLint("SetTextI18n")
    private fun setStatsHelloText(userName: String) {
        binding.statsHelloText.text =
            "${getString(R.string.hey)} $userName! ${getString(R.string.your_stats_hello_text)}"
    }
}