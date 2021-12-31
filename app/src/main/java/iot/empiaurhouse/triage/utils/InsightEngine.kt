package iot.empiaurhouse.triage.utils

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import iot.empiaurhouse.triage.model.*
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates


class InsightEngine {

    private var patientsRecord: ArrayList<Patient> = arrayListOf()
    private var diagnosesRecord: ArrayList<Diagnosis> = arrayListOf()
    private var prescriptionsRecord: ArrayList<Prescription> = arrayListOf()
    private var visitsRecord: ArrayList<Visit> = arrayListOf()
    private var pharmaceuticalRecord: ArrayList<Pharmaceuticals> = arrayListOf()
    private var pharmaceuticalJuxRecord: ArrayList<Pharmaceuticals> = arrayListOf()
    private lateinit var scopeUID: String
    private lateinit var insightPeriod: Period
    private var scopeID by Delegates.notNull<Int>()


    @RequiresApi(Build.VERSION_CODES.O)
    fun initInsightRender(insightModel: InsightModel, insightRenderView: ConstraintLayout,
                          juxtapositionVistaView: ConstraintLayout, histogramVista: BarChart,
                          pieChartVista: PieChart, lineChartVista: LineChart, scatterPlotVista: ScatterChart,
                          histogramJuxVista: BarChart, pieChartJuxVista: PieChart, lineChartJuxVista: LineChart,
                          scatterPlotJuxVista: ScatterChart, viewDivider: View,
                          patientRecords: ArrayList<Patient>? = null, diagnosisRecords: ArrayList<Diagnosis>? = null,
                          prescriptionRecords: ArrayList<Prescription>? = null, visitRecords: ArrayList<Visit>? = null,
                          pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null, pharmaceuticalsJuxRecords: ArrayList<Pharmaceuticals>? = null){


        val startDate = insightModel.rangeStartDate
        val endDate = insightModel.rangeEndDate
        val daysBetween = ChronoUnit.DAYS.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        insightPeriod = Period.between(dateObjectFormat(startDate), dateObjectFormat(endDate))

        insightRenderView.visibility = View.VISIBLE
        if (!patientRecords.isNullOrEmpty()){
            when(insightModel.pointOfInterest) {
                "First Name" -> {
                    patientsRecord.addAll(patientRecords.filter { it.firstName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.firstName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered patient Insight records based on firstName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Last Name" -> {
                    patientsRecord.addAll(patientRecords.filter { it.lastName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.lastName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered patient Insight records based on lastName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Blood Group" -> {
                    patientsRecord.addAll(patientRecords.filter { it.bloodGroup == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.bloodGroup == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered patient Insight records based on bloodGroup predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Insurer" -> {
                    patientsRecord.addAll(patientRecords.filter { it.insuranceVendor == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.insuranceVendor == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered patient Insight records based on insuranceVendor predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
            }
        }
        if (!diagnosisRecords.isNullOrEmpty()){

            when(insightModel.pointOfInterest) {
                "Synopsis" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisSynopsis == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisSynopsis == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisSynopsis predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
                "Diagnosis Details" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisDetails!!.contains(insightModel.piThresholdValue) })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisDetails!!.contains(insightModel.omegaThresholdValue) })
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisDetails predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
                "Level" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisLevel.diagnosisLevelName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisLevel.diagnosisLevelName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisLevelName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
            }
        }
        if (!prescriptionRecords.isNullOrEmpty()){
            when(insightModel.pointOfInterest) {
                "Rx Name" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered prescription Insight records based on prescriptionName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Prescriber" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescribedBy!!.contains( insightModel.piThresholdValue) })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescribedBy!!.contains( insightModel.omegaThresholdValue) })
                    }
                    println("\t\t Filtered prescription Insight records based on prescribedBy predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Prescriber ID" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionPractitionerID == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionPractitionerID == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered prescription Insight records based on prescriptionPractitionerID predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Patient Name" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.patientFullName!!.contains(insightModel.piThresholdValue) })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.patientFullName!!.contains(insightModel.omegaThresholdValue) })
                    }
                    println("\t\t Filtered prescription Insight records based on patientFullName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
            }
        }
        if (!visitRecords.isNullOrEmpty()){
            when(insightModel.pointOfInterest) {
                "Host" -> {
                    visitsRecord.addAll(visitRecords.filter { it.hostPractitioner == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.hostPractitioner == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered visit Insight records based on hostPractitioner predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Host ID" -> {
                    visitsRecord.addAll(visitRecords.filter { it.hostPractitionerID == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.hostPractitionerID == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered visit Insight records based on hostPractitionerID predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Visit Time" -> {
                    visitsRecord.addAll(visitRecords.filter { it.visitTime == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.visitTime == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered visit Insight records based on visitTime predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Description" -> {
                    visitsRecord.addAll(visitRecords.filter { it.visitDescription!!.contains(insightModel.piThresholdValue) })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.visitDescription!!.contains(insightModel.omegaThresholdValue)  })
                    }
                    println("\n\t\t Filtered visit Insight records based on visitDescription predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Patient Name" -> {
                    visitsRecord.addAll(visitRecords.filter { it.patientFullName!!.contains(insightModel.piThresholdValue) })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.patientFullName!!.contains(insightModel.omegaThresholdValue) })
                    }
                    println("\t\t Filtered visit Insight records based on patientFullName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
            }
        }
        if (!pharmaceuticalsRecords.isNullOrEmpty()){
            when(insightModel.pointOfInterest) {
                "Brand Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.brandName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.brandName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered pharmaceutical Insight records based on brandName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")

                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.brandName == insightModel.piThresholdValue })
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.brandName == insightModel.omegaThresholdValue })
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on brandName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Generic Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.genericName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.genericName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered visit pharmaceutical records based on genericName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {

                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.genericName == insightModel.piThresholdValue })
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.genericName == insightModel.omegaThresholdValue })
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on genericName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Chemical Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.chemicalName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.chemicalName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered visit Insight records based on chemicalName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.chemicalName == insightModel.piThresholdValue })
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.chemicalName == insightModel.omegaThresholdValue })
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on chemicalName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Manufacturer" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.manufacturerName == insightModel.piThresholdValue })
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.manufacturerName == insightModel.omegaThresholdValue })
                    }
                    println("\t\t Filtered pharmaceutical Insight records based on manufacturerName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.manufacturerName == insightModel.piThresholdValue })
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.manufacturerName == insightModel.omegaThresholdValue })
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on manufacturerName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }

                }
            }
        }

        // > 2 Yrs
        if (daysBetween > 730 && insightPeriod.years > 1){
            println("\n\t The Insight Range found for ${insightModel.alias} is greater than 2 Years")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "Years"
            scopeID = 1
        }

        // < 2 Yrs && > 1 Yr
        if (daysBetween > 365 && insightPeriod.years < 2){
            println("\n\t The Insight Range found for ${insightModel.alias} is less than 2 Years")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "24 Months"
            scopeID = 2
        }

        // 1 Yr
        if (daysBetween < 366 && insightPeriod.years == 1){
            println("\n\t The Insight Range found for ${insightModel.alias} is 1 Year")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "12 Months"
            scopeID = 3
        }

        // > 1 Week & < 2 Months
        if (daysBetween > 7 && daysBetween < 30.4368){
            println("\n\t The Insight Range found for ${insightModel.alias} is greater than 1 Week, less than 2 months")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "8 Weeks"
            scopeID = 4
        }

        // =< 1 Week
        if (daysBetween in 0..7){
            println("\n\t The Insight Range found for ${insightModel.alias} is less than 1 Week")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "Weeks"
            scopeID = 5
        }


        when(insightModel.vistaCode){
            1 ->{
                histogramVista.visibility = View.VISIBLE
                when(insightModel.entityCode){
                    5 ->{
                        viewDivider.visibility = View.VISIBLE
                        juxtapositionVistaView.visibility = View.VISIBLE
                        histogramJuxVista.visibility = View.VISIBLE
                    }
                }
            }
            2 ->{
                pieChartVista.visibility = View.VISIBLE
                when(insightModel.entityCode){
                    5 ->{
                        viewDivider.visibility = View.VISIBLE
                        juxtapositionVistaView.visibility = View.VISIBLE
                        pieChartJuxVista.visibility = View.VISIBLE
                    }
                }
            }
            3 ->{
                lineChartVista.visibility = View.VISIBLE
                when(insightModel.entityCode){
                    5 ->{
                        viewDivider.visibility = View.VISIBLE
                        juxtapositionVistaView.visibility = View.VISIBLE
                        lineChartJuxVista.visibility = View.VISIBLE
                    }
                }
            }
            4 ->{
                scatterPlotVista.visibility = View.VISIBLE
                when(insightModel.entityCode){
                    5 ->{
                        viewDivider.visibility = View.VISIBLE
                        juxtapositionVistaView.visibility = View.VISIBLE
                        scatterPlotJuxVista.visibility = View.VISIBLE
                    }
                }
            }
        }


    }


    fun renderVisualizer(insightModel: InsightModel, scopeID: Int, histogramVista: BarChart,
                         pieChartVista: PieChart, lineChartVista: LineChart, scatterPlotVista: ScatterChart,
                         histogramJuxVista: BarChart, pieChartJuxVista: PieChart, lineChartJuxVista: LineChart,
                         scatterPlotJuxVista: ScatterChart, viewDivider: View,
                         patientRecords: ArrayList<Patient>? = null, diagnosisRecords: ArrayList<Diagnosis>? = null,
                         prescriptionRecords: ArrayList<Prescription>? = null, visitRecords: ArrayList<Visit>? = null,
                         pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null, pharmaceuticalsJuxRecords: ArrayList<Pharmaceuticals>? = null){

        when(insightModel.entityCode){
            1 ->{
                //patientRecords
                // process & init data inputs to be used per Vista Code

            }

        }


    }


    fun renderHubVisualizer(chironRecords: ArrayList<ChironRecords>, pieChartVista: PieChart,
                            context: OnChartValueSelectedListener, typeface: Typeface, insightTitle: TextView,
                            insightTimeStamp: TextView){
       val recordsCache = arrayListOf<PieEntry>()
        if (!chironRecords.isNullOrEmpty()){
            insightTitle.visibility = View.VISIBLE
            pieChartVista.visibility = View.VISIBLE
            insightTimeStamp.visibility = View.VISIBLE
        }
        else if (chironRecords.isNullOrEmpty()){
            insightTimeStamp.visibility = View.VISIBLE
        }
        for (record in chironRecords){
           val recordName = record.recordName
           val recordCount = record.recordCount
           val  recordEntry = PieEntry(recordCount!!.toFloat(), recordName)
            recordsCache.add(recordEntry)
        }
        val recordsDataSet = PieDataSet(recordsCache, "DB Records")
        recordsDataSet.setDrawIcons(false)

        recordsDataSet.sliceSpace = 3f
        recordsDataSet.iconsOffset = MPPointF(0F, 40F)
        recordsDataSet.selectionShift = 5f
        val recordColors: ArrayList<Int> = ArrayList()
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordColors.add(Color.parseColor("#0c204f"))
        recordsDataSet.colors = recordColors
        val data = PieData(recordsDataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(typeface)
        pieChartVista.setUsePercentValues(true)
        pieChartVista.description.isEnabled = false
        pieChartVista.setExtraOffsets(5F, 10F, 5F, 5F)
        pieChartVista.dragDecelerationFrictionCoef = 0.95f
        pieChartVista.isDrawHoleEnabled = true
        pieChartVista.setHoleColor(Color.TRANSPARENT)
        pieChartVista.setTransparentCircleColor(Color.TRANSPARENT)
        pieChartVista.setTransparentCircleAlpha(110)
        pieChartVista.holeRadius = 58f
        pieChartVista.transparentCircleRadius = 61f
        pieChartVista.rotationAngle = 0F
        pieChartVista.isRotationEnabled = true
        pieChartVista.isHighlightPerTapEnabled = true
        pieChartVista.setOnChartValueSelectedListener(context)
        pieChartVista.animateY(1400, Easing.EaseInOutQuad)
        val l: Legend = pieChartVista.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.isEnabled = false
        pieChartVista.setEntryLabelColor(Color.LTGRAY)
        pieChartVista.setEntryLabelTypeface(typeface)
        pieChartVista.setEntryLabelTextSize(9f)
        pieChartVista.data = data
        pieChartVista.highlightValues(null)
        Handler(Looper.getMainLooper()).postDelayed({
            pieChartVista.invalidate()
        }, 1111)


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun dateObjectFormat(stringDate: String?): LocalDate {
        return LocalDate.parse(stringDate)
    }
}