package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.MultiRecordEditViewController
import iot.empiaurhouse.triage.databinding.FragmentRecordsEditorBinding
import iot.empiaurhouse.triage.model.Patient
import kotlin.properties.Delegates


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class RecordsEditorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecordsEditorBinding
    private lateinit var patientEditorView: ConstraintLayout
    private lateinit var patientEditMode: TextView
    private lateinit var patientFirstName: TextInputEditText
    private lateinit var patientLastName: TextInputEditText
    private lateinit var patientBirthDate: TextInputEditText
    private lateinit var patientBirthDateLayout: TextInputLayout
    private lateinit var patientBloodGroup: TextInputLayout
    private lateinit var patientBloodGroupField: AutoCompleteTextView
    private lateinit var patientInsurer: TextInputEditText
    private lateinit var patientInsurerID: TextInputEditText
    private lateinit var patientAddress: TextInputEditText
    private lateinit var patientPhone: TextInputEditText
    private lateinit var patientEditorButton: MaterialButton
    private lateinit var recordsEditorController: MultiRecordEditViewController
    private lateinit var patientMeta: Patient
    private var recordID by Delegates.notNull<Int>()
    private val args: RecordsEditorFragmentArgs by navArgs()

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
        return inflater.inflate(R.layout.fragment_records_editor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecordsEditorBinding.bind(view)
        patientEditorView = binding.patientRecordsEditorInclude.patientsEditorView
        patientEditMode = binding.patientRecordsEditorInclude.patientEditorMode
        patientFirstName = binding.patientRecordsEditorInclude.patientFirstNameFieldText
        patientLastName = binding.patientRecordsEditorInclude.patientLastNameFieldText
        patientBloodGroup = binding.patientRecordsEditorInclude.patientBloodGroupField
        patientBloodGroupField = binding.patientRecordsEditorInclude.patientBloodGroupFieldText
        patientAddress = binding.patientRecordsEditorInclude.patientAddressFieldText
        patientInsurer = binding.patientRecordsEditorInclude.patientInsurerNameFieldText
        patientInsurerID = binding.patientRecordsEditorInclude.patientInsurerIDFieldText
        patientBirthDate = binding.patientRecordsEditorInclude.patientBirthDateFieldText
        patientBirthDateLayout = binding.patientRecordsEditorInclude.patientBirthDateField
        patientPhone = binding.patientRecordsEditorInclude.patientPhoneFieldText
        patientEditorButton = binding.patientRecordsEditorInclude.createEditPatientButton
        recordsEditorController = MultiRecordEditViewController()
        recordID = args.recordID
        if (args.patient != null){
            patientMeta = args.patient!!
        }
        else if (args.patient == null){
            patientMeta = Patient(null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null,
                null, null)
        }
        initRecordEditorView(recordID)
    }


    private fun initRecordEditorView(recordID: Int){
        when(recordID){
            1 ->{
                recordsEditorController.initPatientEditorView(requireContext(), patientMeta,
                    patientEditorView, patientEditMode, patientFirstName, patientLastName,
                    patientBloodGroupField, patientBloodGroup, patientAddress, patientInsurer,
                    patientInsurerID, patientBirthDate, patientBirthDateLayout, patientPhone, patientEditorButton)
                patientEditorButton.setOnClickListener {

                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecordsEditorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}