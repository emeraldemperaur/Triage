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
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.utils.InsightAPIManager
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
    private var patientsFound = arrayListOf<Patient>()
    private var diagnosesFound = arrayListOf<Diagnosis>()
    private var prescriptionsFound = arrayListOf<Prescription>()
    private var visitsFound = arrayListOf<Visit>()
    private var pharmaceuticalsFound = arrayListOf<Pharmaceuticals>()
    private var pharmaceuticalsFoundII = arrayListOf<Pharmaceuticals>()
    private val args: InsightModelDialogFragmentArgs by navArgs()
    private lateinit var insightAPIManager: InsightAPIManager



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
        insightAPIManager = InsightAPIManager(insightModel, requireContext())
        collateData(insightModel)
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
                    val patientInsights = Patients()
                    patientInsights.addAll(patientsFound)
                    if (patientInsights.isNotEmpty()) {
                        println("\n\nInsight Patient Data Found: $patientInsights\n\n")
                    }
                    val diagnosesInsights = Diagnoses()
                    diagnosesInsights.addAll(diagnosesFound)
                    if (diagnosesInsights.isNotEmpty()) {
                        println("\n\nInsight Diagnoses Data Found: $diagnosesInsights\n\n")
                    }
                    val prescriptionInsights = Prescriptions()
                    prescriptionInsights.addAll(prescriptionsFound)
                    if (prescriptionInsights.isNotEmpty()) {
                        println("\n\nInsight Prescription Data Found: $prescriptionInsights\n\n")
                    }
                    val visitInsights = Visits()
                    visitInsights.addAll(visitsFound)
                    if (visitInsights.isNotEmpty()) {
                        println("\n\nInsight Visit Data Found: $visitInsights\n\n")
                    }
                    val pharmaceuticalsInsights = Pharmaceutical()
                    pharmaceuticalsInsights.addAll(pharmaceuticalsFound)
                    if (pharmaceuticalsInsights.isNotEmpty()) {
                        println("\n\nInsight Pharmaceutical Data Found: $pharmaceuticalsInsights\n\n")
                    }
                    val pharmaceuticalsJuxtaposition = Pharmaceutical()
                    pharmaceuticalsJuxtaposition.addAll(pharmaceuticalsFoundII)
                    if (pharmaceuticalsJuxtaposition.isNotEmpty()) {
                        println("\n\nJuxtaposition Pharmaceutical Data Found: $pharmaceuticalsJuxtaposition\n\n")
                    }
                    val input = InsightModelDialogFragmentDirections.viewInsightAction(insightModel, patientInsights
                        , diagnosesInsights, prescriptionInsights, visitInsights, pharmaceuticalsInsights, pharmaceuticalsJuxtaposition)
                   navigationControl.navigate(input)
                }
            }, 10369)
        }
    }

    private fun collateData(insightModel: InsightModel){
        when(insightModel.entityCode){
            1 ->{
                var result: Boolean
                val fetchedPatients = arrayListOf<Patient>()
                if (view != null) {
                    insightAPIManager.patientRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedPatients.isEmpty()) {
                                    fetchedPatients.addAll(reply)
                                    patientsFound = fetchedPatients
                                    println("Patient Insight Records output is not empty: $result")
                                    println("See Chiron Insight (Patient) output result: $reply")
                                }
                            }
                        })
                }
            }
            2 ->{
                var result: Boolean
                val fetchedDiagnoses = arrayListOf<Diagnosis>()
                if (view != null) {
                    insightAPIManager.diagnosisRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedDiagnoses.isEmpty()) {
                                    fetchedDiagnoses.addAll(reply)
                                    diagnosesFound = fetchedDiagnoses
                                    println("Diagnosis Insight Records output is not empty: $result")
                                    println("See Chiron Insight (Diagnosis) output result: $reply")
                                }
                            }
                        })
                }
            }
            3 ->{
                var result: Boolean
                val fetchedPrescriptions = arrayListOf<Prescription>()
                if (view != null) {
                    insightAPIManager.prescriptionRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedPrescriptions.isEmpty()) {
                                    fetchedPrescriptions.addAll(reply)
                                    prescriptionsFound = fetchedPrescriptions
                                    println("Prescription Insight Records output is not empty: $result")
                                    println("See Chiron Insight (Prescription) output result: $reply")
                                }
                            }
                        })
                }
            }
            4 ->{
                var result: Boolean
                val fetchedVisits = arrayListOf<Visit>()
                if (view != null) {
                    insightAPIManager.visitRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedVisits.isEmpty()) {
                                    fetchedVisits.addAll(reply)
                                    visitsFound = fetchedVisits
                                    println("Visit Insight Records output is not empty: $result")
                                    println("See Chiron Insight (Visit) output result: $reply")
                                }
                            }
                        })
                }
            }
            5 ->{
                var result: Boolean
                val fetchedPharmaceuticals = arrayListOf<Pharmaceuticals>()
                if (view != null) {
                    insightAPIManager.pharmaceuticalRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedPharmaceuticals.isEmpty()) {
                                    fetchedPharmaceuticals.addAll(reply)
                                    pharmaceuticalsFound = fetchedPharmaceuticals
                                    println("Pharmaceutical Expiry Date Insight Records output is not empty: $result")
                                    println("See Chiron Insight (Pharmaceutical) output result: $reply")
                                }
                            }
                        })
                }
                var resultII: Boolean
                val fetchPharmaceuticalsII = arrayListOf<Pharmaceuticals>()
                if (view != null) {
                    insightAPIManager.pharmaceuticalRecordsII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultII = reply.isNotEmpty()
                                if (fetchPharmaceuticalsII.isEmpty()) {
                                    fetchPharmaceuticalsII.addAll(reply)
                                    pharmaceuticalsFoundII = fetchPharmaceuticalsII
                                    println("Pharmaceutical Make Date Insight Records response output is not empty: $resultII")
                                    println("See Chiron Insight (Pharmaceutical) output II result: $reply")
                                }
                            }
                        })
                }


            }
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