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
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.databinding.FragmentPivotDialogBinding
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.utils.PivotAPIManager
import iot.empiaurhouse.triage.utils.TypeWriterTextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class PivotDialogFragment : DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPivotDialogBinding
    private lateinit var viewObject: View
    private lateinit var triageBot: ImageView
    private lateinit var pivotDialogView: MaterialCardView
    private lateinit var pivotingText: TypeWriterTextView
    private lateinit var pivotLabel: TextView
    private lateinit var dataModelTitle: TextView
    private lateinit var dataModelText: TextView
    private lateinit var endPointTitle: TextView
    private lateinit var endPointText: TextView
    private lateinit var pivotTypeTitle: TextView
    private lateinit var pivotTypeText: TextView
    private lateinit var parameterTitle: TextView
    private lateinit var alphaParamTitle: TextView
    private lateinit var alphaParamText: TextView
    private lateinit var betaParamTitle: TextView
    private lateinit var betaParamText: TextView
    private lateinit var epsilonParamTitle: TextView
    private lateinit var epsilonParamText: TextView
    private lateinit var timeStreamTitle: TextView
    private lateinit var timeStreamText: TextView
    private lateinit var chiParamTitle: TextView
    private lateinit var chiParamText: TextView
    private lateinit var psiParamTitle: TextView
    private lateinit var psiParamText: TextView
    private lateinit var pivotProgress: ProgressBar
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var hubUserName: TextView
    private lateinit var pivotController: PivotController
    private lateinit var stagedDataPivot: DataPivot
    private var renderComplete: Boolean = false
    private lateinit var navigationControl: NavController
    private val args: PivotDialogFragmentArgs by navArgs()
    private var patientsFound = arrayListOf<Patient>()
    private var patientsFoundII = arrayListOf<Patient>()
    private var patientsFoundIII = arrayListOf<Patient>()
    private var diagnosesFound = arrayListOf<Diagnosis>()
    private var diagnosesFoundII = arrayListOf<Diagnosis>()
    private var diagnosesFoundIII = arrayListOf<Diagnosis>()
    private var prescriptionsFound = arrayListOf<Prescription>()
    private var prescriptionsFoundII = arrayListOf<Prescription>()
    private var prescriptionsFoundIII = arrayListOf<Prescription>()
    private var visitsFound = arrayListOf<Visit>()
    private var visitsFoundII = arrayListOf<Visit>()
    private var visitsFoundIII = arrayListOf<Visit>()
    private var practitionersFound = arrayListOf<Practitioner>()
    private var doctorsFound = arrayListOf<Doctor>()
    private var nursePractitionersFound = arrayListOf<NursePractitioner>()
    private var registeredNursesFound = arrayListOf<RegisteredNurse>()
    private var pharmaceuticalsFound = arrayListOf<Pharmaceuticals>()
    private lateinit var pivotAPIManager: PivotAPIManager




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
        return inflater.inflate(R.layout.fragment_pivot_dialog, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotDialogBinding.bind(view)
        triageBot = binding.triageBot
        viewObject = binding.pivotDialogView
        pivotDialogView = binding.pivotDialogInfo
        pivotingText = binding.pivotingPrompt
        pivotLabel = binding.pivotLabelTitle
        dataModelTitle = binding.dmTitle
        dataModelText = binding.dmTitleText
        endPointTitle = binding.epTitle
        endPointText = binding.epText
        pivotTypeTitle = binding.ptTitle
        pivotTypeText = binding.ptText
        parameterTitle = binding.paramTitle
        alphaParamTitle = binding.alphaParamTitle
        alphaParamText = binding.alphaParamText
        betaParamTitle = binding.betaParamTitle
        betaParamText = binding.betaParamText
        epsilonParamTitle = binding.epsilonParamTitle
        epsilonParamText = binding.epsilonParamText
        timeStreamTitle = binding.timeStreamTitle
        timeStreamText = binding.timeStreamText
        chiParamTitle = binding.chiParamTitle
        chiParamText = binding.chiParamText
        psiParamTitle = binding.psiParamTitle
        psiParamText = binding.psiParamText
        pivotProgress = binding.pivotProgress
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        searchButton.visibility = View.GONE
        hubUserName.visibility = View.GONE
        toolbarView.visibility = View.GONE
        navigationControl = view.findNavController()
        pivotController = PivotController()
        stagedDataPivot = args.dataPivot
        pivotAPIManager = PivotAPIManager(stagedDataPivot, requireContext())
        collateData(stagedDataPivot)
        renderComplete = pivotController.initPivotDialog(requireContext(), stagedDataPivot, navigationControl,
            triageBot, pivotDialogView, pivotingText,
        pivotLabel,dataModelTitle, dataModelText, endPointTitle, endPointText, pivotTypeTitle,pivotTypeText, parameterTitle,
        alphaParamTitle, alphaParamText, betaParamTitle, betaParamText, epsilonParamTitle, epsilonParamText, timeStreamTitle,
        timeStreamText, chiParamTitle, chiParamText, psiParamTitle, psiParamText, pivotProgress)
        onBackPressed()


    }

    private fun pivotProcess(renderComplete: Boolean){
        if (renderComplete || !renderComplete){
            Handler(Looper.getMainLooper()).postDelayed({
                if (navigationControl.currentDestination == navigationControl.graph.findNode(R.id.pivot_dialog)) {
                    val patientPivots = Patients()
                    patientPivots.addAll(patientsFound)
                    patientPivots.addAll(patientsFoundII)
                    patientPivots.addAll(patientsFoundIII)
                    if (patientPivots.isNotEmpty()) {
                        println("\n\nMerged Patient Pivots Found: $patientPivots\n\n")
                    }
                    val diagnosesPivots = Diagnoses()
                    diagnosesPivots.addAll(diagnosesFound)
                    diagnosesPivots.addAll(diagnosesFoundII)
                    diagnosesPivots.addAll(diagnosesFoundIII)
                    if (diagnosesPivots.isNotEmpty()) {
                        println("\n\nMerged Diagnoses Pivots Found: $diagnosesPivots\n\n")
                    }
                    val prescriptionPivots = Prescriptions()
                    prescriptionPivots.addAll(prescriptionsFound)
                    prescriptionPivots.addAll(prescriptionsFoundII)
                    prescriptionPivots.addAll(prescriptionsFoundIII)
                    if (prescriptionPivots.isNotEmpty()) {
                        println("\n\nMerged Prescription Pivots Found: $prescriptionPivots\n\n")
                    }
                    val visitPivots = Visits()
                    visitPivots.addAll(visitsFound)
                    val practitionerPivots = Practitioners()
                    practitionerPivots.addAll(practitionersFound)
                    val doctorPivots = Doctors()
                    doctorPivots.addAll(doctorsFound)
                    val nursePractitionerPivots = NursePractitioners()
                    nursePractitionerPivots.addAll(nursePractitionersFound)
                    val registeredNursePivots = RegisteredNurses()
                    registeredNursePivots.addAll(registeredNursesFound)
                    val pharmaceuticalsPivots = Pharmaceutical()
                    pharmaceuticalsPivots.addAll(pharmaceuticalsFound)
                    val input = PivotDialogFragmentDirections.viewPivotAction(stagedDataPivot,
                        patientPivots,
                        diagnosesPivots,
                        prescriptionPivots,
                        visitPivots,
                        practitionerPivots,
                        doctorPivots,
                        registeredNursePivots,
                        nursePractitionerPivots,
                        pharmaceuticalsPivots
                    )
                    navigationControl.navigate(input)
                }
            }, 10369)

        }
    }

    private fun collateData(dataPivot: DataPivot){
        when(dataPivot.entityCode){
            1 ->{
                var result: Boolean
                val fetchedPatients = arrayListOf<Patient>()
                if (view != null) {
                    pivotAPIManager.patientRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedPatients.isEmpty()) {
                                    fetchedPatients.addAll(reply)
                                    patientsFound = fetchedPatients
                                    println("Patient Pivot Records output is not empty: $result")
                                    println("See Chiron Pivot (Patient) output result: $reply")
                                }
                            }
                        })
                }
                var resultII: Boolean
                val fetchedPatientsII = arrayListOf<Patient>()
                if (view != null) {
                    pivotAPIManager.patientRecordsII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultII = reply.isNotEmpty()
                                if (fetchedPatientsII.isEmpty()) {
                                    fetchedPatientsII.addAll(reply)
                                    patientsFoundII = fetchedPatientsII
                                    println("Patient Pivot Records II response output is not empty: $resultII")
                                    println("See Chiron Pivot (Patient) output II result: $reply")
                                }
                            }
                        })
                }
                var resultIII: Boolean
                val fetchedPatientsIII = arrayListOf<Patient>()
                if (view != null) {
                    pivotAPIManager.patientRecordsIII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultIII = reply.isNotEmpty()
                                if (fetchedPatientsIII.isEmpty()) {
                                    fetchedPatientsIII.addAll(reply)
                                    patientsFoundIII = fetchedPatientsIII
                                    println("Patient Pivot Records III response object is not empty: $resultIII")
                                    println("See Chiron Pivot (Patient) response III result: $reply")
                                }
                            }
                        })
                }
            }
            2 ->{
                var result: Boolean
                val fetchedDiagnoses = arrayListOf<Diagnosis>()
                if (view != null) {
                    pivotAPIManager.diagnosisRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedDiagnoses.isEmpty()) {
                                    fetchedDiagnoses.addAll(reply)
                                    diagnosesFound = fetchedDiagnoses
                                    println("Diagnosis Pivot Records output is not empty: $result")
                                    println("See Chiron Pivot (Diagnosis) output result: $reply")
                                }
                            }
                        })
                }
                var resultII: Boolean
                val fetchedDiagnosesII = arrayListOf<Diagnosis>()
                if (view != null) {
                    pivotAPIManager.diagnosisRecordsII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultII = reply.isNotEmpty()
                                if (fetchedDiagnosesII.isEmpty()) {
                                    fetchedDiagnosesII.addAll(reply)
                                    diagnosesFoundII = fetchedDiagnosesII
                                    println("Diagnosis Pivot Records II response output is not empty: $resultII")
                                    println("See Chiron Pivot (Diagnosis) output II result: $reply")
                                }
                            }
                        })
                }
                var resultIII: Boolean
                val fetchedDiagnosesIII = arrayListOf<Diagnosis>()
                if (view != null) {
                    pivotAPIManager.diagnosisRecordsIII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultIII = reply.isNotEmpty()
                                if (fetchedDiagnosesIII.isEmpty()) {
                                    fetchedDiagnosesIII.addAll(reply)
                                    diagnosesFoundIII = fetchedDiagnosesIII
                                    println("Diagnosis Pivot Records III response object is not empty: $resultIII")
                                    println("See Chiron Pivot (Diagnosis) response III result: $reply")
                                }
                            }
                        })
                }
            }
            3 ->{
                var result: Boolean
                val fetchedPrescriptions = arrayListOf<Prescription>()
                if (view != null) {
                    pivotAPIManager.prescriptionRecords.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                result = reply.isNotEmpty()
                                if (fetchedPrescriptions.isEmpty()) {
                                    fetchedPrescriptions.addAll(reply)
                                    prescriptionsFound = fetchedPrescriptions
                                    println("Prescription Pivot Records output is not empty: $result")
                                    println("See Chiron Pivot (Prescription) output result: $reply")
                                }
                            }
                        })
                }
                var resultII: Boolean
                val fetchedPrescriptionsII = arrayListOf<Prescription>()
                if (view != null) {
                    pivotAPIManager.prescriptionRecordsII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultII = reply.isNotEmpty()
                                if (fetchedPrescriptionsII.isEmpty()) {
                                    fetchedPrescriptionsII.addAll(reply)
                                    prescriptionsFoundII = fetchedPrescriptionsII
                                    println("Prescription Pivot Records II response output is not empty: $resultII")
                                    println("See Chiron Pivot (Prescription) output II result: $reply")
                                }
                            }
                        })
                }
                var resultIII: Boolean
                val fetchedPrescriptionsIII = arrayListOf<Prescription>()
                if (view != null) {
                    pivotAPIManager.prescriptionRecordsIII.observe(
                        viewLifecycleOwner,
                        androidx.lifecycle.Observer { reply ->
                            reply?.let {
                                resultIII = reply.isNotEmpty()
                                if (fetchedPrescriptionsIII.isEmpty()) {
                                    fetchedPrescriptionsIII.addAll(reply)
                                    prescriptionsFoundIII = fetchedPrescriptionsIII
                                    println("Prescription Pivot Records III response object is not empty: $resultIII")
                                    println("See Chiron Pivot (Prescription) response III result: $reply")
                                }
                            }
                        })
                }

            }
            4 ->{

            }
            5 ->{

            }
            6 ->{
                when(dataPivot.practitionerCode){
                    10 ->{

                    }
                    20 ->{

                    }
                    30 ->{

                    }
                    40 ->{

                    }
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


    override fun onResume() {
        super.onResume()
        if (navigationControl.currentDestination == navigationControl.graph.findNode(R.id.pivot_dialog)){
            pivotProcess(renderComplete)
        }

    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PivotDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}