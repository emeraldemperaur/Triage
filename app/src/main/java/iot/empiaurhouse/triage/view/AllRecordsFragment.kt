package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.MultiRecordController
import iot.empiaurhouse.triage.databinding.FragmentAllRecordsBinding
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.viewmodel.ChironRecordsViewModel


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class AllRecordsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAllRecordsBinding
    private lateinit var recordsViewModel: ChironRecordsViewModel
    private lateinit var recordCount: TextView
    private lateinit var recordTitle: TextView
    private lateinit var noRecordsFound: TextView
    private lateinit var recordsRV: RecyclerView
    private lateinit var recordsController: MultiRecordController
    private var patientsFound = arrayListOf<Patient>()
    private var diagnosesFound = arrayListOf<Diagnosis>()
    private var prescriptionsFound = arrayListOf<Prescription>()
    private var visitsFound = arrayListOf<Visit>()
    private var practitionersFound = arrayListOf<Practitioner>()
    private var doctorsFound = arrayListOf<Doctor>()
    private var nursePractitionersFound = arrayListOf<NursePractitioner>()
    private var registeredNursesFound = arrayListOf<RegisteredNurse>()
    private var pharmaceuticalsFound = arrayListOf<Pharmaceuticals>()
    private var recordsID: Int = 1
    private val args: AllRecordsFragmentArgs by navArgs()




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
        return inflater.inflate(R.layout.fragment_all_records, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllRecordsBinding.bind(view)
        recordsViewModel = ViewModelProvider(this).get(ChironRecordsViewModel::class.java)
        recordsController = MultiRecordController()
        recordsViewModel.pullChironRecords()
        recordsID = args.recordID
        recordCount = binding.chironRecordsSize
        recordTitle = binding.chironRecordsTitle
        noRecordsFound = binding.noChironRecordsFound
        recordsRV = binding.chironRecordsViewRecyclerview
        initRecordsView()

    }




    fun initRecordsView(){
        recordTitle.text = recordsController.fetchRecordName(recordsID)
        recordCount.text = 0.toString()
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }


    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()

    }

    private fun checkRecordsView(){
        noRecordsFound.visibility = View.VISIBLE
        if (patientsFound.isNotEmpty() || diagnosesFound.isNotEmpty() || prescriptionsFound.isNotEmpty()
            || visitsFound.isNotEmpty() || practitionersFound.isNotEmpty() || doctorsFound.isNotEmpty() || nursePractitionersFound.isNotEmpty()
            || registeredNursesFound.isNotEmpty() || pharmaceuticalsFound.isNotEmpty()){
            noRecordsFound.visibility = View.GONE
        }
    }

    private fun recordsCarousel(recordID: Int) {
        //setup list RV and adapter based on recordID
        when(recordID){
            1 -> {
                fetchPatientsList()
            }
            2 -> {
                fetchDiagnosesList()
            }
            3 -> {
                fetchPrescriptionList()
            }
            4 -> {
                fetchVisitList()
            }
            5 -> {
                fetchPractitionersList()
            }
            6 -> {
                fetchDoctorsList()
            }
            7 -> {
                fetchNursePractitionersList()
            }
            8 -> {
                fetchRegisteredNursesList()
            }
            9 -> {
                fetchPharmaceuticalsList()
            }

            }

    }


    private fun fetchPatientsList(): ArrayList<Patient>{
        var result: Boolean
        val fetchedPatients = arrayListOf<Patient>()
        recordsViewModel.patientRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedPatients.isEmpty()) {
                    fetchedPatients.addAll(reply)
                    patientsFound = fetchedPatients
                    println("Patient Records response object is not empty: $result")
                    println("See Chiron Records (Patient) response result: $reply")
                }
            }
        })
        return fetchedPatients
    }

    private fun fetchDiagnosesList(): ArrayList<Diagnosis>{
        var result: Boolean
        val fetchedDiagnoses = arrayListOf<Diagnosis>()
        recordsViewModel.diagnosisRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedDiagnoses.isEmpty()) {
                    fetchedDiagnoses.addAll(reply)
                    diagnosesFound = fetchedDiagnoses
                    println("Diagnosis Records response object is not empty: $result")
                    println("See Chiron Records (Diagnoses) response result: $reply")
                }
            }
        })
        return fetchedDiagnoses
    }

    private fun fetchPrescriptionList(): ArrayList<Prescription>{
        var result: Boolean
        val fetchedPrescriptions = arrayListOf<Prescription>()
        recordsViewModel.prescriptionRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedPrescriptions.isEmpty()) {
                    fetchedPrescriptions.addAll(reply)
                    prescriptionsFound = fetchedPrescriptions
                    println("Prescription Records response object is not empty: $result")
                    println("See Chiron Records (Diagnoses) response result: $reply")
                }
            }
        })
        return fetchedPrescriptions
    }


    private fun fetchVisitList(): ArrayList<Visit>{
        var result: Boolean
        val fetchedVisits = arrayListOf<Visit>()
        recordsViewModel.visitRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedVisits.isEmpty()) {
                    fetchedVisits.addAll(reply)
                    visitsFound = fetchedVisits
                    println("Visit Records response object is not empty: $result")
                    println("See Chiron Records (Visits) response result: $reply")
                }
            }
        })
        return fetchedVisits
    }

    private fun fetchPractitionersList(): ArrayList<Practitioner>{
        var result: Boolean
        val fetchedPractitioners = arrayListOf<Practitioner>()
        recordsViewModel.practitionerRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedPractitioners.isEmpty()) {
                    fetchedPractitioners.addAll(reply)
                    practitionersFound = fetchedPractitioners
                    println("Practitioner Records response object is not empty: $result")
                    println("See Chiron Records (Practitioner) response result: $reply")
                }
            }
        })
        return fetchedPractitioners
    }

    private fun fetchDoctorsList(): ArrayList<Doctor>{
        var result: Boolean
        val fetchedDoctors = arrayListOf<Doctor>()
        recordsViewModel.doctorRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedDoctors.isEmpty()) {
                    fetchedDoctors.addAll(reply)
                    doctorsFound = fetchedDoctors
                    println("Practitioner Records response object is not empty: $result")
                    println("See Chiron Records (Practitioner) response result: $reply")
                }
            }
        })
        return fetchedDoctors
    }


    private fun fetchNursePractitionersList(): ArrayList<NursePractitioner>{
        var result: Boolean
        val fetchedNursePractitioners = arrayListOf<NursePractitioner>()
        recordsViewModel.nursePractitionerRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedNursePractitioners.isEmpty()) {
                    fetchedNursePractitioners.addAll(reply)
                    nursePractitionersFound = fetchedNursePractitioners
                    println("Practitioner Records response object is not empty: $result")
                    println("See Chiron Records (Practitioner) response result: $reply")
                }
            }
        })
        return fetchedNursePractitioners
    }


    private fun fetchRegisteredNursesList(): ArrayList<RegisteredNurse>{
        var result: Boolean
        val fetchedRegisteredNurses = arrayListOf<RegisteredNurse>()
        recordsViewModel.registeredNurseRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedRegisteredNurses.isEmpty()) {
                    fetchedRegisteredNurses.addAll(reply)
                    registeredNursesFound = fetchedRegisteredNurses
                    println("Practitioner Records response object is not empty: $result")
                    println("See Chiron Records (Practitioner) response result: $reply")
                }
            }
        })
        return fetchedRegisteredNurses
    }

    private fun fetchPharmaceuticalsList(): ArrayList<Pharmaceuticals>{
        var result: Boolean
        val fetchedPharmaceuticals = arrayListOf<Pharmaceuticals>()
        recordsViewModel.pharmaceuticalRecords.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedPharmaceuticals.isEmpty()) {
                    fetchedPharmaceuticals.addAll(reply)
                    pharmaceuticalsFound = fetchedPharmaceuticals
                    println("Practitioner Records response object is not empty: $result")
                    println("See Chiron Records (Practitioner) response result: $reply")
                }
            }
        })
        return fetchedPharmaceuticals
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllRecordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}