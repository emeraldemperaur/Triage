package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.model.Visit

class DiagnosesVisitAdapter(private val visits: ArrayList<Visit>): RecyclerView.Adapter<DiagnosesVisitAdapter.ViewHolder>() {

    private lateinit var visitContext: Context
    private lateinit var formatTool: PivotController



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosesVisitItem: MaterialCardView = itemView.findViewById(R.id.diagnosis_visit_list_view)
        val diagnosesVisitLog: TextView = itemView.findViewById(R.id.diagnosis_visit_logname_text)
        val diagnosesVisitHost: TextView = itemView.findViewById(R.id.diagnosis_visit_host_text)
        val diagnosesVisitHostID: TextView = itemView.findViewById(R.id.diagnosis_visit_host_id)
        val diagnosesVisitDescription: TextView = itemView.findViewById(R.id.diagnosis_visit_log_description)
        val diagnosesVisitingTime: TextView = itemView.findViewById(R.id.diagnosis_visit_time)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_diagnoses_visit_list, parent, false)
       visitContext = parent.context
        formatTool = PivotController()
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val focusVisit = visits[position]
        holder.diagnosesVisitLog.text = formatTool.pivotObjectDateFormat(focusVisit.visitDate)
        holder.diagnosesVisitHost.text = focusVisit.hostPractitioner
        holder.diagnosesVisitHostID.text = focusVisit.hostPractitionerID
        holder.diagnosesVisitDescription.text = focusVisit.visitDescription
        holder.diagnosesVisitingTime.text = focusVisit.visitTime
        holder.diagnosesVisitItem.setOnClickListener {

        }


    }

    override fun getItemCount(): Int {
        return visits.size
    }
}