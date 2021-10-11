package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.ChironRecords
import iot.empiaurhouse.triage.view.DashboardFragmentDirections

class RecordRecyclerAdapter(private val recordsList: ArrayList<ChironRecords>, private val recordViewObject: View): RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder>() {

    private lateinit var pivotContext: Context
    private lateinit var recordView: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.records_list_view, parent, false)
        pivotContext = parent.context
        recordView = recordViewObject
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val focusRecord = recordsList[position]
        val navControls = holder.navControl
        holder.recordItemTitle.text = recordsList[position].recordName
        holder.recordItemType.text = recordsList[position].recordType
        holder.recordItemCount.text = recordsList[position].recordCount.toString()
        holder.recordItem.setOnClickListener {
            val focusID = focusRecord.recordID
            if (focusID != null) {
                val input = DashboardFragmentDirections.viewRecordsAction(focusID)
                navControls.navigate(input)
                // init RecordsViewModel List
                var recordName = ""
                when(focusID){
                    1 ->{
                        recordName = "Patients"
                    }
                    2 ->{
                        recordName = "Diagnoses"
                    }
                    3 ->{
                        recordName = "Prescriptions"
                    }
                    4 ->{
                        recordName = "Visits"
                    }
                    5 ->{
                        recordName = "General Practitioners"
                    }
                    6 ->{
                        recordName = "Doctors"
                    }
                    7 ->{
                        recordName = "Registered Nurses"
                    }
                    8 ->{
                        recordName = "Nurse Practitioners"
                    }
                    9 ->{
                        recordName = "Pharmaceuticals"
                    }


                }



            }
            }
        }



    override fun getItemCount(): Int {
        return recordsList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recordItem: MaterialCardView = itemView.findViewById(R.id.records_card_view)
        val recordItemTitle: TextView = itemView.findViewById(R.id.records_item_title)
        val recordItemType: TextView = itemView.findViewById(R.id.records_item_type)
        val recordItemCount: TextView = itemView.findViewById(R.id.records_item_count)
        val navControl = recordView.findNavController()




    }


}