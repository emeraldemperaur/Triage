package iot.empiaurhouse.triage.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.DiagnosesPagerAdapter
import iot.empiaurhouse.triage.databinding.FragmentPatientDiagnosesBinding
import iot.empiaurhouse.triage.model.Diagnosis
import iot.empiaurhouse.triage.model.Prescription
import iot.empiaurhouse.triage.model.Visit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PatientDiagnosesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPatientDiagnosesBinding
    private lateinit var exitButton: FloatingActionButton
    private lateinit var patientName: TextView
    private lateinit var diagnosesLevel: TextView
    private lateinit var diagnosesDetails: TextView
    private lateinit var diagnosesSynopsis: TextView
    private lateinit var navControls: NavController
    private var diagnosis: Diagnosis? = null
    private lateinit var diagnosisTabs: TabLayout
    private lateinit var searchButton: FloatingActionButton
    private var prescriptions: ArrayList<Prescription>? = null
    private var visits: ArrayList<Visit>? = null
    private lateinit  var diagnosesPagerAdapter: DiagnosesPagerAdapter
    private lateinit var diagnosesViewer: ViewPager2

    private val args: PatientDiagnosesFragmentArgs by navArgs()



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
        return inflater.inflate(R.layout.fragment_patient_diagnoses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPatientDiagnosesBinding.bind(view)
        navControls = view.findNavController()
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        patientName = binding.patientDiagnosesPatient
        diagnosesSynopsis = binding.patientDiagnosesSynopsis
        diagnosesDetails = binding.patientDiagnosesDetailsText
        diagnosesLevel = binding.patientDiagnosesLevel
        diagnosisTabs = binding.patientDiagnosesTabs
        diagnosesViewer = binding.patientDiagnosesViewPager
        diagnosis = args.diagnosis
        prescriptions = args.diagnosis.prescriptions
        visits = args.diagnosis.visits
        exitButton = binding.returnToPatientRecord
        initExitButton()
        initPDView(diagnosis!!)
    }

    private fun initPDView(diagnosis: Diagnosis){
        searchButton.visibility = View.INVISIBLE
        patientName.text = diagnosis.patientFullName
        diagnosesSynopsis.text = diagnosis.diagnosisSynopsis
        diagnosesLevel.setTextColor(Color.parseColor(diagnosis.diagnosisLevel.diagnosisLevelHexCode))
        diagnosesDetails.text = detailsHolder(diagnosis.diagnosisDetails)
        diagnosesViewer = binding.patientDiagnosesViewPager
        diagnosisTabs = binding.patientDiagnosesTabs
        diagnosesPagerAdapter = DiagnosesPagerAdapter(requireActivity(), prescriptions!!, visits!!)
        diagnosesPagerAdapter.notifyDataSetChanged()
        diagnosesViewer.adapter = diagnosesPagerAdapter
        TabLayoutMediator(diagnosisTabs, diagnosesViewer) { tab, position ->
            if (position == 0){
                tab.text = "PRESCRIPTIONS"
            }
            else if (position == 1){
                tab.text = "VISITS"
            }

        }.attach()
    }


    override fun onResume() {
        super.onResume()
        diagnosis = args.diagnosis
        prescriptions = args.diagnosis.prescriptions
        visits = args.diagnosis.visits
        searchButton.visibility = View.INVISIBLE

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        diagnosis = args.diagnosis
        if (diagnosis != null) {
            prescriptions = args.diagnosis.prescriptions
            visits = args.diagnosis.visits
        }
    }


    private fun initExitButton(){
        exitButton.setOnClickListener {
            searchButton.visibility = View.VISIBLE
            navControls.navigateUp()

        }
    }


    private fun detailsHolder(string: String?): String{
        var strObject = string.toString()
        if (strObject.trim().isEmpty() || strObject == "null"){
            strObject = "No Diagnosis Details Found"
        }

        return strObject
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientDiagnosesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}