package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.model.Prescription
import java.util.*

class DiagnosesPrescriptionAdapter(private val prescriptions: ArrayList<Prescription>): RecyclerView.Adapter<DiagnosesPrescriptionAdapter.ViewHolder>() {

    private lateinit var formatTool: PivotController
    private lateinit var rxContext: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosesPrescriptionItem: MaterialCardView = itemView.findViewById(R.id.diagnosis_prescription_list_view)
        val diagnosesPrescriptionTitle: TextView = itemView.findViewById(R.id.diagnosis_prescription_name_text)
        val diagnosesPrescriptionPrescriber: TextView = itemView.findViewById(R.id.diagnosis_prescription_prescriber_text)
        val diagnosesPrescriptionPrescriberID: TextView = itemView.findViewById(R.id.diagnosis_prescription_prescriber_id)
        val diagnosesPrescriptionPrescriberDate: TextView = itemView.findViewById(R.id.diagnosis_prescription_date)
        val diagnosesPrescriptionType: ImageView = itemView.findViewById(R.id.diagnosis_prescription_type_icon)
        val diagnosesPrescriptionAmount: TextView = itemView.findViewById(R.id.diagnosis_prescription_amount_text)
        val diagnosesPrescriptionDuration: TextView = itemView.findViewById(R.id.diagnosis_prescription_duration_text)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_diagnoses_prescription_list, parent, false)
        rxContext = parent.context
        formatTool = PivotController()
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val focusPrescription = prescriptions[position]
        holder.diagnosesPrescriptionTitle.text = focusPrescription.prescriptionName
        holder.diagnosesPrescriptionPrescriber.text = focusPrescription.prescribedBy
        holder.diagnosesPrescriptionPrescriberID.text = focusPrescription.prescriptionPractitionerID
        holder.diagnosesPrescriptionPrescriberDate.text = formatTool.pivotObjectDateFormat(focusPrescription.prescriptionDate)
        holder.diagnosesPrescriptionAmount.text = focusPrescription.prescribedDosageAmount
        val duration = "${focusPrescription.prescriptionDosageRegimen} | ${focusPrescription.prescribedDuration}"
        holder.diagnosesPrescriptionDuration.text = duration
        holder.diagnosesPrescriptionType.setImageDrawable(ContextCompat.getDrawable(rxContext,iconRxController(focusPrescription.prescribedDosageType)))
        holder.diagnosesPrescriptionItem.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return prescriptions.size
    }



    private fun iconRxController(rxType: String?): Int {
        var iconResInt = R.drawable.pills_bottle_icon
        when(rxType!!.toUpperCase(Locale.ROOT)){
            "PILL(S)" ->{
                iconResInt = R.drawable.pills_bottle_icon
            }
            "CAPSULE(S)" ->{
                iconResInt = R.drawable.capsule_rx_icon
            }
            "TABLET(S)" ->{
                iconResInt = R.drawable.tablet_rx_icon
            }
            "TEASPOON(S)" ->{
                iconResInt = R.drawable.teaspoon_rx_icon
            }
            "DROP(S)" ->{
                iconResInt = R.drawable.drop_rx_icon
            }
            "BAG(S)" ->{
                iconResInt = R.drawable.mthc_rx_icon
            }
            "GRAM(S)" ->{
                iconResInt = R.drawable.gram_rx_icon
            }

        }
        return iconResInt
    }


}