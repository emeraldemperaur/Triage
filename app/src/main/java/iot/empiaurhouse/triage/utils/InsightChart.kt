package iot.empiaurhouse.triage.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.ChironRecords

class InsightChart {

    fun renderRecordsPieChart(context: Context, chartFont: Typeface,
                              insightPieChart: PieChart, dataArrayList: ArrayList<ChironRecords>, listener: OnChartValueSelectedListener
    ): PieChart{
        insightPieChart.setUsePercentValues(true)
        insightPieChart.description.isEnabled = false
        insightPieChart.setExtraOffsets(5F, 10F, 5F, 5F)
        insightPieChart.dragDecelerationFrictionCoef = 0.95f
        insightPieChart.isDrawHoleEnabled = true
        insightPieChart.setHoleColor(Color.TRANSPARENT)
        insightPieChart.setTransparentCircleColor(Color.WHITE)
        insightPieChart.setTransparentCircleAlpha(110)
        insightPieChart.holeRadius = 58f
        insightPieChart.transparentCircleRadius = 61f
        insightPieChart.setDrawCenterText(false)
        insightPieChart.rotationAngle = 0F
        // enable rotation of the chart by touch
        insightPieChart.isRotationEnabled = true
        insightPieChart.isHighlightPerTapEnabled = true
        insightPieChart.setOnChartValueSelectedListener(listener);
        insightPieChart.animateY(1400, Easing.EaseInOutQuad);


        val dataEntries = arrayListOf<PieEntry>()
        val entryColors = arrayListOf<Int>()
        var count = 0
        var recordsTitle = ""
        for (data in dataArrayList) {
            count = data.recordCount?.toInt()!!
            recordsTitle = data.recordName.toString()
            dataEntries.add(PieEntry(count.toFloat(), recordsTitle ))
        }
        val recordsDataSet = PieDataSet(dataEntries,"")
        recordsDataSet.sliceSpace = 3F
        recordsDataSet.selectionShift = 5F
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        entryColors.add(R.color.recordColor1)
        recordsDataSet.colors = entryColors
        recordsDataSet.valueLinePart1Length = 0.2F
        recordsDataSet.valueLinePart2Length = 0.4F
        recordsDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val recordsChartData = PieData(recordsDataSet)
        recordsChartData.setValueFormatter(PercentFormatter())
        recordsChartData.setValueTextSize(11F)
        recordsChartData.setValueTextColor(R.color.chiron_grey)
        recordsChartData.setValueTypeface(chartFont)
        insightPieChart.data = recordsChartData
        insightPieChart.highlightValues(null)
        insightPieChart.invalidate()

        return insightPieChart
    }

}