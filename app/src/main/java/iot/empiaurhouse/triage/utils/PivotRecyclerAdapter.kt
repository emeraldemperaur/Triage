package iot.empiaurhouse.triage.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.Pivot

class PivotRecyclerAdapter(private val pivotList: ArrayList<Pivot>): RecyclerView.Adapter<PivotRecyclerAdapter.ViewHolder>() {

    private lateinit var pivotContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PivotRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.quick_pivot_item, parent, false)
        pivotContext = parent.context
        val holder = ViewHolder(v)
        holder.pivotItem.setOnClickListener {  }

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PivotRecyclerAdapter.ViewHolder, position: Int) {
        val pivot = pivotList[position]
        holder.pivotIcon.setImageDrawable(ContextCompat.getDrawable(pivotContext, pivotList[position].drawableResourceID))
        holder.pivotTitle.text = pivotList[position].title.toString()
        holder.pivotItem.setOnClickListener {
            when (pivot.id) {
                1 -> {
                    toastSnippet(pivot)
                }
                2 -> {
                    toastSnippet(pivot)
                }
                3 -> {
                    toastSnippet(pivot)
                }
                4 -> {
                    toastSnippet(pivot)
                }
                5 -> {
                    toastSnippet(pivot)
                }
                6 -> {
                    toastSnippet(pivot)
                }
                7 -> {
                    toastSnippet(pivot)
                }
                8 -> {
                    toastSnippet(pivot)
                }
                9 -> {
                    toastSnippet(pivot)
                }
                else -> { // Note the block
                    println("Pivot Adapter Anomaly Detected! ->> Invalid or Null Pivot ID found for Object: ${pivot.hashCode()}")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pivotList.size
    }

    private fun toastSnippet(pivotItem: Pivot){
        Toast.makeText(pivotContext,"Pivot Item Selected: ${pivotItem.title} -- ID: $${pivotItem.id.toString()}",Toast.LENGTH_LONG).show()

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pivotIcon: ImageView = itemView.findViewById(R.id.quick_pivot_icon_img)
        val pivotTitle: TextView = itemView.findViewById(R.id.quick_pivot_title)
        val pivotItem: CardView = itemView.findViewById(R.id.quick_pivot_card)


    }


}