package iot.empiaurhouse.triage.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.ScatterChart
import iot.empiaurhouse.triage.model.*

class InsightEngine {

    private lateinit var patientsRecord: ArrayList<Patient>
    private lateinit var diagnosesRecord: ArrayList<Diagnosis>
    private lateinit var prescriptionsRecord: ArrayList<Prescription>
    private lateinit var visitsRecord: ArrayList<Visit>
    private lateinit var pharmaceuticalRecord: ArrayList<Pharmaceuticals>


    fun initInsightRender(insightModel: InsightModel, insightRenderView: ConstraintLayout,
                          juxtapositionVistaView: ConstraintLayout, histogramVista: BarChart,
                          pieChartVista: PieChart, lineChartVista: LineChart, scatterPlotVista: ScatterChart,
                          histogramJuxVista: BarChart, pieChartJuxVista: PieChart, lineChartJuxVista: LineChart,
                          scatterPlotJuxVista: ScatterChart, viewDivider: View,
                          patientRecords: ArrayList<Patient>? = null, diagnosisRecords: ArrayList<Diagnosis>? = null,
                          prescriptionRecords: ArrayList<Prescription>? = null, visitRecords: ArrayList<Visit>? = null,
                          pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null){

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
}