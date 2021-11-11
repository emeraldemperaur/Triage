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
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.InsightModelController
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.viewmodel.InsightModelViewModel
import java.util.*

class InsightModelsAdapter(private val insightModelList: ArrayList<InsightModel>, private val insightViewObject: View,
                           private val activity: ViewModelStoreOwner,
                           private val application: Application
): RecyclerView.Adapter<InsightModelsAdapter.ViewHolder>() {

    private lateinit var insightHubView: View
    private lateinit var insightContext: Context
    private lateinit var insightController: InsightModelController
    private lateinit var insightViewModel: InsightModelViewModel


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val insightModelIcon: ImageView = itemView.findViewById(R.id.insight_model_list_vista_type_icon)
        val insightModelLabel: TextView = itemView.findViewById(R.id.insight_model_list_label)
        val insightModelEntity: TextView = itemView.findViewById(R.id.insight_model_list_entity_text)
        val insightModelEndPoint: TextView = itemView.findViewById(R.id.insight_model_list_endpoint_text)
        val insightModelParamI: TextView = itemView.findViewById(R.id.insight_model_list_param_text)
        val insightModelParamII: TextView = itemView.findViewById(R.id.insight_model_list_param_sub_text)
        val insightModelRange: TextView = itemView.findViewById(R.id.insight_model_list_range_text)
        val insightModelItem: MaterialCardView = itemView.findViewById(R.id.insight_model_list_view)
        val dataPivotsNavControl = insightHubView.findNavController()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.insight_model_list_view, parent, false)
        insightContext = parent.context
        insightController = InsightModelController()
        insightViewModel = ViewModelProvider(activity)[InsightModelViewModel::class.java]
        insightViewModel.processPivot(application)
        insightHubView = insightViewObject
        val holder = ViewHolder(v)
        holder.insightModelItem.setOnClickListener {  }
        return ViewHolder(v)    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val insightModel = insightModelList[position]
        val navControls = holder.dataPivotsNavControl
        holder.insightModelLabel.text = insightModel.alias
        holder.insightModelEntity.text = insightController.fetchInsightEntityTitle(insightModel.entityCode!!)
        holder.insightModelEndPoint.text = insightModel.pointOfInterest
        val insightRange = "${insightController.insightObjectDateFormat(insightModel.rangeStartDate)} - ${insightController.insightObjectDateFormat(insightModel.rangeEndDate)}"
        holder.insightModelRange.text = insightRange
        holder.insightModelParamI.text = insightModel.piThresholdValue
        holder.insightModelIcon.setImageDrawable(ContextCompat.getDrawable(insightContext,
            insightController.fetchInsightIcon(insightModel.vistaCode!!)))
        if (!insightModel.omegaThresholdValue.isNullOrBlank()){
            holder.insightModelParamII.text = insightModel.omegaThresholdValue
            holder.insightModelParamII.visibility = View.VISIBLE
        }
        holder.insightModelItem.setOnClickListener {
            //val input = PivotsFragmentDirections.openPivotAction(dataPivot)
            //navControls.navigate(input)

        }

    }

    override fun getItemCount(): Int {
        return insightModelList.size
    }

}