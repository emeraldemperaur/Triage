package iot.empiaurhouse.triage.adapter

import android.app.Application
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.utils.subscribeOnBackground
import iot.empiaurhouse.triage.view.PivotsFragmentDirections
import iot.empiaurhouse.triage.viewmodel.DataPivotViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

class DataPivotsAdapter(private val dataPivotList: ArrayList<DataPivot>, private val pivotsViewObject: View,
                        private val activity: ViewModelStoreOwner,
                        private val application: Application): RecyclerView.Adapter<DataPivotsAdapter.ViewHolder>() {

    private lateinit var pivotContext: Context
    private lateinit var hubView: View
    private lateinit var pivotController: PivotController
    private lateinit var pivotViewModel: DataPivotViewModel



    @InternalCoroutinesApi
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.datapivots_list_view, parent, false)
        pivotContext = parent.context
        pivotController = PivotController()
        pivotViewModel = ViewModelProvider(activity).get(DataPivotViewModel::class.java)
        pivotViewModel.processPivot(application)
        hubView = pivotsViewObject
        val holder = ViewHolder(v)
        holder.dataPivotsItem.setOnClickListener {  }
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPivot = dataPivotList[position]
        val navControls = holder.dataPivotsNavControl
        holder.dataPivotsIcon.setImageDrawable(ContextCompat.getDrawable(pivotContext, pivotIconController(dataPivot)))
        holder.dataPivotsLabel.text = dataPivot.alias.capitalize(Locale.ROOT)
        holder.dataPivotsModel.text = pivotController.pivotObjectModelCheck(dataPivot)
        holder.dataPivotsEndPoint.text = pivotController.pivotObjectEndPointCheck(dataPivot)
        val holderText = ""
        if (isDatePivot(dataPivot)){
            holder.dataPivotsParamI.text = pivotController.pivotObjectDateFormat(dataPivot.dateParameterA)
            if (dataPivot.timeStreamCode == 4){
                holder.dataPivotsParamII.text = pivotController.pivotObjectDateFormat(dataPivot.dateParameterB)
                holder.dataPivotsParamIII.text = holderText
            } else{
                holder.dataPivotsParamII.text = holderText
                holder.dataPivotsParamIII.text = holderText
            }
        }
        else if (!isDatePivot(dataPivot)){
            holder.dataPivotsParamI.text = pivotController.pivotsObjectValueHolderCheck(dataPivot.valueParameterA)
            holder.dataPivotsParamII.text = pivotController.pivotsObjectValueHolderCheck(dataPivot.valueParameterB)
            holder.dataPivotsParamIII.text = pivotController.pivotsObjectValueHolderCheck(dataPivot.valueParameterC)
        }

        holder.dataPivotsItem.setOnClickListener {
            val input = PivotsFragmentDirections.openPivotAction(dataPivot)
            navControls.navigate(input)

        }
    }

    override fun getItemCount(): Int {
        return dataPivotList.size
    }

    private fun restorePivot(dataPivot: DataPivot, position: Int){
        dataPivotList.add(position, dataPivot)
        subscribeOnBackground {
            pivotViewModel.insertDataPivot(dataPivot)
            println("Resorted Data Pivot to DB:\n $dataPivot")
        }
        notifyItemInserted(position)
    }


    fun deletePivot(position: Int){
        val focusPivot = dataPivotList[position]
        subscribeOnBackground {
            pivotViewModel.deleteDataPivot(focusPivot)
            println("Deleted Data Pivot from DB:\n $focusPivot")
        }
        dataPivotList.removeAt(position)
        notifyItemRemoved(position)
        val deletedPrompt = Snackbar.make(pivotsViewObject,"Deleted Pivot | ${focusPivot.alias.capitalize(
            Locale.ROOT
        )
        }", Snackbar.LENGTH_LONG)
        deletedPrompt.setAction("UNDO", View.OnClickListener {

            restorePivot(focusPivot, position)

        })
        deletedPrompt.anchorView = pivotsViewObject.rootView.findViewById(R.id.hub_foot_nav)
        deletedPrompt.show()

    }



    private fun pivotIconController(dataPivot: DataPivot): Int{
        var drawableInt = 0
        val tSC = dataPivot.timeStreamCode
        val vPC = dataPivot.valueParamCode
        if (tSC != null){
            when(tSC){
                1 -> {
                    drawableInt = R.drawable.on_time_icon
                }
                2 -> {
                    drawableInt = R.drawable.before_icon
                }
                3 -> {
                    drawableInt = R.drawable.after_icon
                }
                4 -> {
                    drawableInt = R.drawable.between_time_icon
                }
            }
        }
        else if (vPC != null || dataPivot.valueParamCode!! > 0){
            drawableInt = R.drawable.text_field_icon
        }
        return drawableInt
    }


    private fun isDatePivot(dataPivot: DataPivot): Boolean{
        val tSC = dataPivot.timeStreamCode
        val vPC = dataPivot.valueParamCode
        var isDate = false
        if (tSC != null){
            isDate = true
        }
        else if (vPC != null || dataPivot.valueParamCode!! > 0){
            isDate = false
        }
        return isDate
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dataPivotsIcon: ImageView = itemView.findViewById(R.id.pivots_list_pivot_type_icon)
        val dataPivotsLabel: TextView = itemView.findViewById(R.id.pivots_list_label)
        val dataPivotsModel: TextView = itemView.findViewById(R.id.pivots_list_entity_text)
        val dataPivotsEndPoint: TextView = itemView.findViewById(R.id.pivots_list_endpoint_text)
        val dataPivotsParamI:  TextView = itemView.findViewById(R.id.pivots_list_param_text)
        val dataPivotsParamII:  TextView = itemView.findViewById(R.id.pivots_list_param_sub_text)
        val dataPivotsParamIII:  TextView = itemView.findViewById(R.id.pivots_list_param_unsub_text)
        val dataPivotsItem: MaterialCardView = itemView.findViewById(R.id.pivots_list_view)
        val dataPivotsNavControl = hubView.findNavController()


    }


}