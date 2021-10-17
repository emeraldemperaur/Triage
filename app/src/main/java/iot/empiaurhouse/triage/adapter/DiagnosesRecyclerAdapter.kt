package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.model.Diagnosis

class DiagnosesRecyclerAdapter(private val diagnoses: ArrayList<Diagnosis>, private val diagnosesViewObject: View): RecyclerView.Adapter<DiagnosesRecyclerAdapter.ViewHolder>()  {

    private lateinit var diagnosesContext: Context
    private lateinit var diagnosesView: View
    private lateinit var formatTool: PivotController

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiagnosesRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_diagnoses_list_view, parent, false)
        diagnosesView = diagnosesViewObject
        formatTool = PivotController()
        diagnosesContext = parent.context
        val holder = ViewHolder(v)
        holder.diagnosesItem.setOnClickListener {  }
        return ViewHolder(v)

    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DiagnosesRecyclerAdapter.ViewHolder, position: Int) {
        val focusDiagnosis = diagnoses[position]
        val navControls = holder.diagnosesViewNavControl
        holder.diagnosesSynopsis.text = focusDiagnosis.diagnosisSynopsis
        holder.diagnosesDate.text = formatTool.pivotObjectDateFormat(focusDiagnosis.visitDate)
        holder.diagnosesLevel.text = focusDiagnosis.diagnosisLevel.diagnosisLevelName
        holder.diagnosesLevel.setTextColor(Color.parseColor(focusDiagnosis.diagnosisLevel.diagnosisLevelHexCode))
        holder.diagnosesItem.setOnClickListener {

        }

    }



    override fun getItemCount(): Int {
        return diagnoses.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val diagnosesItem: MaterialCardView = itemView.findViewById(R.id.patient_diagnosis_list_view)
        val diagnosesSynopsis: TextView = itemView.findViewById(R.id.patient_diagnosis_list_synopsis_text)
        val diagnosesDate: TextView = itemView.findViewById(R.id.patient_diagnosis_list_diagnosis_date)
        val diagnosesLevel: TextView = itemView.findViewById(R.id.diagnosis_list_diagnosis_level)
        val diagnosesViewNavControl = diagnosesView.findNavController()


    }

}