package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.InsightModelController
import iot.empiaurhouse.triage.databinding.FragmentInsightModelDialogBinding
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.utils.TypeWriterTextView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InsightModelDialogFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInsightModelDialogBinding
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var insightModel: InsightModel
    private lateinit var navigationControl: NavController
    private lateinit var triageBot: ImageView
    private lateinit var insightDialogView: MaterialCardView
    private lateinit var renderingText: TypeWriterTextView
    private lateinit var insightLabel: TextView
    private lateinit var dataModelTitle: TextView
    private lateinit var dataModelText: TextView
    private lateinit var endPointTitle: TextView
    private lateinit var endPointText: TextView
    private lateinit var insightTypeTitle: TextView
    private lateinit var insightTypeText: TextView
    private lateinit var insightRangeTitle: TextView
    private lateinit var insightRangeText: TextView
    private lateinit var piThresholdParamText: TextView
    private lateinit var piThresholdParamTitle: TextView
    private lateinit var omegaThresholdParamText: TextView
    private lateinit var omegaThresholdParamTitle: TextView
    private lateinit var renderProgress: ProgressBar
    private var renderComplete: Boolean = false
    private val args: InsightModelDialogFragmentArgs by navArgs()


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
        return inflater.inflate(R.layout.fragment_insight_model_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInsightModelDialogBinding.bind(view)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        navigationControl = view.findNavController()
        insightModel = args.insightModel
        triageBot = binding.insightTriageBot
        insightDialogView = binding.insightDialogInfo
        renderingText = binding.renderingPrompt
        insightLabel = binding.insightLabelTitle
        dataModelTitle = binding.insightDmTitle
        dataModelText = binding.insightDmTitleText
        endPointTitle = binding.epText
        endPointText = binding.epText
        insightTypeTitle = binding.vtTitle
        insightTypeText = binding.vtText
        insightRangeTitle = binding.rangeInsightsTitle
        insightRangeText = binding.insightRangeText
        piThresholdParamTitle = binding.piInsightThresholdTitle
        piThresholdParamText = binding.piInsightParamText
        omegaThresholdParamTitle = binding.omegaInsightParamTitle
        omegaThresholdParamText = binding.omegaInsightParamText
        renderProgress = binding.insightProgress
        val insightController = InsightModelController()
        renderComplete = insightController.initInsightDialog(requireContext(), insightModel, triageBot,
            insightDialogView, renderingText,insightLabel,dataModelTitle, dataModelText,endPointTitle,
            endPointText, insightTypeTitle, insightTypeText, insightRangeTitle, insightRangeText,
            piThresholdParamText,piThresholdParamTitle,omegaThresholdParamText, omegaThresholdParamTitle
            ,renderProgress)
        initInsightDialogView()
        onBackPressed()
    }


    private fun initInsightDialogView(){
        toolbarView.visibility = View.GONE
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        toolbarView.visibility = View.GONE
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
        if (navigationControl.currentDestination == navigationControl.graph.findNode(R.id.insight_model_dialog)){
            insightModelProcess(renderComplete)
        }
    }

    private fun insightModelProcess(renderComplete: Boolean){
        if (renderComplete || !renderComplete){
            Handler(Looper.getMainLooper()).postDelayed({
                if (navigationControl.currentDestination == navigationControl.graph.findNode(R.id.insight_model_dialog)) {
                    val input = InsightModelDialogFragmentDirections.viewInsightAction(insightModel)
                   navigationControl.navigate(input)
                }
            }, 10000)

        }
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
            InsightModelDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}