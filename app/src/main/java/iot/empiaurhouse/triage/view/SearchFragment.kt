package iot.empiaurhouse.triage.view

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.SearchRecyclerAdapter
import iot.empiaurhouse.triage.databinding.FragmentSearchBinding
import iot.empiaurhouse.triage.model.Patient
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
        fetchPatientsRecords()
        initSearchUI()

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
                    noResultsText = "First Name '${searchView.query}'\n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Last Name" ->{
                    resultsRV = null
                    resultsRVA = null
                    //resultsRV!!.adapter = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 2)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Last Name '${searchView.query}'\n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Insurer" ->{
                    resultsRV = null
                    resultsRVA = null
                    //resultsRV!!.adapter = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 3)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Insurer '${searchView.query}'\n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Insurer ID" ->{
                    resultsRV = null
                    resultsRVA = null
                    //resultsRV!!.adapter = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 4)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Insurer ID '${searchView.query}'\n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE
                }
                "Blood Group" ->{
                    resultsRV = null
                    resultsRVA = null
                    //resultsRV!!.adapter = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 5)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Blood Group '${searchView.query}'\n not found\n in Patient records"
                    resultsRV!!.visibility = View.VISIBLE

                }
                "Birth Date" ->{
                    resultsRV = null
                    resultsRVA = null
                    //resultsRV!!.adapter = null
                    resultsRV = binding.searchPatientsResultsRecycler
                    resultsRV!!.visibility = View.GONE
                    fetchPatientsRecords()
                    resultsRVA = SearchRecyclerAdapter(patientRecords, noResults, 6)
                    resultsRV!!.adapter = resultsRVA
                    resultsRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    resetInputField(searchView)
                    initQueryListener(searchView, resultsRVA!!)
                    noResultsText = "Birth Date '${searchView.query}'\n not found\n in Patient records"
                    initDoBSelection(searchView)
                    resultsRV!!.visibility = View.VISIBLE
                }
            }
            noResults.text = noResultsText
        }

    }

    private fun initQueryListener(inputField: SearchView, sRA: SearchRecyclerAdapter){
        inputField.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
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