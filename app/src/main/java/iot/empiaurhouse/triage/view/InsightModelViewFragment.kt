package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.InsightModelController
import iot.empiaurhouse.triage.databinding.FragmentInsightModelViewBinding
import iot.empiaurhouse.triage.model.InsightModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InsightModelViewFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInsightModelViewBinding
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolBar: Toolbar
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var insightLabel: TextView
    private lateinit var insightModel: TextView
    private lateinit var insightEndPoint: TextView
    private lateinit var insightResultCount: TextView
    private lateinit var insightResultCountIcon: ImageView
    private lateinit var vistaType: TextView
    private lateinit var insightRange: TextView
    private lateinit var piParam: TextView
    private lateinit var omegaParam: TextView
    private lateinit var piParamTitle: TextView
    private lateinit var omegaParamTitle: TextView
    private lateinit var noResultsText: TextView
    private lateinit var rangeBaseTitle: TextView
    private lateinit var exitInsight: FloatingActionButton
    private lateinit var navigationControls: NavController
    private lateinit var stagedInsightModel: InsightModel
    private lateinit var insightModelController: InsightModelController
    private val args: InsightModelViewFragmentArgs by navArgs()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insight_model_view, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInsightModelViewBinding.bind(view)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolBar = requireActivity().findViewById(R.id.hub_toolbar)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        navigationControls = view.findNavController()
        insightLabel = binding.insightDetailInsightLabel
        insightModel = binding.insightDetailModelText
        insightEndPoint = binding.insightDetailEndpointText
        insightResultCount = binding.insightDetailResultsCount
        insightResultCountIcon = binding.insightDetailResultIcon
        vistaType = binding.insightDetailInsightVistaTypeText
        insightRange = binding.insightDetailInsightRangeTitle
        piParam = binding.insightDetailInsightPiParametersText
        piParamTitle = binding.insightDetailInsightPiParametersTitle
        omegaParam = binding.insightDetailInsightOmegaParametersText
        omegaParamTitle = binding.insightDetailInsightOmegaParametersTitle
        noResultsText = binding.insightDetailNoResults
        rangeBaseTitle = binding.insightDetailRangeBaseTitle
        exitInsight = binding.exitInsightModel
        stagedInsightModel = args.insightModel
        onBackPressed()
        initInsightModelView(stagedInsightModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initInsightModelView(insightModelObject: InsightModel){
        when(insightModelObject.entityCode){
            1 ->{
                renderResultsView(args.patient!!.size)
            }
            2 ->{
                renderResultsView(args.diagnosis!!.size)
            }
            3 ->{
                renderResultsView(args.prescription!!.size)
            }
            4 ->{
                renderResultsView(args.visit!!.size)
            }
            5 ->{
                renderResultsView(args.pharmaceutical!!.size)
            }
        }
        toolBar.visibility = View.VISIBLE
        toolbarView.visibility = View.VISIBLE
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        insightModelController = InsightModelController()
        insightLabel.text = insightModelObject.alias
        insightModel.text = insightModelController.fetchInsightEntityTitle(insightModelObject.entityCode!!)
        insightEndPoint.text = insightModelObject.pointOfInterest
        insightResultCountIcon.setImageDrawable(
            ContextCompat.getDrawable(requireContext(),
            insightModelController.fetchInsightIcon(insightModelObject.vistaCode!!)))
        vistaType.text = insightModelController.fetchInsightTypeTitle(insightModelObject.vistaCode)
        val insightRangeText = "${insightModelController.insightObjectDateFormat(insightModelObject.rangeStartDate)} " +
                "-\n${insightModelController.insightObjectDateFormat(insightModelObject.rangeEndDate)}"
        rangeBaseTitle.text = insightModelController.fetchRangeBaseTitle(insightModelObject.entityCode)
        insightRange.text = insightRangeText
        piParam.text = insightModelObject.piThresholdValue
        piParamTitle.visibility = View.VISIBLE
        piParam.visibility = View.VISIBLE
        if (insightModelObject.vistaCode == 3 || insightModelObject.vistaCode == 4){
            omegaParam.text = insightModelObject.omegaThresholdValue
            omegaParamTitle.visibility = View.VISIBLE
            omegaParam.visibility = View.VISIBLE
        }

        exitInsight.setOnClickListener {
            hubUserName.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            toolbarView.visibility = View.VISIBLE
            val input = InsightModelViewFragmentDirections.exitInsightDetail()
            navigationControls.navigate(input)
        }


    }

    private fun renderResultsView(recordsFound: Int?){
        if (recordsFound == null || recordsFound < 1){
            //insightView.visibility = View.GONE
            noResultsText.visibility = View.VISIBLE
            insightResultCount.text = 0.toString()

        }
        else if (recordsFound > 0){
            noResultsText.visibility = View.GONE
            insightResultCount.text = recordsFound.toString()
            //insightView.visibility = View.VISIBLE
        }
    }



    override fun onResume() {
        super.onResume()
        toolbarView.visibility = View.VISIBLE
        hubUserName.visibility = View.INVISIBLE
        searchButton.visibility = View.INVISIBLE
    }


    fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                isEnabled = true
                requireActivity().moveTaskToBack(true)
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsightModelViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}