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
import iot.empiaurhouse.triage.adapter.DiagnosesPrescriptionAdapter
import iot.empiaurhouse.triage.databinding.FragmentPatientDiagnosesRxBinding
import iot.empiaurhouse.triage.model.Prescription

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PatientDiagnosesRxFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPatientDiagnosesRxBinding
    private var rxRV: RecyclerView? = null
    private  var rxRVA: DiagnosesPrescriptionAdapter? = null
    private lateinit var noPrescriptions: TextView
    private var prescriptions: ArrayList<Prescription>? = null
    private var args: Bundle? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            prescriptions = it.getParcelableArrayList("prescriptions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patient_diagnoses_rx, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPatientDiagnosesRxBinding.bind(view)
        if (args != null){
            prescriptions = args!!.getParcelableArrayList<Prescription>("prescriptions") as ArrayList<Prescription>
            println("Diagnoses Prescription Found: $prescriptions")

        }
        println("Diagnoses Prescription Found: $prescriptions")
        rxRV = binding.patientDiagnosesRxRv
        rxRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rxRVA = DiagnosesPrescriptionAdapter(prescriptions!!)
        rxRV!!.adapter = rxRVA
        noPrescriptions = binding.patientDiagnosesNoPrescriptions
        initPrescriptionsUI(prescriptions!!)

    }


    private fun initPrescriptionsUI(prescriptions: ArrayList<Prescription>){
        Handler(Looper.getMainLooper()).postDelayed({

            if (prescriptions.size < 1){
                noPrescriptions.visibility = View.VISIBLE
            }
            else if (prescriptions.size > 0){
                rxRV?.adapter = null
                rxRVA = null
                rxRV = null
                noPrescriptions.visibility = View.GONE
                rxRV = binding.patientDiagnosesRxRv
                rxRV!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                rxRVA = DiagnosesPrescriptionAdapter(prescriptions)
                rxRV!!.adapter = rxRVA


            }
        }, 1000)
    }



    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (args != null){
            prescriptions = args!!.getParcelableArrayList<Prescription>("prescriptions") as ArrayList<Prescription>
            println("Diagnoses Prescription Found: $prescriptions")

        }
        initPrescriptionsUI(prescriptions!!)

    }

    override fun onResume() {
        super.onResume()
        initPrescriptionsUI(prescriptions!!)
        println("Diagnoses Prescription Found: $prescriptions")

    }






    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, prescriptions: ArrayList<Prescription>) =
            PatientDiagnosesRxFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelableArrayList("prescriptions", prescriptions)

                }
            }
    }
}