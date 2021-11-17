package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.Patient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class SearchRecyclerAdapter(private val searchList: ArrayList<Patient>, private val finderViewObject: View, private val endPointCode: Int): RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>(),
    Filterable {

    private lateinit var finderContext: Context
    private lateinit var finderView: View
    private var endPointCodex by Delegates.notNull<Int>()
    var resultsFilterList = ArrayList<Patient>()

    init {
        resultsFilterList = searchList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val resultPrescriptionIcon: ImageView = itemView.findViewById(R.id.patients_found_list_prescription_icon)
        val resultVisitIcon: ImageView = itemView.findViewById(R.id.patients_found_list_visits_icon)
        val resultFullName: TextView = itemView.findViewById(R.id.patients_found_list_name_text)
        val resultDoB: TextView = itemView.findViewById(R.id.patients_found_list_dob_text)
        val resultBloodGroup: TextView = itemView.findViewById(R.id.patients_found_list_blood_group)
        val resultInsurer: TextView = itemView.findViewById(R.id.patients_found_list_insurer_text)
        val resultInsurerID: TextView = itemView.findViewById(R.id.patients_found_list_insurer_subtext)
        val resultDiagnosesCount: TextView = itemView.findViewById(R.id.patients_found_list_diagnoses_count_text)
        val resultPrescriptionCount: TextView = itemView.findViewById(R.id.patients_found_list_prescription_count_text)
        val resultVisitCount: TextView = itemView.findViewById(R.id.patients_found_list_visit_count_text)
        val resultItem: CardView = itemView.findViewById(R.id.patients_found_list_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_search_item_view, parent, false)
        finderContext = parent.context
        finderView = finderViewObject
        val holder = ViewHolder(v)
        holder.resultItem.setOnClickListener {  }
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val focusItem = resultsFilterList[position]
        holder.resultFullName.text = focusItem.fullName
        holder.resultDoB.text = insightObjectDateFormat(focusItem.birthDate)
        holder.resultBloodGroup.text = focusItem.bloodGroup
        holder.resultInsurer.text = focusItem.insuranceVendor
        val holderText = "Not Provided"
        if (focusItem.insuranceVendor.isNullOrBlank()){
            holder.resultInsurer.text = holderText
        }
        holder.resultInsurerID.text = focusItem.insuranceVendorID
        if (focusItem.insuranceVendorID.isNullOrBlank()){
            holder.resultInsurerID.text = holderText
        }
        if (focusItem.diagnoses != null && focusItem.diagnoses.size > 0){
            val diagnosesCount = focusItem.diagnoses.size
            val diagnosesCountText = "$diagnosesCount"
            holder.resultDiagnosesCount.text = diagnosesCountText
        }
        val focusDiagnoses = focusItem.diagnoses
        if (focusDiagnoses.isNullOrEmpty()){
            holder.resultPrescriptionCount.setTextColor(Color.parseColor("#A9A9A9"))
            holder.resultPrescriptionIcon.setColorFilter(Color.parseColor("#A9A9A9"))
            holder.resultVisitCount.setTextColor(Color.parseColor("#A9A9A9"))
            holder.resultVisitIcon.setColorFilter(Color.parseColor("#A9A9A9"))
            holder.resultPrescriptionCount.text = 0.toString()
            holder.resultVisitCount.text = 0.toString()
            holder.resultDiagnosesCount.text = 0.toString()
            holder.resultDiagnosesCount.setTextColor(Color.parseColor("#A9A9A9"))
        }
        if (!focusDiagnoses.isNullOrEmpty()) {
            var resultRxCounter = 0
            var resultVisitCounter = 0
            holder.resultPrescriptionCount.text = resultRxCounter.toString()
            holder.resultVisitCount.text = resultVisitCounter.toString()
            for (diagnosis in focusDiagnoses) {
                if (!diagnosis.prescriptions.isNullOrEmpty()) {
                    resultRxCounter += diagnosis.prescriptions.size
                }
                if (!diagnosis.visits.isNullOrEmpty()){
                    resultVisitCounter += diagnosis.visits.size
                }
            }
            holder.resultPrescriptionCount.text = resultRxCounter.toString()
            holder.resultVisitCount.text = resultVisitCounter.toString()
            if (resultRxCounter < 1){
                holder.resultPrescriptionCount.setTextColor(Color.parseColor("#A9A9A9"))
                holder.resultPrescriptionIcon.setColorFilter(Color.parseColor("#A9A9A9"))
            }
            if (resultVisitCounter < 1){
                holder.resultVisitCount.setTextColor(Color.parseColor("#A9A9A9"))
                holder.resultVisitIcon.setColorFilter(Color.parseColor("#A9A9A9"))
            }

        }
        holder.resultItem.setOnClickListener {
            if (!focusItem.phoneNumber.isNullOrBlank()){
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + focusItem.phoneNumber)
                finderContext.startActivity(dialIntent)
            }

        }
        holder.resultItem.setOnLongClickListener {
            if (!focusItem.address.isNullOrBlank()){
            val residence = "${focusItem.address}, ${focusItem.city}"
            val residenceIntent =  Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$residence"))
            residenceIntent.setPackage("com.google.android.apps.maps");
            finderContext.startActivity(residenceIntent)
        }
            true
        }
    }

    override fun getItemCount(): Int {
        return resultsFilterList.size
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    resultsFilterList = searchList
                } else {
                    val resultList = ArrayList<Patient>()
                    for (row in searchList) {
                        when(endPointCode) {
                            1 ->{
                            if (row.firstName!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                finderView.visibility = View.GONE
                                resultList.add(row)
                            }else{
                                //finderView.visibility = View.VISIBLE
                                resultsFilterList = resultList
                            }
                            }
                            2 ->{
                                if (row.lastName!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                    finderView.visibility = View.GONE
                                    resultList.add(row)
                                }else{
                                    //finderView.visibility = View.VISIBLE
                                    resultsFilterList = resultList
                                }
                            }
                            3 ->{
                                if (row.insuranceVendor!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                    finderView.visibility = View.GONE
                                    resultList.add(row)
                                }else{
                                    //finderView.visibility = View.VISIBLE
                                    resultsFilterList = resultList
                                }
                            }
                            4 ->{
                                if (row.insuranceVendorID!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                    finderView.visibility = View.GONE
                                    resultList.add(row)
                                }else{
                                    //finderView.visibility = View.VISIBLE
                                    resultsFilterList = resultList
                                }
                            }
                            5 ->{
                                if (row.bloodGroup!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                    finderView.visibility = View.GONE
                                    resultList.add(row)
                                }else{
                                    //finderView.visibility = View.VISIBLE
                                    resultsFilterList = resultList

                                }
                            }
                            6 ->{
                                if (row.birthDate!!.lowercase().contains(charSearch.lowercase(Locale.ROOT))) {
                                        finderView.visibility = View.GONE
                                    resultList.add(row)
                                }else{
                                    //finderView.visibility = View.VISIBLE
                                    resultsFilterList = resultList

                                }
                            }


                        }

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = resultsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                if (filterResults?.values != null){
                    resultsFilterList = filterResults.values as ArrayList<Patient>?: arrayListOf()
                    //filterResults.values = resultsFilterList
                    notifyDataSetChanged()
                }else if (filterResults?.values == null){
                    filterResults!!.values = resultsFilterList
                    notifyDataSetChanged()
                    if (resultsFilterList.size > 0){
                        finderView.visibility = View.GONE
                    }
                }

            }


        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun insightObjectDateFormat(stringDate: String?): String {
        val dateObject = LocalDate.parse(stringDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return dateObject.format(formatter)
    }


    }