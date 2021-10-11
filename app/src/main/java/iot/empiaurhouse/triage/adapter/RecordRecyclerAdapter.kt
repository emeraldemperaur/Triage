package iot.empiaurhouse.triage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.ChironRecords

class RecordRecyclerAdapter(private val recordsList: ArrayList<ChironRecords>): RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.records_list_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recordItemTitle.text = recordsList[position].recordName
        holder.recordItemType.text = recordsList[position].recordType
        holder.recordItemCount.text = recordsList[position].recordCount.toString()
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recordItemTitle: TextView = itemView.findViewById(R.id.records_item_title)
        val recordItemType: TextView = itemView.findViewById(R.id.records_item_type)
        val recordItemCount: TextView = itemView.findViewById(R.id.records_item_count)



    }


}