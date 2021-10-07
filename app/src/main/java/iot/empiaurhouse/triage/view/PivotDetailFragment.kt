package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.databinding.FragmentPivotDetailBinding
import iot.empiaurhouse.triage.model.*
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    private lateinit var chiParam: TextView
    private lateinit var psiParam: TextView
    private lateinit var pivotListView: ExpandableListView
    private lateinit var exitPivot: FloatingActionButton
    private lateinit var noResultsText: TextView
    private lateinit var pivotTimeStream: TextView
    private lateinit var noResultsImageView: ImageView
    private lateinit var pivotController: PivotController





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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotDetailBinding.bind(view)
        pivotLabel = binding.pivotDetailPivotLabel
        pivotModel = binding.pivotDetailModelText
        pivotEndPoint = binding.pivotDetailEndpointText
        pivotResultCount = binding.pivotDetailResultsCount
        pivotType = binding.pivotDetailPivotTypeText
        alphaParam = binding.pivotDetailPivotAlphaParametersText
        betaParam = binding.pivotDetailPivotBetaParametersText
        epsilonParam = binding.pivotDetailPivotEpsilonParametersText
        pivotTimeStream = binding.pivotDetailTimeStream
        chiParam = binding.pivotDetailPivotChiParametersText
        psiParam = binding.pivotDetailPivotPsiParametersText
        pivotListView = binding.pivotDetailPivotList
        exitPivot = binding.exitDataPivot
        noResultsText = binding.pivotDetailNoResults
        noResultsImageView = binding.pivotDetailNoResultsImg
        pivotController = PivotController()

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun initPivotDetailView(dataPivot: DataPivot){
        pivotLabel.text = dataPivot.alias.capitalize(Locale.ROOT)
        pivotModel.text = pivotController.pivotObjectModelCheck(dataPivot)
        pivotEndPoint.text = pivotController.pivotObjectEndPointCheck(dataPivot)
        pivotType.text = pivotController.pivotObjectTypeCheck(dataPivot)
        val isDatePivot = pivotController.pivotObjectChronoCheck(dataPivot)
        if (isDatePivot){
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
            alphaParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterA)
            betaParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterB)
            epsilonParam.text = pivotController.pivotObjectValueHolderCheck(dataPivot.valueParameterC)
        }

    }


    fun initPivotRecordsView(dataPivot: DataPivot, patientRecords: ArrayList<Patient>? = null,
                             diagnosisList: ArrayList<Diagnosis>? = null,
    prescriptionList: ArrayList<Prescription>? = null,
                             visitList: ArrayList<Visit>? = null,
                             pharmaceuticalsList: ArrayList<Pharmaceuticals>? = null,
    practitionerList: ArrayList<Practitioner>? = null,
                             doctorList: ArrayList<Doctor>? = null,
                             nursePractitionerList: ArrayList<NursePractitioner>? = null,
                             registeredNurseList: ArrayList<RegisteredNurse>? = null){

        var listSize = 0
        when(dataPivot.entityCode) {
            1 -> {
                listSize = patientRecords!!.size
                noResultsView(listSize)
                noResultsText.text = listSize.toString()
            }
            2 -> {
                listSize = diagnosisList!!.size
                noResultsView(listSize)
                noResultsText.text = listSize.toString()

            }
            3 -> {
                listSize = prescriptionList!!.size
                noResultsView(listSize)
                noResultsText.text = listSize.toString()

            }
            4 -> {
                listSize = visitList!!.size
                noResultsView(listSize)
                noResultsText.text = listSize.toString()
            }
            5 -> {
                listSize = pharmaceuticalsList!!.size
                noResultsView(listSize)
                noResultsText.text = listSize.toString()

            }
            6 -> {

                when (dataPivot.practitionerCode) {
                    10 -> {
                        listSize = practitionerList!!.size
                        noResultsView(listSize)
                        noResultsText.text = listSize.toString()
                    }
                    20 -> {
                        listSize = doctorList!!.size
                        noResultsView(listSize)
                        noResultsText.text = listSize.toString()

                    }
                    30 -> {
                        listSize = nursePractitionerList!!.size
                        noResultsView(listSize)
                        noResultsText.text = listSize.toString()

                    }
                    40 -> {
                        listSize = registeredNurseList!!.size
                        noResultsView(listSize)
                        noResultsText.text = listSize.toString()

                    }
                }

            }
        }
    }


    private fun noResultsView(recordsFound: Int){
        if (recordsFound < 1){
            pivotListView.visibility = View.GONE
            noResultsText.visibility = View.VISIBLE
            noResultsImageView.visibility = View.VISIBLE

        }
        else if (recordsFound > 0){
            noResultsText.visibility = View.GONE
            noResultsImageView.visibility = View.GONE
            pivotListView.visibility = View.VISIBLE
        }
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