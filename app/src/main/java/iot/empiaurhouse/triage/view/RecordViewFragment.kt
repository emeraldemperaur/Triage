package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.MultiRecordController
import iot.empiaurhouse.triage.databinding.FragmentRecordViewBinding
import iot.empiaurhouse.triage.model.Patient
import iot.empiaurhouse.triage.viewmodel.ChironRecordsViewModel

private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""

class RecordViewFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecordViewBinding
    private lateinit var recordController: MultiRecordController
    private lateinit var navController: NavController
    private lateinit var exitRecordViewButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var searchButton: FloatingActionButton
    private lateinit var patientRecordView: ConstraintLayout
    private lateinit var patientRecordFirstName: TextView
    private lateinit var patientRecordLastName: TextView
    private lateinit var patientRecordBirthDate: TextView
    private lateinit var patientRecordInsurer: TextView
    private lateinit var patientRecordInsurerID: TextView
    private lateinit var patientRecordBloodGroup: TextView
    private lateinit var patientRecordAddress: TextView
    private lateinit var patientRecordPhone: TextView
    private lateinit var patientRecordDiagnosesTitle: TextView
    private lateinit var patientRecordNoDiagnosesFound: TextView
    private lateinit var patientRecordProfile: ImageView
    private lateinit var patientRecordDiagnosesRV: RecyclerView
    private lateinit var userName: TextView
    private var recordID: Int? = null
    private lateinit var patient: Patient
    private lateinit var recordsViewModel: ChironRecordsViewModel
    private val args: RecordViewFragmentArgs by navArgs()
    

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
        return inflater.inflate(R.layout.fragment_record_view, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecordViewBinding.bind(view)
        exitRecordViewButton = binding.exitRecordDetail
        recordsViewModel = ViewModelProvider(this).get(ChironRecordsViewModel::class.java)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        userName = requireActivity().findViewById(R.id.hub_username_title)
        navController = view.findNavController()
        //patientRecordView
        patientRecordView = binding.patientRecordsViewInclude.patientDetailView
        patientRecordFirstName = binding.patientRecordsViewInclude.patientDetailFirstNameText
        patientRecordLastName = binding.patientRecordsViewInclude.patientDetailLastNameText
        patientRecordBirthDate = binding.patientRecordsViewInclude.patientDateOfBirthText
        patientRecordInsurer = binding.patientRecordsViewInclude.patientDetailInsurerText
        patientRecordInsurerID = binding.patientRecordsViewInclude.patientDetailInsurerIdText
        patientRecordBloodGroup = binding.patientRecordsViewInclude.patientDetailBloodgroupText
        patientRecordAddress = binding.patientRecordsViewInclude.patientDetailInfoAddressText
        patientRecordPhone = binding.patientRecordsViewInclude.patientDetailPhoneText
        patientRecordDiagnosesTitle = binding.patientRecordsViewInclude.patientDetailDiagnosesTitle
        patientRecordNoDiagnosesFound = binding.patientRecordsViewInclude.noDiagnosesFound
        patientRecordDiagnosesRV = binding.patientRecordsViewInclude.dataPivotsViewRecyclerview
        patientRecordProfile = binding.patientRecordsViewInclude.patientDetailProfile
        recordID = args.recordID
        patient = args.patient
        recordController = MultiRecordController()
        initMultiRecordView(recordID!!)
        initViewExit()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initMultiRecordView(recordID: Int){
        searchButton.visibility = View.GONE
            when(recordID){
                1 ->{

                    recordController.initPatientRecordView(requireActivity(),
                        patient, patientRecordView, patientRecordFirstName, patientRecordLastName,
                        patientRecordBirthDate, patientRecordInsurer, patientRecordInsurerID, patientRecordBloodGroup, patientRecordAddress,
                        patientRecordPhone, patientRecordDiagnosesTitle, patientRecordNoDiagnosesFound, patientRecordProfile, patientRecordDiagnosesRV)

                }
                2 ->{

                }
                3 ->{

                }
                4 ->{

                }
                5 ->{

                }
                6 ->{

                }
                7 ->{

                }
                8 ->{

                }
                9 ->{

                }
            }

    }

    private fun initViewExit(){
        exitRecordViewButton.setOnClickListener {
            navController.navigateUp()
            searchButton.visibility = View.VISIBLE
            userName.visibility = View.VISIBLE
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        toolbarView.visibility = View.VISIBLE
        searchButton.visibility = View.GONE
        userName.visibility = View.VISIBLE

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecordViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}