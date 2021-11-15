package iot.empiaurhouse.triage.view

import android.app.SearchManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentSearchBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchFieldText: AutoCompleteTextView
    private lateinit var searchField: TextInputLayout
    private lateinit var searchView: SearchView
    private lateinit var resultsRV: RecyclerView
    private lateinit var searchManager: SearchManager


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        searchFieldText = binding.searchEndpointFieldText
        searchField = binding.searchEndpointField
        searchFieldText = binding.searchEndpointFieldText
        searchView = binding.searchPatientsField
        resultsRV = binding.searchPatientsResultsRecycler

    }


    fun initSearchUI(){

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