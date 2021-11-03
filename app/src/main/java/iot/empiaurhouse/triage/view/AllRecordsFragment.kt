package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.ChironRecordsRecyclerAdapter
import iot.empiaurhouse.triage.adapter.SwipeToDeleteCallback
import iot.empiaurhouse.triage.adapter.SwipeToEditCallback
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
    private lateinit var recordsSwipeRefresh: SwipeRefreshLayout
    private lateinit var recordCount: TextView
    private lateinit var recordTitle: TextView
    private lateinit var noRecordsFound: TextView
    private lateinit var pullPrompt: TextView
    private var recordsRV: RecyclerView? = null
    private var cRRA: ChironRecordsRecyclerAdapter? = null
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
    private var recordsFound = arrayListOf<Any>()
    private lateinit var recordsView: View
    private lateinit var loadingText: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var createNewRecord: FloatingActionButton



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
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        searchButton.visibility = View.GONE
        recordsController = MultiRecordController()
        recordsID = args.recordID
        recordsViewModel.pullChironRecords(recordsID)
        recordsCarousel(recordsID)
        recordsView = binding.chironRecordsView
        recordsSwipeRefresh = binding.allRecordsSwipeRefresh
        recordsSwipeRefresh.setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.chiron_blue, null))
        recordCount = binding.chironRecordsSize
        recordTitle = binding.chironRecordsTitle
        loadingText = binding.loadingChironRecords
        pullPrompt = binding.recordsPullToRefreshText
        noRecordsFound = binding.noChironRecordsFound
        createNewRecord = binding.createChironEntityRecord
        recordsRV = binding.chironRecordsViewRecyclerview
        recordsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        createNewRecord.visibility = View.VISIBLE
        initRecordsView(recordsID)
        initCreateNewRecord(recordsID)
        initRefresh()

    }




    private fun initRecordsView(recordID: Int){
        searchButton.visibility = View.GONE
        recordTitle.text = recordsController.fetchRecordName(recordID)
        if (view != null) {
            when (recordID) {
                1 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, patientsList = patientsFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = patientsFound.size.toString()
                    initSwipeEditGesture(patientsFound.size)
                    initSwipeDeleteGesture(patientsFound.size)
                    noResultsView(patientsFound.size)
                }
                2 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, diagnosesList = diagnosesFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = diagnosesFound.size.toString()
                    createNewRecord.visibility = View.GONE
                    initSwipeDeleteGesture(diagnosesFound.size)
                    noResultsView(diagnosesFound.size)
                }
                3 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, prescriptionsList = prescriptionsFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = prescriptionsFound.size.toString()
                    createNewRecord.visibility = View.GONE
                    initSwipeDeleteGesture(prescriptionsFound.size)
                    noResultsView(prescriptionsFound.size)
                }
                4 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, visitsList = visitsFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = visitsFound.size.toString()
                    createNewRecord.visibility = View.GONE
                    initSwipeDeleteGesture(visitsFound.size)
                    noResultsView(visitsFound.size)
                }
                5 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, practitionersList = practitionersFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = practitionersFound.size.toString()
                    initSwipeEditGesture(practitionersFound.size)
                    initSwipeDeleteGesture(practitionersFound.size)
                    noResultsView(practitionersFound.size)
                }
                6 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, doctorsList = doctorsFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = doctorsFound.size.toString()
                    initSwipeEditGesture(doctorsFound.size)
                    initSwipeDeleteGesture(doctorsFound.size)
                    noResultsView(doctorsFound.size)
                }
                7 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, registeredNursesList = fetchRegisteredNursesList(),
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = fetchRegisteredNursesList().size.toString()
                    initSwipeEditGesture(registeredNursesFound.size)
                    initSwipeDeleteGesture(registeredNursesFound.size)
                    noResultsView(registeredNursesFound.size)
                }
                8 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, nursePractitionersList = fetchNursePractitionersList(),
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = fetchNursePractitionersList().size.toString()
                    initSwipeEditGesture(nursePractitionersFound.size)
                    initSwipeDeleteGesture(nursePractitionersFound.size)
                    noResultsView(nursePractitionersFound.size)
                }
                9 -> {
                    cRRA = ChironRecordsRecyclerAdapter(
                        recordsID, pharmaceuticalsList = pharmaceuticalsFound,
                        recordsViewObject = recordsView, activity = requireActivity()
                    )
                    recordsRV!!.adapter = cRRA
                    recordCount.text = pharmaceuticalsFound.size.toString()
                    initSwipeEditGesture(pharmaceuticalsFound.size)
                    initSwipeDeleteGesture(pharmaceuticalsFound.size)
                    noResultsView(pharmaceuticalsFound.size)
                }
            }

        }
    }


    private fun viewRefresh(){
        recordsRV!!.adapter = null
        cRRA = null
        recordsRV = null
        Handler(Looper.getMainLooper()).postDelayed({
            recordsCarousel(recordsID)
            recordsRV = binding.chironRecordsViewRecyclerview
            initRecordsView(recordsID)
        }, 1000)

    }


    private fun initSwipeDeleteGesture(size: Int){
        if (recordsRV != null) {
            if (view != null && size > 0) {
                val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val adapter = cRRA
                        adapter!!.deleteChironRecord(viewHolder.absoluteAdapterPosition, recordsID)
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(recordsRV)
            }
        }
    }

    private fun initSwipeEditGesture(size: Int){
        if (recordsRV != null) {
            if (view != null && size > 0) {
                val swipeHandler = object : SwipeToEditCallback(requireContext()) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val adapter = cRRA
                       // adapter!!.deleteChironRecord(viewHolder.absoluteAdapterPosition, recordsID)
                        adapter!!.editChironRecord(viewHolder.absoluteAdapterPosition, recordsID)
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(recordsRV)
            }
        }
    }


    private fun initRefresh(){
        recordsSwipeRefresh.setOnRefreshListener {
            noRecordsFound.visibility = View.INVISIBLE
            loadingText.visibility = View.VISIBLE
            viewRefresh()
            recordsSwipeRefresh.isRefreshing = false
        }
    }


    override fun onResume() {
        super.onResume()
        recordsCarousel(recordsID)
        Handler(Looper.getMainLooper()).postDelayed({
            recordsCarousel(recordsID)
            recordsRV = binding.chironRecordsViewRecyclerview
            initRecordsView(recordsID)
            recordCount.visibility = View.VISIBLE
        }, 2000)

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recordsCarousel(recordsID)
        viewRefresh()

    }


    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()

    }

    private fun initCreateNewRecord(recordID: Int){
        createNewRecord.setOnClickListener {
            val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null,
                null, null, null, null)
            val viewControl = requireView().findNavController()
            viewControl.navigate(input)

        }
    }


    private fun noResultsView(recordsFound: Int){

        Handler(Looper.getMainLooper()).postDelayed({
            loadingText.visibility = View.GONE
            if (recordsFound < 1){
                if (recordsRV != null) {
                   // recordsRV!!.visibility = View.GONE
                }
                noRecordsFound.visibility = View.VISIBLE
                pullPrompt.visibility = View.VISIBLE
            }
            else if (recordsFound > 0){
                noRecordsFound.visibility = View.GONE
                pullPrompt.visibility = View.GONE
                if (recordsRV != null) {
                    recordsRV!!.visibility = View.VISIBLE
                }
            }
        }, 3000)

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
        if (view != null) {
            recordsViewModel.patientRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedPatients.isEmpty()) {
                            fetchedPatients.addAll(reply)
                            patientsFound = fetchedPatients
                            println("Patient Records response object is not empty: $result")
                            println("See Chiron Records (Patient) response result: $reply")
                        }
                    }
                })
        }
        return fetchedPatients
    }

    private fun fetchDiagnosesList(): ArrayList<Diagnosis>{
        var result: Boolean
        val fetchedDiagnoses = arrayListOf<Diagnosis>()
        if (view != null) {
            recordsViewModel.diagnosisRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedDiagnoses.isEmpty()) {
                            fetchedDiagnoses.addAll(reply)
                            diagnosesFound = fetchedDiagnoses
                            println("Diagnosis Records response object is not empty: $result")
                            println("See Chiron Records (Diagnoses) response result: $reply")
                        }
                    }
                })
        }
        return fetchedDiagnoses
    }

    private fun fetchPrescriptionList(): ArrayList<Prescription>{
        var result: Boolean
        val fetchedPrescriptions = arrayListOf<Prescription>()
        if (view != null) {
            recordsViewModel.prescriptionRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedPrescriptions.isEmpty()) {
                            fetchedPrescriptions.addAll(reply)
                            prescriptionsFound = fetchedPrescriptions
                            println("Prescription Records response object is not empty: $result")
                            println("See Chiron Records (Diagnoses) response result: $reply")
                        }
                    }
                })
        }
        return fetchedPrescriptions
    }


    private fun fetchVisitList(): ArrayList<Visit>{
        var result: Boolean
        val fetchedVisits = arrayListOf<Visit>()
        if (view != null) {
            recordsViewModel.visitRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedVisits.isEmpty()) {
                            fetchedVisits.addAll(reply)
                            visitsFound = fetchedVisits
                            println("Visit Records response object is not empty: $result")
                            println("See Chiron Records (Visits) response result: $reply")
                        }
                    }
                })
        }
        return fetchedVisits
    }

    private fun fetchPractitionersList(): ArrayList<Practitioner>{
        var result: Boolean
        val fetchedPractitioners = arrayListOf<Practitioner>()
        if (view != null) {
            recordsViewModel.practitionerRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedPractitioners.isEmpty()) {
                            fetchedPractitioners.addAll(reply)
                            practitionersFound = fetchedPractitioners
                            println("Practitioner Records response object is not empty: $result")
                            println("See Chiron Records (Practitioner) response result: $reply")
                        }
                    }
                })
        }
        return fetchedPractitioners
    }

    private fun fetchDoctorsList(): ArrayList<Doctor>{
        var result: Boolean
        val fetchedDoctors = arrayListOf<Doctor>()
        if (view != null) {
            recordsViewModel.doctorRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedDoctors.isEmpty()) {
                            fetchedDoctors.addAll(reply)
                            doctorsFound = fetchedDoctors
                            println("Doctor Records response object is not empty: $result")
                            println("See Chiron Records (Doctor) response result: $reply")
                        }
                    }
                })
        }
        return fetchedDoctors
    }


    private fun fetchNursePractitionersList(): ArrayList<NursePractitioner>{
        var result: Boolean
        val fetchedNursePractitioners = arrayListOf<NursePractitioner>()
        if (view != null) {
            recordsViewModel.nursePractitionerRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedNursePractitioners.isEmpty()) {
                            fetchedNursePractitioners.addAll(reply)
                            nursePractitionersFound = fetchedNursePractitioners
                            println("Nurse Practitioner Records response object is not empty: $result")
                            println("See Chiron Records (Nurse Practitioner) response result: $reply")
                        }
                    }
                })
        }
        return fetchedNursePractitioners
    }


    private fun fetchRegisteredNursesList(): ArrayList<RegisteredNurse>{
        var result: Boolean
        val fetchedRegisteredNurses = arrayListOf<RegisteredNurse>()
        if (view != null) {
            recordsViewModel.registeredNurseRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedRegisteredNurses.isEmpty()) {
                            fetchedRegisteredNurses.addAll(reply)
                            registeredNursesFound = fetchedRegisteredNurses
                            println("Registered Nurse Records response object is not empty: $result")
                            println("See Chiron Records (Registered Nurse) response result: $reply")
                        }
                    }
                })
        }
        return fetchedRegisteredNurses
    }

    private fun fetchPharmaceuticalsList(): ArrayList<Pharmaceuticals>{
        var result: Boolean
        val fetchedPharmaceuticals = arrayListOf<Pharmaceuticals>()
        if (view != null) {
            recordsViewModel.pharmaceuticalRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedPharmaceuticals.isEmpty()) {
                            fetchedPharmaceuticals.addAll(reply)
                            pharmaceuticalsFound = fetchedPharmaceuticals
                            println("Pharmaceuticals Records response object is not empty: $result")
                            println("See Chiron Records (Pharmaceuticals) response result: $reply")
                        }
                    }
                })
        }
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