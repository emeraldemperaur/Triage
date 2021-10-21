package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.DiagnosesVisitAdapter
import iot.empiaurhouse.triage.databinding.FragmentPatientDiagnosesVisitBinding
import iot.empiaurhouse.triage.model.Visit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PatientDiagnosesVisitFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPatientDiagnosesVisitBinding
    private var visitRV: RecyclerView? = null
    private var visitRVA: DiagnosesVisitAdapter? = null
    private lateinit var noVisitLogs: TextView
    private var visits: ArrayList<Visit>? = null
    private var args: Bundle? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            visits = it.getParcelableArrayList("visits")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patient_diagnoses_visit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPatientDiagnosesVisitBinding.bind(view)
        args = arguments
        if (args != null){
           visits = args!!.getParcelableArrayList<Visit>("visits") as ArrayList<Visit>
            println("Diagnoses Visit Found: $visits")

        }
        visitRV = binding.patientDiagnosesVisitRv
        noVisitLogs = binding.patientDiagnosesNoVisits
        initVisitsUI(visits!!)

    }


    private fun initVisitsUI(visits: ArrayList<Visit>){
        Handler(Looper.getMainLooper()).postDelayed({
        if (visits.size < 1){
            noVisitLogs.visibility = View.VISIBLE
            visitRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            visitRVA = DiagnosesVisitAdapter(visits)
            visitRV!!.adapter = visitRVA

        }
        else if (visits.size > 0){
            visitRV?.adapter = null
            visitRVA = null
            visitRV = null
            noVisitLogs.visibility = View.GONE
            visitRV = binding.patientDiagnosesVisitRv
            visitRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            visitRVA = DiagnosesVisitAdapter(visits)
            visitRV!!.adapter = visitRVA
        }
        }, 1000)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (args != null){
            visits = args!!.getParcelableArrayList<Visit>("visits") as ArrayList<Visit>
            println("Diagnoses Visit Found: $visits")

        }
        initVisitsUI(visits!!)


    }

    override fun onResume() {
        super.onResume()
            println("Diagnoses Visit Found: $visits")
            initVisitsUI(visits!!)

        }






    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, visits: ArrayList<Visit>) =
            PatientDiagnosesVisitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelableArrayList("visits", visits)
                }
            }
    }
}