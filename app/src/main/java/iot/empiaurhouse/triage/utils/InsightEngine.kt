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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import iot.empiaurhouse.triage.model.*
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class InsightEngine {

    private var patientsRecord: ArrayList<Patient> = arrayListOf()
    private var diagnosesRecord: ArrayList<Diagnosis> = arrayListOf()
    private var prescriptionsRecord: ArrayList<Prescription> = arrayListOf()
    private var visitsRecord: ArrayList<Visit> = arrayListOf()
    private var pharmaceuticalRecord: ArrayList<Pharmaceuticals> = arrayListOf()
    private var pharmaceuticalJuxRecord: ArrayList<Pharmaceuticals> = arrayListOf()
    private lateinit var scopeUID: String
    private lateinit var viewDivide: View
    private lateinit var insightPeriod: Period
    private var scopeID: Int = 0
    private lateinit var insightEntity: String
    private var originListCount : Int = 0
    private var predicateListCount : Int = 0
    private var originJuxListCount : Int = 0
    private var predicateJuxListCount : Int = 0
    private var othersRecordsCount : Int = 0
    private var othersRecordsYCount : Int = 0
    private var originListYCount : Int = 0
    private var predicateListYCount : Int = 0



    @RequiresApi(Build.VERSION_CODES.O)
    fun initInsightRender(insightModel: InsightModel, insightRenderView: ConstraintLayout,
                          juxtapositionVistaView: ConstraintLayout, histogramVista: BarChart,
                          pieChartVista: PieChart, lineChartVista: LineChart, scatterPlotVista: ScatterChart,
                          histogramJuxVista: BarChart, pieChartJuxVista: PieChart, lineChartJuxVista: LineChart,
                          scatterPlotJuxVista: ScatterChart, viewDivider: View,
                          patientRecords: ArrayList<Patient>? = null, diagnosisRecords: ArrayList<Diagnosis>? = null,
                          prescriptionRecords: ArrayList<Prescription>? = null, visitRecords: ArrayList<Visit>? = null,
                          pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null, pharmaceuticalsJuxRecords: ArrayList<Pharmaceuticals>? = null,
                          context: OnChartValueSelectedListener, typeface: Typeface, insightResultCount: TextView, insightOthersResultCount: TextView){


        viewDivide = viewDivider
        val startDate = insightModel.rangeStartDate
        val endDate = insightModel.rangeEndDate
        val daysBetween = ChronoUnit.DAYS.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        val monthsBetween = ChronoUnit.MONTHS.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        val yearsBetween = ChronoUnit.YEARS.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        insightPeriod = Period.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        initInsightEntity(insightModel)
        insightRenderView.visibility = View.VISIBLE
        // > 2 Yrs
        if (yearsBetween > 1){
            println("\n\t The Insight Range found for ${insightModel.alias} is greater than 2 Years")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "Years"
            scopeID = 1
            println("\n\t ScopeID: $scopeID | Scope UID: $scopeUID")
        }

        // < 2 Yrs && > 1 Yr
        if (daysBetween > 365 && yearsBetween < 2){
            println("\n\t The Insight Range found for ${insightModel.alias} is less than 2 Years")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "24 Months"
            scopeID = 2
            println("\n\t ScopeID: $scopeID | Scope UID: $scopeUID")
        }

        // 1 Yr
        if (daysBetween < 366 && (monthsBetween in 12..12)){
            println("\n\t The Insight Range found for ${insightModel.alias} is 1 Year")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "12 Months"
            scopeID = 3
            println("\n\t ScopeID: $scopeID | Scope UID: $scopeUID")
        }

        // > 1 Week & < 2 Months
        if (daysBetween > 7 && (daysBetween < 30.4368 || monthsBetween < 3)){
            println("\n\t The Insight Range found for ${insightModel.alias} is greater than 1 Week, less than 2 months")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "8 Weeks"
            scopeID = 4
            println("\n\t ScopeID: $scopeID | Scope UID: $scopeUID")
        }

        // =< 1 Week
        if (daysBetween in 0..7){
            println("\n\t The Insight Range found for ${insightModel.alias} is less than 1 Week")
            println("\n\t\t Days Between: $daysBetween")
            println("\n\t\t\t Years: ${insightPeriod.years} | Months: ${insightPeriod.months} | Days: ${insightPeriod.days}")
            scopeUID = "Weeks"
            scopeID = 5
            println("\n\t ScopeID: $scopeID | Scope UID: $scopeUID")
        }

        if (!patientRecords.isNullOrEmpty()){
            originListCount = patientRecords.size
            insightEntity = "Patients"
            when(insightModel.pointOfInterest) {
                "First Name" -> {
                    patientsRecord.addAll(patientRecords.filter { it.firstName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = patientsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.firstName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = patientsRecord.size
                    }
                    println("\t\t Filtered patient Insight records based on firstName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Last Name" -> {
                    patientsRecord.addAll(patientRecords.filter { it.lastName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = patientsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.lastName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = patientsRecord.size
                    }
                    println("\t\t Filtered patient Insight records based on lastName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Blood Group" -> {
                    patientsRecord.addAll(patientRecords.filter { it.bloodGroup!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = patientsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.bloodGroup!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = patientsRecord.size
                    }
                    println("\t\t Filtered patient Insight records based on bloodGroup predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
                "Insurer" -> {
                    patientsRecord.addAll(patientRecords.filter { it.insuranceVendor!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = patientsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()){
                        patientsRecord.addAll(patientRecords.filter { it.insuranceVendor!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = patientsRecord.size
                    }
                    println("\t\t Filtered patient Insight records based on insuranceVendor predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$patientsRecord")
                }
            }
        }
        if (!diagnosisRecords.isNullOrEmpty()){
            originListCount = diagnosisRecords.size
            insightEntity = "Diagnoses"
            when(insightModel.pointOfInterest) {
                "Synopsis" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisSynopsis!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = diagnosesRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisSynopsis!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = diagnosesRecord.size
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisSynopsis predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
                "Diagnosis Details" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisDetails!!.contains(insightModel.piThresholdValue) })
                    predicateListCount = diagnosesRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisDetails!!.contains(insightModel.omegaThresholdValue) })
                        predicateListYCount = diagnosesRecord.size
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisDetails predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
                "Level" -> {
                    diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisLevel.diagnosisLevelName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = diagnosesRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        diagnosesRecord.addAll(diagnosisRecords.filter { it.diagnosisLevel.diagnosisLevelName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = diagnosesRecord.size
                    }
                    println("\t\t Filtered diagnosis Insight records based on diagnosisLevelName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$diagnosesRecord")
                }
            }
        }
        if (!prescriptionRecords.isNullOrEmpty()){
            originListCount = prescriptionRecords.size
            insightEntity = "Prescriptions"
            when(insightModel.pointOfInterest) {
                "Rx Name" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = prescriptionsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = prescriptionsRecord.size
                    }
                    println("\t\t Filtered prescription Insight records based on prescriptionName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Prescriber" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescribedBy!!.contains( insightModel.piThresholdValue) })
                    predicateListCount = prescriptionsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescribedBy!!.contains( insightModel.omegaThresholdValue) })
                        predicateListYCount = prescriptionsRecord.size
                    }
                    println("\t\t Filtered prescription Insight records based on prescribedBy predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Prescriber ID" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionPractitionerID!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = prescriptionsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.prescriptionPractitionerID!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = prescriptionsRecord.size
                    }
                    println("\t\t Filtered prescription Insight records based on prescriptionPractitionerID predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
                "Patient Name" -> {
                    prescriptionsRecord.addAll(prescriptionRecords.filter { it.patientFullName!!.contains(insightModel.piThresholdValue) })
                    predicateListCount = prescriptionsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        prescriptionsRecord.addAll(prescriptionRecords.filter { it.patientFullName!!.contains(insightModel.omegaThresholdValue) })
                        predicateListYCount = prescriptionsRecord.size
                    }
                    println("\t\t Filtered prescription Insight records based on patientFullName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$prescriptionsRecord")
                }
            }
        }
        if (!visitRecords.isNullOrEmpty()){
            originListCount = visitRecords.size
            insightEntity = "Visits"
            when(insightModel.pointOfInterest) {
                "Host" -> {
                    visitsRecord.addAll(visitRecords.filter { it.hostPractitioner!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = visitsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.hostPractitioner!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = visitsRecord.size
                    }
                    println("\t\t Filtered visit Insight records based on hostPractitioner predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Host ID" -> {
                    visitsRecord.addAll(visitRecords.filter { it.hostPractitionerID!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = visitsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.hostPractitionerID!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = visitsRecord.size
                    }
                    println("\t\t Filtered visit Insight records based on hostPractitionerID predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Visit Time" -> {
                    visitsRecord.addAll(visitRecords.filter { it.visitTime!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = visitsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.visitTime!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = visitsRecord.size
                    }
                    println("\t\t Filtered visit Insight records based on visitTime predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Description" -> {
                    visitsRecord.addAll(visitRecords.filter { it.visitDescription!!.contains(insightModel.piThresholdValue) })
                    predicateListCount = visitsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.visitDescription!!.contains(insightModel.omegaThresholdValue)  })
                        predicateListYCount = visitsRecord.size
                    }
                    println("\n\t\t Filtered visit Insight records based on visitDescription predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
                "Patient Name" -> {
                    visitsRecord.addAll(visitRecords.filter { it.patientFullName!!.contains(insightModel.piThresholdValue) })
                    predicateListCount = visitsRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        visitsRecord.addAll(visitRecords.filter { it.patientFullName!!.contains(insightModel.omegaThresholdValue) })
                        predicateListYCount = visitsRecord.size
                    }
                    println("\t\t Filtered visit Insight records based on patientFullName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$visitsRecord")
                }
            }
        }
        if (!pharmaceuticalsRecords.isNullOrEmpty()){
            originListCount = pharmaceuticalsRecords.size
            insightEntity = "Pharmaceuticals"
            when(insightModel.pointOfInterest) {
                "Brand Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.brandName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = pharmaceuticalRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.brandName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListYCount = pharmaceuticalRecord.size

                    }
                    println("\t\t Filtered pharmaceutical Insight records based on brandName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")

                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.brandName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                        predicateJuxListCount = pharmaceuticalJuxRecord.size
                        originJuxListCount = pharmaceuticalsJuxRecords.size
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.brandName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                            predicateJuxListCount = pharmaceuticalJuxRecord.size
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on brandName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Generic Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.genericName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = pharmaceuticalRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.genericName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        predicateListCount = pharmaceuticalRecord.size
                    }
                    println("\t\t Filtered visit pharmaceutical records based on genericName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.genericName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                        predicateJuxListCount = pharmaceuticalJuxRecord.size
                        originJuxListCount = pharmaceuticalsJuxRecords.size
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.genericName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                            predicateJuxListCount = pharmaceuticalJuxRecord.size
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on genericName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Chemical Name" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.chemicalName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = pharmaceuticalRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.chemicalName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                    }
                    println("\t\t Filtered visit Insight records based on chemicalName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        originJuxListCount = pharmaceuticalsJuxRecords.size
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.chemicalName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                        predicateJuxListCount = pharmaceuticalJuxRecord.size
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.chemicalName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                            predicateJuxListCount = pharmaceuticalJuxRecord.size
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on chemicalName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
                "Manufacturer" -> {
                    pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.manufacturerName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                    predicateListCount = pharmaceuticalRecord.size
                    if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                        pharmaceuticalRecord.addAll(pharmaceuticalsRecords.filter { it.manufacturerName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                    }
                    println("\t\t Filtered pharmaceutical Insight records based on manufacturerName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                    println("$pharmaceuticalRecord")
                    if(!pharmaceuticalsJuxRecords.isNullOrEmpty()) {
                        originJuxListCount = pharmaceuticalsJuxRecords.size
                        pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.manufacturerName!!.lowercase() == insightModel.piThresholdValue.lowercase() })
                        predicateJuxListCount = pharmaceuticalJuxRecord.size
                        if (!insightModel.omegaThresholdValue.isNullOrBlank()) {
                            pharmaceuticalJuxRecord.addAll(pharmaceuticalsJuxRecords.filter { it.manufacturerName!!.lowercase() == insightModel.omegaThresholdValue.lowercase() })
                        }
                        println("\t\t Filtered pharmaceutical Juxtaposition records based on manufacturerName predicates: ${insightModel.piThresholdValue} | ${insightModel.omegaThresholdValue}")
                        println("$pharmaceuticalJuxRecord")
                    }
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            insightResultCount.text = predicateListCount.toString()
            when(insightModel.vistaCode){
                1 ->{
                    histogramVista.visibility = View.VISIBLE
                    renderVisualizer(insightModel, histogramVista = histogramVista, histogramJuxVista= histogramJuxVista, scopeID = scopeID,
                        viewDivider = viewDivide, context = context, typeface = typeface, predicatePatientRecords = patientsRecord,
                        predicateDiagnosisRecords = diagnosesRecord, predicatePrescriptionRecords = prescriptionsRecord, predicateVisitRecords = visitsRecord,
                        predicatePharmaceuticalsRecords = pharmaceuticalRecord, predicatePharmaceuticalsJuxRecord = pharmaceuticalJuxRecord)
                    when(insightModel.entityCode){
                        5 ->{
                            if (originListCount > 0) {
                                viewDivider.visibility = View.GONE
                                histogramJuxVista.visibility = View.VISIBLE
                                juxtapositionVistaView.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                2 ->{
                    pieChartVista.visibility = View.VISIBLE
                    renderVisualizer(insightModel, pieChartVista = pieChartVista, scopeID = scopeID,
                        viewDivider = viewDivide, context = context, typeface = typeface)
                    if(originListCount > 0) {
                        insightOthersResultCount.text = othersRecordsCount.toString()
                    }
                    when(insightModel.entityCode){
                        5 ->{
                            if (originListCount > 0) {
                                viewDivider.visibility = View.GONE
                                juxtapositionVistaView.visibility = View.VISIBLE
                                pieChartJuxVista.visibility = View.VISIBLE
                                renderVisualizer(insightModel, pieChartJuxVista = pieChartJuxVista, scopeID = scopeID,
                                    viewDivider = viewDivide, context = context, typeface = typeface)
                            }
                        }
                    }
                }
                3 ->{
                    lineChartVista.visibility = View.VISIBLE
                    val lineVistaCount = ""
                    insightResultCount.text = lineVistaCount
                    renderVisualizer(insightModel, lineChartVista = lineChartVista, scopeID = scopeID,
                        viewDivider = viewDivide, context = context, typeface = typeface)
                    when(insightModel.entityCode){
                        5 ->{
                            if (originListCount > 0) {
                                viewDivider.visibility = View.GONE
                                juxtapositionVistaView.visibility = View.VISIBLE
                                lineChartJuxVista.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                4 ->{
                    scatterPlotVista.visibility = View.VISIBLE
                    val scatterPlotVistaCount = ""
                    insightResultCount.text = scatterPlotVistaCount
                    renderVisualizer(insightModel, scatterPlotVista = scatterPlotVista, scopeID = scopeID,
                        viewDivider = viewDivide, context = context, typeface = typeface)
                    when(insightModel.entityCode){
                        5 ->{
                            if (originListCount > 0) {
                                viewDivider.visibility = View.GONE
                                juxtapositionVistaView.visibility = View.VISIBLE
                                scatterPlotJuxVista.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }, 669)


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderVisualizer(insightModel: InsightModel, scopeID: Int?, histogramVista: BarChart? = null,
                                 pieChartVista: PieChart? = null, lineChartVista: LineChart?  = null, scatterPlotVista: ScatterChart? = null,
                                 histogramJuxVista: BarChart?  = null, pieChartJuxVista: PieChart? = null, lineChartJuxVista: LineChart? = null,
                                 scatterPlotJuxVista: ScatterChart? = null, viewDivider: View,
                                 context: OnChartValueSelectedListener, typeface: Typeface, predicatePatientRecords: ArrayList<Patient>? = null, predicateDiagnosisRecords: ArrayList<Diagnosis>? = null,
                                 predicatePrescriptionRecords: ArrayList<Prescription>? = null, predicateVisitRecords: ArrayList<Visit>? = null,
                                 predicatePharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null, predicatePharmaceuticalsJuxRecord: ArrayList<Pharmaceuticals>? = null){

        when(insightModel.vistaCode){
            1 ->{
                if (histogramVista != null) {
                    val histogramCache = arrayListOf<BarEntry>()
                    val predicateName = insightModel.piThresholdValue
                    val predicateCount = predicateListCount
                    val originName = "Other $insightEntity"
                    val originCount = originListCount
                    histogramVista.setOnChartValueSelectedListener(context)
                    histogramVista.setDrawBarShadow(false)
                    histogramVista.setDrawValueAboveBar(true)
                    histogramVista.description.isEnabled = false
                    histogramVista.setMaxVisibleValueCount(60)
                    histogramVista.setPinchZoom(false)
                    histogramVista.setDrawGridBackground(false)
                    val xAxis: XAxis = histogramVista.xAxis
                    xAxis.granularity = 1F
                    xAxis.mAxisMinimum = 0F
                    xAxis.position = XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    histogramVista.axisLeft.setDrawGridLines(false)
                    histogramVista.animateY(1500)
                    histogramVista.legend.isEnabled = false
                    when(insightModel.entityCode){
                        1 ->{
                            for (patient in predicatePatientRecords!!){
                                val patientDoB = patient.birthDate
                                val formattedDoB = pivotObjectDateFormat(patientDoB)
                                val index = 0F
                                val histogramEntries = BarEntry(index, predicateCount.toFloat(), formattedDoB)
                                histogramCache.add(histogramEntries)
                                println("\n\tHistogram Cache: $histogramCache")
                            }
                            val set1: BarDataSet
                            if (histogramVista.data != null &&
                                histogramVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramCache
                                histogramVista.data.notifyDataChanged()
                                histogramVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramVista.data = data
                                histogramVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramVista.invalidate()
                            }, 333)
                        }
                        2 ->{
                            for (diagnosis in predicateDiagnosisRecords!!){
                                val diagnosisDate = diagnosis.visitDate
                                val formattedDiagnosisDate = pivotObjectDateFormat(diagnosisDate)
                                val histogramEntries = BarEntry((predicateDiagnosisRecords.indexOf(diagnosis) + 1).toFloat(), predicateCount.toFloat(), formattedDiagnosisDate)
                                histogramCache.add(histogramEntries)
                                println("\n\tHistogram Cache: $histogramCache")
                            }
                            val set1: BarDataSet
                            if (histogramVista.data != null &&
                                histogramVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramCache
                                histogramVista.data.notifyDataChanged()
                                histogramVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramVista.data = data
                                histogramVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramVista.invalidate()
                            }, 333)
                        }
                        3 ->{
                            for (prescription in predicatePrescriptionRecords!!){
                                val prescriptionDate = prescription.prescriptionDate
                                val formattedPrescriptionDate = pivotObjectDateFormat(prescriptionDate)
                                val histogramEntries = BarEntry((predicatePrescriptionRecords.indexOf(prescription) + 1).toFloat(), predicateCount.toFloat(), formattedPrescriptionDate)
                                histogramCache.add(histogramEntries)
                                println("\n\tHistogram Cache: $histogramCache")
                            }
                            val set1: BarDataSet
                            if (histogramVista.data != null &&
                                histogramVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramCache
                                histogramVista.data.notifyDataChanged()
                                histogramVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramVista.data = data
                                histogramVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramVista.invalidate()
                            }, 333)
                        }
                        4 ->{
                            for (visit in predicateVisitRecords!!){
                                val visitDate = visit.visitDate
                                val formattedPrescriptionDate = pivotObjectDateFormat(visitDate)
                                val histogramEntries = BarEntry((predicateVisitRecords.indexOf(visit) + 1).toFloat(), predicateCount.toFloat(), formattedPrescriptionDate)
                                histogramCache.add(histogramEntries)
                                println("\n\tHistogram Cache: $histogramCache")
                            }
                            val set1: BarDataSet
                            if (histogramVista.data != null &&
                                histogramVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramCache
                                histogramVista.data.notifyDataChanged()
                                histogramVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramVista.data = data
                                histogramVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramVista.invalidate()
                            }, 333)
                        }
                        5 ->{
                            for (pharmaceutical in predicatePharmaceuticalsRecords!!){
                                val expiryDate = pharmaceutical.expiryDate
                                val formattedPrescriptionDate = pivotObjectDateFormat(expiryDate)
                                val histogramEntries = BarEntry((predicatePharmaceuticalsRecords.indexOf(pharmaceutical) + 1).toFloat(), predicateCount.toFloat(), formattedPrescriptionDate)
                                histogramCache.add(histogramEntries)
                                println("\n\tHistogram Cache: $histogramCache")
                            }
                            val set1: BarDataSet
                            if (histogramVista.data != null &&
                                histogramVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramCache
                                histogramVista.data.notifyDataChanged()
                                histogramVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramVista.data = data
                                histogramVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramVista.invalidate()
                            }, 333)
                        }
                    }

                }

                if (histogramJuxVista != null) {
                    val histogramJuxCache = arrayListOf<BarEntry>()
                    val predicateJuxName = insightModel.piThresholdValue
                    val predicateJuxCount = predicateJuxListCount
                    val originJuxName = "Other $insightEntity"
                    val originJuxCount = originJuxListCount
                    histogramJuxVista.setOnChartValueSelectedListener(context)
                    histogramJuxVista.setDrawBarShadow(false)
                    histogramJuxVista.setDrawValueAboveBar(true)
                    histogramJuxVista.description.isEnabled = false
                    histogramJuxVista.setMaxVisibleValueCount(60)
                    histogramJuxVista.setPinchZoom(false)
                    histogramJuxVista.setDrawGridBackground(false)
                    val xAxis: XAxis = histogramJuxVista.xAxis
                    xAxis.position = XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    histogramJuxVista.axisLeft.setDrawGridLines(false)
                    histogramJuxVista.animateY(1500)
                    histogramJuxVista.legend.isEnabled = false
                    when(insightModel.entityCode){
                        5 ->{
                            for (pharmaceutical in predicatePharmaceuticalsJuxRecord!!){
                                val expiryDate = pharmaceutical.expiryDate
                                val formattedDoB = pivotObjectDateFormat(expiryDate)
                                val histogramEntries = BarEntry((predicatePharmaceuticalsJuxRecord.indexOf(pharmaceutical) + 1).toFloat(), predicateJuxCount.toFloat(), formattedDoB)
                                histogramJuxCache.add(histogramEntries)
                            }
                            val set1: BarDataSet
                            if (histogramJuxVista.data != null &&
                                histogramJuxVista.data.dataSetCount > 0
                            ) {
                                set1 = histogramJuxVista.data.getDataSetByIndex(0) as BarDataSet
                                set1.values = histogramJuxCache
                                histogramJuxVista.data.notifyDataChanged()
                                histogramJuxVista.notifyDataSetChanged()
                            } else {
                                set1 = BarDataSet(histogramJuxCache, insightModel.piThresholdValue)
                                set1.setColors(*ColorTemplate.MATERIAL_COLORS)
                                set1.setDrawValues(false)
                                val dataSets: ArrayList<IBarDataSet> = ArrayList()
                                dataSets.add(set1)
                                val data = BarData(dataSets)
                                histogramJuxVista.data = data
                                histogramJuxVista.setFitBars(true)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                histogramJuxVista.invalidate()
                            }, 333)
                        }
                    }
                }
            }
            2 -> {
                if (pieChartVista != null){
                    val pieCache = arrayListOf<PieEntry>()
                    val predicateName = insightModel.piThresholdValue
                    val predicateCount = predicateListCount
                    val originName = "Other $insightEntity"
                    val originCount = originListCount
                    val othersCount = (originCount.toFloat() - predicateCount.toFloat())
                    othersRecordsCount = othersCount.toInt()
                    println("\t\t\tpredicate count for $predicateName: $predicateCount")
                    println("\t\t\torigin count for $predicateName: $originCount")
                    println("\t\t\tothers count for $predicateName: $othersCount")
                    val piInsightEntry = PieEntry(predicateCount.toFloat(), predicateName)
                    val originEntry = PieEntry(othersCount, originName)
                    pieCache.add(piInsightEntry)
                    pieCache.add(originEntry)
                    val pieDataSet = PieDataSet(
                    pieCache, "${insightModel.pointOfInterest}:" +
                            " ${insightModel.piThresholdValue}"
                )
                    pieDataSet.setDrawIcons(false)
                    pieDataSet.sliceSpace = 3f
                    pieDataSet.iconsOffset = MPPointF(0F, 40F)
                pieDataSet.selectionShift = 5f
                val insightColors: ArrayList<Int> = ArrayList()
                insightColors.add(Color.parseColor("#0c204f"))
                insightColors.add(Color.parseColor("#000000"))
                pieDataSet.colors = insightColors
                val data = PieData(pieDataSet)
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
                pieChartVista.setEntryLabelTextSize(10f)
                pieChartVista.data = data
                pieChartVista.highlightValues(null)
                Handler(Looper.getMainLooper()).postDelayed({
                    pieChartVista.invalidate()
                }, 333)
                }
                if (pieChartJuxVista != null){
                    val pieJuxCache = arrayListOf<PieEntry>()
                    val predicateJuxName = insightModel.piThresholdValue
                    val predicateJuxCount = predicateJuxListCount
                    val originJuxName = "Other $insightEntity"
                    val originJuxCount = originJuxListCount
                    val othersCount = (originJuxCount.toFloat() - predicateJuxCount.toFloat())
                    println("\t\t\tpredicate Jux count for $predicateJuxName: $predicateJuxCount")
                    println("\t\t\torigin Jux count for $predicateJuxName: $originJuxCount")
                    println("\t\t\tothers Jux count for $predicateJuxName: $othersCount")
                    val piInsightEntry = PieEntry(predicateJuxCount.toFloat(), predicateJuxName)
                    val originEntry = PieEntry(othersCount
                        , originJuxName)
                    pieJuxCache.add(piInsightEntry)
                    pieJuxCache.add(originEntry)
                    val pieJuxDataSet = PieDataSet(
                        pieJuxCache, "${insightModel.pointOfInterest}:" +
                                " ${insightModel.piThresholdValue}"
                    )
                    pieJuxDataSet.setDrawIcons(false)
                    pieJuxDataSet.sliceSpace = 3f
                    pieJuxDataSet.iconsOffset = MPPointF(0F, 40F)
                    pieJuxDataSet.selectionShift = 5f
                    val insightColors: ArrayList<Int> = ArrayList()
                    insightColors.add(Color.parseColor("#000000"))
                    insightColors.add(Color.parseColor("#0c204f"))
                    pieJuxDataSet.colors = insightColors
                    val data = PieData(pieJuxDataSet)
                    data.setValueFormatter(PercentFormatter())
                    data.setValueTextSize(16f)
                    data.setValueTextColor(Color.WHITE)
                    data.setValueTypeface(typeface)
                    pieChartJuxVista.setUsePercentValues(true)
                    pieChartJuxVista.description.isEnabled = false
                    pieChartJuxVista.setExtraOffsets(5F, 10F, 5F, 5F)
                    pieChartJuxVista.dragDecelerationFrictionCoef = 0.95f
                    pieChartJuxVista.isDrawHoleEnabled = true
                    pieChartJuxVista.setHoleColor(Color.TRANSPARENT)
                    pieChartJuxVista.setTransparentCircleColor(Color.TRANSPARENT)
                    pieChartJuxVista.setTransparentCircleAlpha(110)
                    pieChartJuxVista.holeRadius = 58f
                    pieChartJuxVista.transparentCircleRadius = 61f
                    pieChartJuxVista.rotationAngle = 0F
                    pieChartJuxVista.isRotationEnabled = true
                    pieChartJuxVista.isHighlightPerTapEnabled = true
                    pieChartJuxVista.setOnChartValueSelectedListener(context)
                    pieChartJuxVista.animateY(1400, Easing.EaseInOutQuad)
                    val l: Legend = pieChartJuxVista.legend
                    l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                    l.orientation = Legend.LegendOrientation.VERTICAL
                    l.isEnabled = false
                    pieChartJuxVista.setEntryLabelColor(Color.LTGRAY)
                    pieChartJuxVista.setEntryLabelTypeface(typeface)
                    pieChartJuxVista.setEntryLabelTextSize(10f)
                    pieChartJuxVista.data = data
                    pieChartJuxVista.highlightValues(null)
                    Handler(Looper.getMainLooper()).postDelayed({
                        pieChartJuxVista.invalidate()
                    }, 333)
                }

            }
            3 ->{
                if (lineChartVista != null){
                    val lineCache = arrayListOf<Entry>()
                    val predicateName = insightModel.piThresholdValue
                    val predicateCount = predicateListCount
                    val originName = "Other $insightEntity"
                    val originCount = originListCount
                    val othersCount = (originCount.toFloat() - predicateCount.toFloat())
                    othersRecordsCount = othersCount.toInt()
                    val xEntry = Entry(1F, predicateCount.toFloat())
                    val xEntryB = Entry(1F, originCount.toFloat())
                    val xEntryC = Entry(1F, othersRecordsCount.toFloat())
                    lineCache.add(xEntry)
                    lineCache.add(xEntryB)
                    lineCache.add(xEntryC)
                    println("\t\t\tpredicate count for $predicateName: $predicateCount")
                    println("\t\t\torigin count for $predicateName: $originCount")
                    println("\t\t\tothers count for $predicateName: $othersCount")
                    val lineYCache = arrayListOf<Entry>()
                    val predicateYName = insightModel.omegaThresholdValue
                    val predicateYCount = predicateListCount
                    val othersYCount = (originCount.toFloat() - predicateYCount.toFloat())
                    othersRecordsYCount = othersYCount.toInt()
                    val yEntry = Entry(2F, predicateYCount.toFloat())
                    val yEntryB = Entry(2F, originCount.toFloat())
                    val yEntryC = Entry(2F, othersRecordsYCount.toFloat())
                    lineYCache.add(yEntry)
                    lineYCache.add(yEntryB)
                    lineYCache.add(yEntryC)
                    println("\t\t\tpredicate count for $predicateYName: $predicateCount")
                    println("\t\t\torigin count for $predicateYName: $originCount")
                    println("\t\t\tothers count for $predicateYName: $othersCount")
                    val lineDataCache = arrayListOf<ILineDataSet>()
                    val lineDataI = LineDataSet(lineCache, "Predicate: $predicateName")
                    lineDataI.lineWidth = 2.5f
                    lineDataI.circleRadius = 4f
                    val colorI: Int = Color.parseColor("#0c204f")
                    val colorII: Int = Color.parseColor("#800020")
                    lineDataI.color = colorI
                    lineDataI.setCircleColor(colorI)
                    lineDataCache.add(lineDataI)
                    val lineDataII = LineDataSet(lineYCache, "Predicate: $predicateYName")
                    lineDataII.lineWidth = 2.5f
                    lineDataII.circleRadius = 4f
                    lineDataI.color = colorII
                    lineDataI.setCircleColor(colorII)
                    lineDataCache.add(lineDataII)
                    (lineDataCache[0] as LineDataSet).enableDashedLine(10f, 10f, 0f)
                    (lineDataCache[0] as LineDataSet).setColors(*ColorTemplate.VORDIPLOM_COLORS)
                    (lineDataCache[0] as LineDataSet).setCircleColors(*ColorTemplate.VORDIPLOM_COLORS)
                    val data = LineData(lineDataCache)
                    lineChartVista.data = data
                    Handler(Looper.getMainLooper()).postDelayed({
                        lineChartVista.invalidate()
                    }, 333)

                }
            }
            4 ->{
                if (scatterPlotVista != null){
                    val scatterPlotCache = arrayListOf<Entry>()
                    val predicateName = insightModel.piThresholdValue
                    val predicateCount = predicateListCount
                    val originName = "Other $insightEntity"
                    val originCount = originListCount
                    val othersCount = (originCount.toFloat() - predicateCount.toFloat())
                    othersRecordsCount = othersCount.toInt()
                    val xEntry = Entry(1F, predicateCount.toFloat())
                    val xEntryB = Entry(1F, originCount.toFloat())
                    val xEntryC = Entry(1F, othersRecordsCount.toFloat())
                    scatterPlotCache.add(xEntry)
                    scatterPlotCache.add(xEntryB)
                    scatterPlotCache.add(xEntryC)
                    println("\t\t\tpredicate count for $predicateName: $predicateCount")
                    println("\t\t\torigin count for $predicateName: $originCount")
                    println("\t\t\tothers count for $predicateName: $othersCount")
                    val scatterPlotYCache = arrayListOf<Entry>()
                    val predicateYName = insightModel.omegaThresholdValue
                    val predicateYCount = predicateListCount
                    val othersYCount = (originCount.toFloat() - predicateYCount.toFloat())
                    othersRecordsYCount = othersYCount.toInt()
                    val yEntry = Entry(2F, predicateYCount.toFloat())
                    val yEntryB = Entry(2F, originCount.toFloat())
                    val yEntryC = Entry(2F, othersRecordsYCount.toFloat())
                    scatterPlotYCache.add(yEntry)
                    scatterPlotYCache.add(yEntryB)
                    scatterPlotYCache.add(yEntryC)
                    println("\t\t\tpredicate count for $predicateYName: $predicateCount")
                    println("\t\t\torigin count for $predicateYName: $originCount")
                    println("\t\t\tothers count for $predicateYName: $othersCount")
                    scatterPlotVista.description.isEnabled = false
                    scatterPlotVista.setOnChartValueSelectedListener(context)
                    scatterPlotVista.setDrawGridBackground(false)
                    scatterPlotVista.setTouchEnabled(true)
                    scatterPlotVista.maxHighlightDistance = 50f
                    scatterPlotVista.isDragEnabled = true
                    scatterPlotVista.setScaleEnabled(true)
                    scatterPlotVista.setMaxVisibleValueCount(200)
                    scatterPlotVista.setPinchZoom(true)
                    val l: Legend = scatterPlotVista.legend
                    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                    l.orientation = Legend.LegendOrientation.VERTICAL
                    l.setDrawInside(false)
                    l.xOffset = 5f
                    val yl: YAxis = scatterPlotVista.axisLeft
                    yl.typeface = typeface
                    yl.axisMinimum = 0f // this replaces setStartAtZero(true)
                    scatterPlotVista.axisRight.isEnabled = false
                    val xl: XAxis = scatterPlotVista.xAxis
                    xl.typeface = typeface
                    xl.setDrawGridLines(false)
                    val set1 = ScatterDataSet(scatterPlotCache, insightModel.piThresholdValue)
                    set1.setScatterShape(ScatterChart.ScatterShape.SQUARE)
                    set1.color = ColorTemplate.COLORFUL_COLORS[0]
                    val set2 = ScatterDataSet(scatterPlotYCache, insightModel.omegaThresholdValue)
                    set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
                    set2.scatterShapeHoleColor = ColorTemplate.COLORFUL_COLORS[3]
                    set2.scatterShapeHoleRadius = 3f
                    set2.color = ColorTemplate.COLORFUL_COLORS[1]
                    set1.scatterShapeSize = 8f
                    set2.scatterShapeSize = 8f
                    val dataSets: ArrayList<IScatterDataSet> = ArrayList()
                    dataSets.add(set1)
                    dataSets.add(set2)
                    val data = ScatterData(dataSets)
                    data.setValueTypeface(typeface)
                    scatterPlotVista.data = data
                    Handler(Looper.getMainLooper()).postDelayed({
                        scatterPlotVista.invalidate();
                    }, 333)



                }
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

    private fun initInsightEntity(insightModel: InsightModel){
        when(insightModel.entityCode){
            1 ->{
                insightEntity = "Patients"
            }
            2 ->{
                insightEntity = "Diagnoses"
            }
            3 ->{
                insightEntity = "Prescriptions"
            }
            4 ->{
                insightEntity = "Visits"
            }
            5 ->{
                insightEntity = "Pharmaceuticals"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateObjectFormat(stringDate: String?): LocalDate {
        return LocalDate.parse(stringDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pivotObjectDateFormat(stringDate: String?): String {
        val dateObject = LocalDate.parse(stringDate)
        val formatter = DateTimeFormatter.ofPattern("dd, MMM yyyy")
        return dateObject.format(formatter)
    }

}