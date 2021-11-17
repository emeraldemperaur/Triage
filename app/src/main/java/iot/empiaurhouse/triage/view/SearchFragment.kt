package iot.empiaurhouse.triage.view

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.SearchRecyclerAdapter
import iot.empiaurhouse.triage.databinding.FragmentSearchBinding
import iot.empiaurhouse.triage.model.Doctor
import iot.empiaurhouse.triage.model.Patient
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.viewmodel.ChironRecordsViewModel
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchFieldText: AutoCompleteTextView
    private lateinit var searchField: TextInputLayout
    private lateinit var searchView: SearchView
    private var resultsRV: RecyclerView? = null
    private var resultsRVA: SearchRecyclerAdapter? = null
    private lateinit var noResults: TextView
    private lateinit var patientRecords: ArrayList<Patient>
    private lateinit var patientVM: ChironRecordsViewModel
    private lateinit var serverTitle: TextView
    private lateinit var doctorRecords: ArrayList<Doctor>
    private lateinit var userManager: UserPreferenceManager
    private val requestSMSCode = 1
    private lateinit var numberList: ArrayList<String>



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
        userManager = UserPreferenceManager(requireContext())
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        patientVM = ViewModelProvider(this)[ChironRecordsViewModel::class.java]
        patientVM.pullChironRecords(1)
        searchFieldText = binding.searchEndpointFieldText
        searchField = binding.searchEndpointField
        searchFieldText = binding.searchEndpointFieldText
        searchView = binding.searchPatientsField
        resultsRV = binding.searchPatientsResultsRecycler
        noResults = binding.searchPatientNoResults
        serverTitle = requireActivity().findViewById(R.id.search_patient_server_title)
        fetchPatientsRecords()
        initSearchUI()
        initAPBPush()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initSearchUI(){

        val endPoints = listOf("First Name", "Last Name", "Insurer", "Insurer ID", "Blood Group", "Birth Date")
        val adapter = ArrayAdapter(requireContext(), R.layout.blood_group_item, endPoints)
        (searchField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (searchField.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, position, l ->
            val endPointSelected = adapterView.getItemAtPosition(position)
            var noResultsText = ""
            when(endPointSelected.toString()){
                "First Name" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 1)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "First Name \n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Last Name" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 2)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Last Name \n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Insurer" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 3)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Insurer \n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Insurer ID" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 4)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Insurer ID \n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Blood Group" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 5)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Blood Group \n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE

                }
                "Birth Date" ->{
                    resultsRV = null
                    resultsRVA = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 6)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Birth Date \n not found\n in Patient records"
                    initDoBSelection(searchView)
                    resultsRV!!.visibility = View.VISIBLE
                }
            }
            noResults.text = noResultsText
        }

    }

    private fun initQueryListener(inputField: SearchView, sRA: SearchRecyclerAdapter){
        inputField.setQuery("", false)
        inputField.isIconified = true
        inputField.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                sRA.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sRA.filter.filter(newText)
                return false
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initSearchUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        resultsRV?.adapter = null
        resultsRVA = null
        resultsRV = null

    }

    private fun resetInputField(inputField: SearchView){
        inputField.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        inputField.isFocusable = true
        inputField.setOnClickListener(null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initDoBSelection(inputField: SearchView){
        inputField.inputType = InputType.TYPE_NULL
        inputField.isFocusable = false
        inputField.setOnSearchClickListener {
            inputField.clearFocus()
            val cal = Calendar.getInstance()
            var y = cal.get(Calendar.YEAR)
            var m = cal.get(Calendar.MONTH)
            var d = cal.get(Calendar.DAY_OF_MONTH)
            if (!inputField.query.isNullOrBlank()){
                val inputDate = inputField.query.toString().split("-")
                y = inputDate[0].toInt()
                m = inputDate[1].toInt() - 1
                d = inputDate[2].toInt()
            }

            val datePickerDialog: DatePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val monthInt = monthOfYear + 1
                var monthStr = monthInt.toString()
                var dayInt = dayOfMonth.toString()
                if (dayOfMonth < 10){
                    dayInt = "0$dayInt"
                }
                if (monthInt < 10){
                    monthStr = "0$monthInt"
                }
                val datePicked = "$year-$monthStr-$dayInt"
                inputField.setQuery(datePicked, false)
                inputField.clearFocus()
            }, y, m, d)

            datePickerDialog.show()
        }


    }

    private fun fetchPatientsRecords(): ArrayList<Patient>{
        var result: Boolean
        val fetchedPatients = arrayListOf<Patient>()
        if (view != null) {
            patientVM.patientRecords.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { reply ->
                    reply?.let {
                        result = reply.isNotEmpty()
                        if (fetchedPatients.isEmpty()) {
                            fetchedPatients.addAll(reply)
                            patientRecords = fetchedPatients
                            println("Patient Records response object is not empty: $result")
                            println("See Chiron Records (Patient) response result: $reply")
                        }
                    }
                })
        }
        return fetchedPatients
    }


    private fun initAPBPush(){
        serverTitle.setOnLongClickListener {
            patientVM.pullChironRecords(6)
            var docResult: Boolean
            val fetchedDoctors = arrayListOf<Doctor>()
            if (view != null) {
                patientVM.doctorRecords.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { reply ->
                        reply?.let {
                            docResult = reply.isNotEmpty()
                            if (fetchedDoctors.isEmpty()) {
                                fetchedDoctors.addAll(reply)
                                doctorRecords = fetchedDoctors
                                println("Doctor Records response object is not empty: $docResult")
                                println("See Chiron Records (Doctor) response result: $reply")
                            }
                        }
                    })
                val numbersList = arrayListOf<String>()
                Handler(Looper.getMainLooper()).postDelayed({
                for (doctor in fetchedDoctors){
                    if (!doctor.contactInfo.isNullOrEmpty()){
                        numbersList.add(doctor.contactInfo)
                    }
                }
                    checkForSmsPermission(numbersList)
                }, 666)

            }
            true


        }
    }

    private fun confirmAPBPush(numbersList: ArrayList<String>){
        if (numbersList.isNotEmpty()) {
            numberList = numbersList
            val smsManager = SmsManager.getDefault()
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Sending All Points Bulletin...")
            builder.setIcon(iot.empiaurhouse.triage.R.drawable.ic_baseline_record_voice_over_24)
            builder.setMessage("${numbersList.size} recipient(s) found\n\nAre you sure you'd like " +
                    "to proceed with this push broadcast?\n\n SMS charges may apply")
            builder.setPositiveButton("YES") { _, _ ->
                for (number in numbersList){
                    Handler(Looper.getMainLooper()).postDelayed({
                        println("Sending APB to $number...")
                        smsManager.sendTextMessage(number, null, "APB -\n\t" +
                                "This is an All Points Bulletin push broadcast from ${userManager.getChironID()}", null, null)
                    }, 1000)
                }

            }

            builder.setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        }
    }


    private fun checkForSmsPermission(numbersList: ArrayList<String>) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "not_granted")
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.SEND_SMS),
                requestSMSCode
            )
        } else {
            // Permission already granted. Enable the SMS button.
            confirmAPBPush(numbersList)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        // For each permission, checks if it is granted or not.
        when (requestCode) {
            requestSMSCode -> {
                if (permissions[0].equals(
                        Manifest.permission.SEND_SMS,
                        ignoreCase = true
                    )
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission was granted. Enable the button.
                    confirmAPBPush(numberList)
                } else {
                    Log.d(TAG, "SMS SEND Permission Denied")
                    Toast.makeText(
                        requireContext(), "APB disabled. SMS Permission denied",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}