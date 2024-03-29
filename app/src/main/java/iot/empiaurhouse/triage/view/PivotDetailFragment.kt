package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.ChironRecordsRecyclerAdapter
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.databinding.FragmentPivotDetailBinding
import iot.empiaurhouse.triage.model.*
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PivotDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPivotDetailBinding
    private lateinit var pivotLabel: TextView
    private lateinit var pivotModel: TextView
    private lateinit var pivotEndPoint: TextView
    private lateinit var pivotResultCount: TextView
    private lateinit var pivotType: TextView
    private lateinit var alphaParam: TextView
    private lateinit var betaParam: TextView
    private lateinit var epsilonParam: TextView
    private lateinit var alphaParamTitle: TextView
    private lateinit var betaParamTitle: TextView
    private lateinit var epsilonParamTitle: TextView
    private lateinit var chiParam: TextView
    private lateinit var psiParam: TextView
    private lateinit var chiParamTitle: TextView
    private lateinit var psiParamTitle: TextView
    private lateinit var pivotListView: RecyclerView
    private lateinit var exitPivot: FloatingActionButton
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var noResultsText: TextView
    private lateinit var pivotTimeStream: TextView
    private lateinit var toolBar: Toolbar
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var pivotController: PivotController
    private lateinit var navigationControls: NavController
    private val args: PivotDetailFragmentArgs by navArgs()
    private lateinit var stagedDataPivot: DataPivot
    private var cRRA: ChironRecordsRecyclerAdapter? = null
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pivot_detail, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotDetailBinding.bind(view)
        pivotLabel = binding.pivotDetailPivotLabel
        pivotModel = binding.pivotDetailModelText
        pivotEndPoint = binding.pivotDetailEndpointText
        pivotResultCount = binding.pivotDetailResultsCount
        pivotType = binding.pivotDetailPivotTypeText
        alphaParam = binding.pivotDetailPivotAlphaParametersText
        alphaParamTitle = binding.pivotDetailPivotAlphaParametersTitle
        betaParam = binding.pivotDetailPivotBetaParametersText
        betaParamTitle = binding.pivotDetailPivotBetaParametersTitle
        epsilonParam = binding.pivotDetailPivotEpsilonParametersText
        epsilonParamTitle = binding.pivotDetailPivotEpsilonParametersTitle
        pivotTimeStream = binding.pivotDetailTimeStream
        chiParam = binding.pivotDetailPivotChiParametersText
        chiParamTitle = binding.pivotDetailPivotChiParametersTitle
        psiParam = binding.pivotDetailPivotPsiParametersText
        psiParamTitle = binding.pivotDetailPivotPsiParametersTitle
        pivotListView = binding.pivotDetailPivotList
        exitPivot = binding.exitDataPivot
        noResultsText = binding.pivotDetailNoResults
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolBar = requireActivity().findViewById(R.id.hub_toolbar)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        navigationControls = view.findNavController()
        pivotController = PivotController()
        stagedDataPivot = args.dataPivot
        pivotListView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        initPivotDetailView(stagedDataPivot)
        initPivotRecordsView(stagedDataPivot, patientRecords = args.patient, diagnosesRecords = args.diagnosis,
            prescriptionRecords = args.prescription, visitRecords = args.visit,
            practitionerRecords = args.practitioner, doctorRecords = args.doctor,
            nursePractitionerRecords = args.nursePractitioner, registeredNurseRecords = args.registeredNurse,
            pharmaceuticalsRecords = args.pharmaceutical)
        onBackPressed()

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun initPivotDetailView(dataPivot: DataPivot){
        hubUserName.visibility = View.INVISIBLE
        searchButton.visibility = View.INVISIBLE
        pivotLabel.text = dataPivot.alias.capitalize(Locale.ROOT)
        pivotModel.text = pivotController.pivotObjectModelCheck(dataPivot)
        pivotEndPoint.text = pivotController.pivotObjectEndPointCheck(dataPivot)
        pivotType.text = pivotController.pivotObjectTypeCheck(dataPivot)
        val isDatePivot = pivotController.pivotObjectChronoCheck(dataPivot)
        if (isDatePivot){
            alphaParamTitle.visibility = View.GONE
            alphaParam.visibility = View.GONE
            betaParamTitle.visibility = View.GONE
            betaParam.visibility = View.GONE
            epsilonParamTitle.visibility = View.GONE
            epsilonParam.visibility = View.GONE
            pivotTimeStream.visibility = View.VISIBLE
            chiParamTitle.visibility = View.VISIBLE
            chiParam.visibility = View.VISIBLE
            psiParamTitle.visibility = View.VISIBLE
            psiParam.visibility = View.VISIBLE
            pivotTimeStream.text = pivotController.pivotObjectTimeFlowInterpreter(dataPivot)
            if (dataPivot.timeStreamCode == 4){
                chiParam.text = pivotController.pivotObjectDateFormat(dataPivot.dateParameterA)
                psiParam.text = pivotController.pivotObjectDateFormat(dataPivot.dateParameterB)
            }else {
                chiParam.text = pivotController.pivotObjectDateFormat(dataPivot.dateParameterA)
                val holderText = "Not Applicable"
                psiParam.text = holderText
            }
        }
        else if (!isDatePivot){
            pivotTimeStream.visibility = View.GONE
            chiParamTitle.visibility = View.GONE
            chiParam.visibility = View.GONE
            psiParamTitle.visibility = View.GONE
            psiParam.visibility = View.GONE
            alphaParamTitle.visibility = View.VISIBLE
            alphaParam.visibility = View.VISIBLE
            betaParamTitle.visibility = View.VISIBLE
            betaParam.visibility = View.VISIBLE
            epsilonParamTitle.visibility = View.VISIBLE
            epsilonParam.visibility = View.VISIBLE
            alphaParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterA)
            betaParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterB)
            epsilonParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterC)
        }


        exitPivot.setOnClickListener {
            hubUserName.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            toolbarView.visibility = View.VISIBLE

            val input = PivotDetailFragmentDirections.exitPivotDetail()
            navigationControls.navigate(input)
        }

    }


    private fun initPivotRecordsView(dataPivot: DataPivot, patientRecords: ArrayList<Patient>? = null,
                                     diagnosesRecords: ArrayList<Diagnosis>? = null,
                                     prescriptionRecords: ArrayList<Prescription>? = null,
                                     visitRecords: ArrayList<Visit>? = null,
                                     pharmaceuticalsRecords: ArrayList<Pharmaceuticals>? = null,
                                     practitionerRecords: ArrayList<Practitioner>? = null,
                                     doctorRecords: ArrayList<Doctor>? = null,
                                     nursePractitionerRecords: ArrayList<NursePractitioner>? = null,
                                     registeredNurseRecords: ArrayList<RegisteredNurse>? = null){
        when(dataPivot.entityCode) {
            1 -> {
                cRRA = ChironRecordsRecyclerAdapter(
                    dataPivot.entityCode, patientsList = patientRecords,
                    recordsViewObject = binding.root, activity = requireActivity()
                )
                pivotListView.adapter = cRRA
                noResultsView(patientRecords!!.size)
                pivotResultCount.text = patientRecords.size.toString()
            }
            2 -> {
                cRRA = ChironRecordsRecyclerAdapter(
                    dataPivot.entityCode, diagnosesList = diagnosesRecords,
                    recordsViewObject = binding.root, activity = requireActivity()
                )
                pivotListView.adapter = cRRA
                noResultsView(diagnosesRecords!!.size)
                pivotResultCount.text = diagnosesRecords.size.toString()

            }
            3 -> {
                cRRA = ChironRecordsRecyclerAdapter(
                    dataPivot.entityCode, prescriptionsList = prescriptionRecords,
                    recordsViewObject = binding.root, activity = requireActivity()
                )
                pivotListView.adapter = cRRA
                noResultsView(prescriptionRecords!!.size)
                pivotResultCount.text = prescriptionRecords.size.toString()

            }
            4 -> {
                cRRA = ChironRecordsRecyclerAdapter(
                    dataPivot.entityCode, visitsList = visitRecords,
                    recordsViewObject = binding.root, activity = requireActivity()
                )
                pivotListView.adapter = cRRA
                noResultsView(visitRecords!!.size)
                pivotResultCount.text = visitRecords.size.toString()
            }
            5 -> {
                cRRA = ChironRecordsRecyclerAdapter(
                    9, pharmaceuticalsList = pharmaceuticalsRecords,
                    recordsViewObject = binding.root, activity = requireActivity()
                )
                pivotListView.adapter = cRRA
                noResultsView(pharmaceuticalsRecords!!.size)
                pivotResultCount.text = pharmaceuticalsRecords.size.toString()

            }
            6 -> {
                when (dataPivot.practitionerCode) {
                    10 -> {
                        cRRA = ChironRecordsRecyclerAdapter(
                            5, practitionersList = practitionerRecords,
                            recordsViewObject = binding.root, activity = requireActivity()
                        )
                        pivotListView.adapter = cRRA
                        noResultsView(practitionerRecords!!.size)
                        pivotResultCount.text = practitionerRecords.size.toString()
                    }
                    20 -> {
                        cRRA = ChironRecordsRecyclerAdapter(
                            6, doctorsList = doctorRecords,
                            recordsViewObject = binding.root, activity = requireActivity()
                        )
                        pivotListView.adapter = cRRA
                        noResultsView(doctorRecords!!.size)
                        pivotResultCount.text = doctorRecords.size.toString()
                    }
                    30 -> {
                        cRRA = ChironRecordsRecyclerAdapter(
                            8, nursePractitionersList = nursePractitionerRecords,
                            recordsViewObject = binding.root, activity = requireActivity()
                        )
                        pivotListView.adapter = cRRA
                        noResultsView(nursePractitionerRecords!!.size)
                        pivotResultCount.text = nursePractitionerRecords.size.toString()

                    }
                    40 -> {
                        cRRA = ChironRecordsRecyclerAdapter(
                            7, registeredNursesList = registeredNurseRecords,
                            recordsViewObject = binding.root, activity = requireActivity()
                        )
                        pivotListView.adapter = cRRA
                        noResultsView(registeredNurseRecords!!.size)
                        pivotResultCount.text = registeredNurseRecords.size.toString()
                    }
                }
            }
        }
    }


    private fun noResultsView(recordsFound: Int?){
        if (recordsFound!! < 1){
            pivotListView.visibility = View.GONE
            noResultsText.visibility = View.VISIBLE
            pivotResultCount.text = 0.toString()

        }
        else if (recordsFound > 0){
            noResultsText.visibility = View.GONE
            pivotListView.visibility = View.VISIBLE
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
        toolbarView.visibility = View.VISIBLE
        hubUserName.visibility = View.INVISIBLE
        searchButton.visibility = View.INVISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PivotDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}