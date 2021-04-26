package com.apps.bacon.pomodorotimer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.apps.bacon.pomodorotimer.databinding.ActivityUserProfileBinding
import com.apps.bacon.pomodorotimer.viewmodel.ProfileViewModel
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val userName = profileViewModel.getUserName()
        setStatsHelloText(userName)

        profileViewModel.getCompletedSessions().observe(this, {
            setCompletedSessions(it)
        })

        profileViewModel.getRunningSessions().observe(this, {
            setRunningSessions(it)
        })

        profileViewModel.getCurrentWeekStats().observe(this, {
            val runningData = listOf(
                BarEntry(1f, it.mondayRunning.toFloat()),
                BarEntry(2f, it.tuesdayRunning.toFloat()),
                BarEntry(3f, it.wednesdayRunning.toFloat()),
                BarEntry(4f, it.thursdayRunning.toFloat()),
                BarEntry(5f, it.fridayRunning.toFloat()),
                BarEntry(6f, it.saturdayRunning.toFloat()),
                BarEntry(7f, it.sundayRunning.toFloat())
            )

            val completedData = listOf(
                BarEntry(1f, it.mondayCompleted.toFloat()),
                BarEntry(2f, it.tuesdayCompleted.toFloat()),
                BarEntry(3f, it.wednesdayCompleted.toFloat()),
                BarEntry(4f, it.thursdayCompleted.toFloat()),
                BarEntry(5f, it.fridayCompleted.toFloat()),
                BarEntry(6f, it.saturdayCompleted.toFloat()),
                BarEntry(7f, it.sundayCompleted.toFloat())
            )

            initChart(binding.pomodoroChart, runningData, completedData)
        })

        binding.alarmSoundButton.setOnClickListener {
            //startActivity
        }

        binding.sessionTimeButton.setOnClickListener {
            //startActivity
        }

        binding.returnButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initChart(chart: CombinedChart, runningData: List<BarEntry>, completedData: List<Entry>) {
        val data = CombinedData()
        data.setData(initBarData(runningData))
        data.setData(initLineData(completedData))

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

    private fun initBarData(entries: List<BarEntry>): BarData {
        val dataSet = BarDataSet(entries, getString(R.string.running_sessions))
        dataSet.apply {
            color = ContextCompat.getColor(this@UserProfileActivity, R.color.light_blue)
            setDrawValues(false)
        }

        return BarData(dataSet)
    }

    private fun initLineData(entries: List<Entry>): LineData {
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

    private fun setCompletedSessions(completedSessions: Int?) {
        binding.completedSessions.text = completedSessions?.toString() ?: "0"
    }

    private fun setRunningSessions(runningSessions: Int?) {
        binding.runningSessions.text = runningSessions?.toString() ?: "0"
    }
}