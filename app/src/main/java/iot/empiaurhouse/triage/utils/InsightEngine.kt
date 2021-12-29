package iot.empiaurhouse.triage.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.ScatterChart
import iot.empiaurhouse.triage.model.*
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates

class InsightEngine {

    private lateinit var patientsRecord: ArrayList<Patient>
    private lateinit var diagnosesRecord: ArrayList<Diagnosis>
    private lateinit var prescriptionsRecord: ArrayList<Prescription>
    private lateinit var visitsRecord: ArrayList<Visit>
    private lateinit var pharmaceuticalRecord: ArrayList<Pharmaceuticals>
    private lateinit var scopeUID: String
    private var scopeID by Delegates.notNull<Int>()


    @RequiresApi(Build.VERSION_CODES.O)
    fun initInsightRender(insightModel: InsightModel, insightRenderView: ConstraintLayout,
                          juxtapositionVistaView: ConstraintLayout, histogramVista: BarChart,
                          pieChartVista: PieChart, lineChartVista: LineChart, scatterPlotVista: ScatterChart,
                          histogramJuxVista: BarChart, pieChartJuxVista: PieChart, lineChartJuxVista: LineChart,
                          scatterPlotJuxVista: ScatterChart, viewDivider: View,
                          patientRecords: ArrayList<Patient>? = null, diagnosisRecords: ArrayList<Diagnosis>? = null,
                          prescriptionRecords: ArrayList<Prescription>? = null, visitRecords: ArrayList<Visit>? = null,
                          pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null){


        val startDate = insightModel.rangeStartDate
        val endDate = insightModel.rangeEndDate
        val daysBetween = ChronoUnit.DAYS.between(dateObjectFormat(startDate), dateObjectFormat(endDate))
        val insightPeriod = Period.between(dateObjectFormat(startDate), dateObjectFormat(endDate))

        insightRenderView.visibility = View.VISIBLE
        if (!patientRecords.isNullOrEmpty()){
            patientsRecord = patientRecords
        }
        if (!diagnosisRecords.isNullOrEmpty()){
            diagnosesRecord = diagnosisRecords
        }
        if (!prescriptionRecords.isNullOrEmpty()){
            prescriptionsRecord = prescriptionRecords
        }
        if (!visitRecords.isNullOrEmpty()){
            visitsRecord = visitRecords
        }
        if (!pharmaceuticalsRecords.isNullOrEmpty()){
            pharmaceuticalRecord = pharmaceuticalsRecords
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun dateObjectFormat(stringDate: String?): LocalDate {
        return LocalDate.parse(stringDate)
    }
}