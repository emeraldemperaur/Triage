package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.Pivot
import iot.empiaurhouse.triage.view.DashboardFragmentDirections

class PivotRecyclerAdapter(private val pivotList: ArrayList<Pivot>, private val hubViewObject: View): RecyclerView.Adapter<PivotRecyclerAdapter.ViewHolder>() {

    private lateinit var pivotContext: Context
    private lateinit var hubView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.quick_pivot_item, parent, false)
        pivotContext = parent.context
        hubView = hubViewObject
        val holder = ViewHolder(v)
        holder.pivotItem.setOnClickListener {  }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pivot = pivotList[position]
        val navControls = holder.navControl
        holder.pivotIcon.setImageDrawable(ContextCompat.getDrawable(pivotContext, pivotList[position].drawableResourceID))
        holder.pivotTitle.text = pivotList[position].title.toString()
        holder.pivotItem.setOnClickListener {
            when (pivot.id) {
                1 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(1)
                    navControls.navigate(input)
                }
                2 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(2)
                    navControls.navigate(input)
                }
                3 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(3)
                    navControls.navigate(input)
                }
                4 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(4)
                    navControls.navigate(input)
                }
                5 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(5)
                    navControls.navigate(input)
                }
                6 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(6)
                    navControls.navigate(input)
                }
                7 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(6)
                    navControls.navigate(input)
                }
                8 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(6)
                    navControls.navigate(input)
                }
                9 -> {
                    //toastSnippet(pivot)
                    val input = DashboardFragmentDirections.quickPivotAction(6)
                    navControls.navigate(input)
                    true
                }
                else -> { // Note the block
                    false
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
        val navControl = hubView.findNavController()


    }


}