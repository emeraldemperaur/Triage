package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
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
    private lateinit var pivotListView: ExpandableListView
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
        initPivotDetailView(stagedDataPivot)
        initPivotRecordsView(stagedDataPivot)
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
                                     diagnosisList: ArrayList<Diagnosis>? = null,
                                     prescriptionList: ArrayList<Prescription>? = null,
                                     visitList: ArrayList<Visit>? = null,
                                     pharmaceuticalsList: ArrayList<Pharmaceuticals>? = null,
                                     practitionerList: ArrayList<Practitioner>? = null,
                                     doctorList: ArrayList<Doctor>? = null,
                                     nursePractitionerList: ArrayList<NursePractitioner>? = null,
                                     registeredNurseList: ArrayList<RegisteredNurse>? = null){

        val listSize = 0
        when(dataPivot.entityCode) {
            1 -> {
                noResultsView(listSize)
                pivotResultCount.text = listSize.toString()
            }
            2 -> {
                noResultsView(listSize)
                pivotResultCount.text = listSize.toString()

            }
            3 -> {
                noResultsView(listSize)
                pivotResultCount.text = listSize.toString()

            }
            4 -> {
                noResultsView(listSize)
                pivotResultCount.text = listSize.toString()
            }
            5 -> {

                noResultsView(listSize)
                pivotResultCount.text = listSize.toString()

            }
            6 -> {

                when (dataPivot.practitionerCode) {
                    10 -> {

                        noResultsView(listSize)
                        pivotResultCount.text = listSize.toString()
                    }
                    20 -> {

                        noResultsView(listSize)
                        pivotResultCount.text = listSize.toString()

                    }
                    30 -> {

                        noResultsView(listSize)
                        pivotResultCount.text = listSize.toString()

                    }
                    40 -> {

                        noResultsView(listSize)
                        pivotResultCount.text = listSize.toString()

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