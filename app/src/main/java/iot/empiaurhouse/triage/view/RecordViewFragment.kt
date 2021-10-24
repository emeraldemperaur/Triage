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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.MultiRecordController
import iot.empiaurhouse.triage.databinding.FragmentRecordViewBinding
import iot.empiaurhouse.triage.model.*
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
    private lateinit var diagnosisRecordView: ConstraintLayout
    private lateinit var diagnosisSynopsis: TextView
    private lateinit var diagnosisLevel: TextView
    private lateinit var diagnosisDate: TextView
    private lateinit var diagnosisPatient: TextView
    private lateinit var diagnosisLevelLine: TextView
    private lateinit var diagnosisNote: TextView
    private lateinit var diagnosisTabs: TabLayout
    private lateinit var diagnosisViewPager2: ViewPager2
    private lateinit var prescriptionRecordView: ConstraintLayout
    private lateinit var prescriptionName: TextView
    private lateinit var prescriptionDate: TextView
    private lateinit var prescriptionPrescriber: TextView
    private lateinit var prescriptionPrescriberID: TextView
    private lateinit var prescriptionPatient: TextView
    private lateinit var prescriptionDosageAmount: TextView
    private lateinit var prescriptionDosageRegimen: TextView
    private lateinit var prescriptionDosageDuration: TextView
    private lateinit var prescriptionDosageType: ImageView
    private lateinit var prescriptionBatchNumber: TextView
    private lateinit var visitRecordView: ConstraintLayout
    private lateinit var visitDate: TextView
    private lateinit var visitTime: TextView
    private lateinit var visitHost: TextView
    private lateinit var visitHostID: TextView
    private lateinit var visitPatient: TextView
    private lateinit var visitLogText: TextView
    private lateinit var practitionerRecordView: ConstraintLayout
    private lateinit var practitionerFirstName: TextView
    private lateinit var practitionerLastName: TextView
    private lateinit var practitionerSpeciality: TextView
    private lateinit var practitionerPractitionerID: TextView
    private lateinit var practitionerPractitionerPhone: TextView
    private lateinit var practitionerPractitionerEmail: TextView
    private lateinit var practitionerPractitionerPhoneButton: MaterialButton
    private lateinit var practitionerPractitionerEmailButton: MaterialButton
    private lateinit var practitionerProfile: ImageView
    private lateinit var doctorRecordView: ConstraintLayout
    private lateinit var doctorFirstName: TextView
    private lateinit var doctorLastName: TextView
    private lateinit var doctorSpeciality: TextView
    private lateinit var doctorPractitionerID: TextView
    private lateinit var doctorPractitionerPhone: TextView
    private lateinit var doctorPractitionerEmail: TextView
    private lateinit var doctorPractitionerPhoneButton: MaterialButton
    private lateinit var doctorPractitionerEmailButton: MaterialButton
    private lateinit var doctorProfile: ImageView
    private lateinit var rNRecordView: ConstraintLayout
    private lateinit var rNFirstName: TextView
    private lateinit var rNLastName: TextView
    private lateinit var rNSpeciality: TextView
    private lateinit var rNPractitionerID: TextView
    private lateinit var rNPractitionerPhone: TextView
    private lateinit var rNPractitionerEmail: TextView
    private lateinit var rNPractitionerPhoneButton: MaterialButton
    private lateinit var rNPractitionerEmailButton: MaterialButton
    private lateinit var rNProfile: ImageView
    private lateinit var nPRecordView: ConstraintLayout
    private lateinit var nPFirstName: TextView
    private lateinit var nPLastName: TextView
    private lateinit var nPSpeciality: TextView
    private lateinit var nPPractitionerID: TextView
    private lateinit var nPPractitionerPhone: TextView
    private lateinit var nPPractitionerEmail: TextView
    private lateinit var nPPractitionerPhoneButton: MaterialButton
    private lateinit var nPPractitionerEmailButton: MaterialButton
    private lateinit var nPProfile: ImageView
    private lateinit var pharmaceuticalRecordView: ConstraintLayout
    private lateinit var pharmaceuticalRxName: TextView
    private lateinit var pharmaceuticalManufacturer: TextView
    private lateinit var pharmaceuticalExpiryDate: TextView
    private lateinit var pharmaceuticalMakeDate: TextView
    private lateinit var pharmaceuticalGenericName: TextView
    private lateinit var pharmaceuticalChemicalName: TextView
    private lateinit var pharmaceuticalLotID: TextView
    private lateinit var pharmaceuticalApprovalID: TextView
    private lateinit var pharmaceuticalCount: TextView
    private lateinit var pharmaceuticalButton: MaterialButton
    private lateinit var userName: TextView
    private var recordID: Int? = null
    private lateinit var patient: Patient
    private lateinit var diagnosis: Diagnosis
    private lateinit var prescription: Prescription
    private lateinit var visit: Visit
    private lateinit var practitioner: Practitioner
    private lateinit var doctor: Doctor
    private lateinit var registeredNurse: RegisteredNurse
    private lateinit var nursePractitioner: NursePractitioner
    private lateinit var pharmaceuticals: Pharmaceuticals
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
        searchButton.visibility = View.GONE
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
        //diagnosisRecordView
        diagnosisRecordView = binding.diagnosisRecordsViewInclude.diagnosisDetailView
        diagnosisSynopsis = binding.diagnosisRecordsViewInclude.diagnosisSynopsisTitleText
        diagnosisLevel = binding.diagnosisRecordsViewInclude.diagnosisLevelText
        diagnosisDate = binding.diagnosisRecordsViewInclude.diagnosisDateText
        diagnosisPatient = binding.diagnosisRecordsViewInclude.diagnosisPatientText
        diagnosisLevelLine = binding.diagnosisRecordsViewInclude.diagnosesDiagnosesLevel
        diagnosisNote = binding.diagnosisRecordsViewInclude.diagnosesDetailNote
        diagnosisTabs = binding.diagnosisRecordsViewInclude.diagnosisDiagnosesTabs
        diagnosisViewPager2 = binding.diagnosisRecordsViewInclude.diagnosisDiagnosesViewPager
        //prescriptionRecordView
        prescriptionRecordView = binding.prescriptionRecordsViewInclude.prescriptionDetailView
        prescriptionName = binding.prescriptionRecordsViewInclude.prescriptionDetailNameText
        prescriptionDate = binding.prescriptionRecordsViewInclude.prescriptionDetailDateText
        prescriptionPrescriber = binding.prescriptionRecordsViewInclude.prescriptionDetailPrescriberText
        prescriptionPrescriberID = binding.prescriptionRecordsViewInclude.prescriptionDetailPrescriberId
        prescriptionPatient = binding.prescriptionRecordsViewInclude.prescriptionDetailPatientText
        prescriptionDosageAmount = binding.prescriptionRecordsViewInclude.prescriptionDetailDosageAmountText
        prescriptionDosageRegimen = binding.prescriptionRecordsViewInclude.prescriptionDetailRegimenText
        prescriptionDosageDuration = binding.prescriptionRecordsViewInclude.prescriptionDetailDurationText
        prescriptionDosageType = binding.prescriptionRecordsViewInclude.prescriptionDetailDosageType
        prescriptionBatchNumber = binding.prescriptionRecordsViewInclude.prescriptionDetailBatchNumber
        //visitRecordView
        visitRecordView = binding.visitRecordsViewInclude.visitDetailView
        visitDate = binding.visitRecordsViewInclude.visitDetailVisitDateText
        visitTime = binding.visitRecordsViewInclude.visitDetailTimeText
        visitHost = binding.visitRecordsViewInclude.visitDetailHostText
        visitHostID = binding.visitRecordsViewInclude.visitDetailHostId
        visitPatient = binding.visitRecordsViewInclude.visitDetailPatientText
        visitLogText = binding.visitRecordsViewInclude.visitDetailLogContext
        //practitionerRecordView
        practitionerRecordView = binding.practitionerRecordsViewInclude.practitionerDetailView
        practitionerFirstName = binding.practitionerRecordsViewInclude.practitionerDetailFirstNameText
        practitionerLastName = binding.practitionerRecordsViewInclude.practitionerDetailLastNameText
        practitionerSpeciality = binding.practitionerRecordsViewInclude.practitionerDetailSpeciality
        practitionerPractitionerID = binding.practitionerRecordsViewInclude.practitionerDetailPractitionerIdText
        practitionerPractitionerPhone = binding.practitionerRecordsViewInclude.practitionerDetailContactPhoneText
        practitionerPractitionerEmail = binding.practitionerRecordsViewInclude.practitionerDetailContactEmailText
        practitionerPractitionerPhoneButton = binding.practitionerRecordsViewInclude.practitionerDetailContactPhoneButton
        practitionerPractitionerEmailButton = binding.practitionerRecordsViewInclude.practitionerDetailContactEmailButton
        practitionerProfile = binding.practitionerRecordsViewInclude.practitionerDetailProfile
        //doctorRecordView
        doctorRecordView = practitionerRecordView
        doctorFirstName = practitionerFirstName
        doctorLastName = practitionerLastName
        doctorSpeciality = practitionerSpeciality
        doctorPractitionerID = practitionerPractitionerID
        doctorPractitionerPhone = practitionerPractitionerPhone
        doctorPractitionerEmail = practitionerPractitionerEmail
        doctorPractitionerPhoneButton = practitionerPractitionerPhoneButton
        doctorPractitionerEmailButton = practitionerPractitionerEmailButton
        doctorProfile = practitionerProfile
        //registeredNurseRecordView
        rNRecordView = practitionerRecordView
        rNFirstName = practitionerFirstName
        rNLastName = practitionerLastName
        rNSpeciality = practitionerSpeciality
        rNPractitionerID = practitionerPractitionerID
        rNPractitionerPhone = practitionerPractitionerPhone
        rNPractitionerEmail = practitionerPractitionerEmail
        rNPractitionerPhoneButton = practitionerPractitionerPhoneButton
        rNPractitionerEmailButton = practitionerPractitionerEmailButton
        rNProfile = practitionerProfile
        //nursePractitionerRecordView
        nPRecordView = practitionerRecordView
        nPFirstName = practitionerFirstName
        nPLastName = practitionerLastName
        nPSpeciality = practitionerSpeciality
        nPPractitionerID = practitionerPractitionerID
        nPPractitionerPhone = practitionerPractitionerPhone
        nPPractitionerEmail = practitionerPractitionerEmail
        nPPractitionerPhoneButton = practitionerPractitionerPhoneButton
        nPPractitionerEmailButton = practitionerPractitionerEmailButton
        nPProfile = practitionerProfile
        //pharmaceuticalRecordView
        pharmaceuticalRecordView = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailView
        pharmaceuticalRxName = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailBrandNameText
        pharmaceuticalManufacturer = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailMakerText
        pharmaceuticalMakeDate = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailMakeDate
        pharmaceuticalExpiryDate = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailExpiryText
        pharmaceuticalGenericName = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailGenericNameText
        pharmaceuticalChemicalName = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailChemicalNameText
        pharmaceuticalLotID = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailLotNumberIconText
        pharmaceuticalApprovalID = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailApprovalNumberText
        pharmaceuticalCount = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailInventoryCount
        pharmaceuticalButton = binding.pharmaceuticalRecordsViewInclude.pharmaceuticalDetailRestockButton

        recordID = args.recordID
        if (args.patient != null){
            patient = args.patient!!
        }
        if (args.diagnosis != null){
            diagnosis = args.diagnosis!!
        }
        if (args.prescription != null){
            prescription = args.prescription!!
        }
        if (args.visit != null){
            visit = args.visit!!
        }
        if (args.practitioner != null){
            practitioner = args.practitioner!!
        }
        if (args.doctor != null){
            doctor = args.doctor!!
        }
        if (args.registeredNurse != null){
            registeredNurse = args.registeredNurse!!
        }
        if (args.nursePractitioner != null){
            nursePractitioner = args.nursePractitioner!!
        }
        if (args.pharmaceutical != null){
            pharmaceuticals = args.pharmaceutical!!
        }
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
                    recordController.initDiagnosisRecordView(requireActivity(), diagnosis,
                        diagnosisRecordView, diagnosisSynopsis, diagnosisDate, diagnosisLevel,
                        diagnosisPatient, diagnosisLevelLine, diagnosisNote, diagnosisTabs, diagnosisViewPager2)

                }
                3 ->{
                    recordController.initPrescriptionRecordView(requireActivity(), prescription,
                        prescriptionRecordView, prescriptionName, prescriptionDate, prescriptionPrescriber
                        , prescriptionPrescriberID, prescriptionPatient,prescriptionDosageAmount,
                        prescriptionDosageRegimen, prescriptionDosageDuration, prescriptionDosageType,
                        prescriptionBatchNumber)

                }
                4 ->{
                    recordController.initVisitRecordView(visit, visitRecordView, visitDate, visitTime,
                        visitHost, visitHostID, visitPatient, visitLogText)

                }
                5 ->{
                    recordController.initPractitionerRecordView(requireActivity(), practitioner,
                        practitionerRecordView, practitionerFirstName, practitionerLastName,
                        practitionerSpeciality,practitionerPractitionerID, practitionerPractitionerPhone,
                        practitionerPractitionerEmail, practitionerPractitionerPhoneButton,
                        practitionerPractitionerEmailButton, practitionerProfile)

                }
                6 ->{
                    recordController.initDoctorRecordView(requireActivity(), doctor, doctorRecordView,
                        doctorFirstName, doctorLastName, doctorSpeciality, doctorPractitionerID,
                        doctorPractitionerPhone,doctorPractitionerEmail,doctorPractitionerPhoneButton,
                        doctorPractitionerEmailButton, doctorProfile)

                }
                7 ->{
                    recordController.initRegisteredNurseRecordView(requireActivity(), registeredNurse,
                        rNRecordView, rNFirstName, rNLastName, rNSpeciality,rNPractitionerID,
                        rNPractitionerPhone, rNPractitionerEmail, rNPractitionerPhoneButton,
                        rNPractitionerEmailButton,rNProfile)

                }
                8 ->{
                    recordController.initNursePractitionerRecordView(requireActivity(), nursePractitioner,
                        nPRecordView, nPFirstName, nPLastName, nPSpeciality, nPPractitionerID,
                        nPPractitionerPhone, nPPractitionerEmail, nPPractitionerPhoneButton,
                        nPPractitionerEmailButton, nPProfile)

                }
                9 ->{
                    recordController.initPharmaceuticalRecordView(requireActivity(), pharmaceuticals,
                        pharmaceuticalRecordView, pharmaceuticalRxName, pharmaceuticalManufacturer,
                        pharmaceuticalMakeDate, pharmaceuticalExpiryDate, pharmaceuticalGenericName,
                        pharmaceuticalChemicalName, pharmaceuticalLotID, pharmaceuticalApprovalID,
                        pharmaceuticalCount, pharmaceuticalButton)
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
        searchButton.visibility = View.GONE

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