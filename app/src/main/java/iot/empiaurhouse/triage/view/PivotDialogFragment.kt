package iot.empiaurhouse.triage.view

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
    private var diagnosesFound = arrayListOf<Diagnosis>()
    private var prescriptionsFound = arrayListOf<Prescription>()
    private var visitsFound = arrayListOf<Visit>()
    private var practitionersFound = arrayListOf<Practitioner>()
    private var doctorsFound = arrayListOf<Doctor>()
    private var nursePractitionersFound = arrayListOf<NursePractitioner>()
    private var registeredNursesFound = arrayListOf<RegisteredNurse>()
    private var pharmaceuticalsFound = arrayListOf<Pharmaceuticals>()




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
                    val diagnosesPivots = Diagnoses()
                    diagnosesPivots.addAll(diagnosesFound)
                    val prescriptionPivots = Prescriptions()
                    prescriptionPivots.addAll(prescriptionsFound)
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