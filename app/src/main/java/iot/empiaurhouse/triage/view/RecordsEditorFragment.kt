package iot.empiaurhouse.triage.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.MultiRecordEditViewController
import iot.empiaurhouse.triage.databinding.FragmentRecordsEditorBinding
import iot.empiaurhouse.triage.model.Patient
import iot.empiaurhouse.triage.utils.RecordEditValidator
import java.util.*
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
    private lateinit var patientFirstNameLayout: TextInputLayout
    private lateinit var patientLastName: TextInputEditText
    private lateinit var patientLastNameLayout: TextInputLayout
    private lateinit var patientBirthDate: TextInputEditText
    private lateinit var patientBirthDateLayout: TextInputLayout
    private lateinit var patientBloodGroup: TextInputLayout
    private lateinit var patientBloodGroupField: AutoCompleteTextView
    private lateinit var patientInsurer: TextInputEditText
    private lateinit var patientInsurerLayout: TextInputLayout
    private lateinit var patientInsurerID: TextInputEditText
    private lateinit var patientInsurerIDLayout: TextInputLayout
    private lateinit var patientAddress: TextInputEditText
    private lateinit var patientAddressLayout: TextInputLayout
    private lateinit var patientPhone: TextInputEditText
    private lateinit var patientPhoneLayout: TextInputLayout
    private lateinit var patientEditorButton: MaterialButton
    private lateinit var recordValidator: RecordEditValidator
    private lateinit var recordsEditorController: MultiRecordEditViewController
    private lateinit var navController: NavController
    private lateinit var patientMeta: Patient
    private var metaID: Int? = null
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
        patientFirstNameLayout = binding.patientRecordsEditorInclude.patientFirstNameField
        patientLastName = binding.patientRecordsEditorInclude.patientLastNameFieldText
        patientLastNameLayout = binding.patientRecordsEditorInclude.patientLastNameField
        patientBloodGroup = binding.patientRecordsEditorInclude.patientBloodGroupField
        patientBloodGroupField = binding.patientRecordsEditorInclude.patientBloodGroupFieldText
        patientAddress = binding.patientRecordsEditorInclude.patientAddressFieldText
        patientAddressLayout = binding.patientRecordsEditorInclude.patientAddressField
        patientInsurer = binding.patientRecordsEditorInclude.patientInsurerNameFieldText
        patientInsurerLayout = binding.patientRecordsEditorInclude.patientInsurerNameField
        patientInsurerID = binding.patientRecordsEditorInclude.patientInsurerIDFieldText
        patientInsurerIDLayout = binding.patientRecordsEditorInclude.patientInsurerIDField
        patientBirthDate = binding.patientRecordsEditorInclude.patientBirthDateFieldText
        patientBirthDateLayout = binding.patientRecordsEditorInclude.patientBirthDateField
        patientPhone = binding.patientRecordsEditorInclude.patientPhoneFieldText
        patientPhoneLayout = binding.patientRecordsEditorInclude.patientPhoneField
        patientEditorButton = binding.patientRecordsEditorInclude.createEditPatientButton
        recordsEditorController = MultiRecordEditViewController()
        recordValidator = RecordEditValidator()
        navController = findNavController()
        recordID = args.recordID
        if (args.patient != null){
            patientMeta = args.patient!!
            metaID = patientMeta.id
        }
        else if (args.patient == null){
            patientMeta = Patient(null, null, null, null, null,
                null, null, null, null, null,
                null, arrayListOf(), null, null, null, null,
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
                recordValidator.initValidator(patientEditorView)
                onBackPressed()
                patientEditorButton.setOnClickListener {
                    val patientCheck = recordValidator.isValidPatient(patientFirstName, patientFirstNameLayout,
                        patientLastName, patientLastNameLayout, patientBirthDate, patientBirthDateLayout,
                        patientBloodGroupField, patientBloodGroup, patientInsurer, patientInsurerLayout,
                        patientInsurerID, patientInsurerIDLayout, patientAddress, patientAddressLayout, patientPhone, patientPhoneLayout)
                    if (patientCheck){
                        //proceed to progress view with patientMetaOutput object
                        val fullName = "${patientFirstName.text!!.trim()} ${patientLastName.text!!.trim()}"
                        val shortName = "${patientLastName.text!!.trim()}, ${patientFirstName.text!!.first()}"
                        val delimitedFullName = "${patientLastName.text!!.trim()}, ${patientFirstName.text!!.trim()}"
                        var isNew = true
                        if (patientMeta.id != null){
                            isNew = false
                        }
                        val patientMetaOutput = Patient(patientMeta.id, patientFirstName.text.toString().trim(),
                            patientLastName.text.toString().trim(), patientBloodGroupField.text.toString(),
                            patientAddress.text.toString().trim(), null, patientPhone.text.toString().trim(),
                            patientInsurer.text.toString().trim(), patientInsurerID.text.toString().trim(),
                            patientMeta.profileImagePath, patientBirthDate.text.toString(),
                            patientMeta.diagnoses, patientMeta.image,
                            fullName, shortName, delimitedFullName,
                            patientMeta.systemImagePath, isNew)

                        println("Found patientMetaOutput:\n\t $patientMetaOutput")
                        val input = RecordsEditorFragmentDirections.editingRecordDialog(recordID, patientMetaOutput)
                        navController.navigate(input)
                    }
                }
            }
            2 ->{

            }
        }
    }



    fun onBackPressed(){
        if (metaID == null) {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        isEnabled = true
                        var promptLabel = ""
                        var entityType = ""
                        var promptText = ""
                        var entityDrawable = 0
                        when (recordID) {
                            1 -> {
                                entityType = "Patient"
                                entityDrawable = R.drawable.ic_patient_pivot_icon_24
                                val patientFN = patientFirstName.text.toString().trim()
                                val patientLN = patientLastName.text.toString().trim()
                                promptLabel = patientFN
                                if (patientLN.isNotBlank() && patientFN.isNotBlank()) {
                                    promptLabel = "$patientLN, ${patientFN.first()}"
                                }
                                promptText = "Are you sure you'd like to discard this unsaved $entityType record for ${
                                    promptLabel.capitalize(
                                        Locale.ROOT
                                    )
                                }?"
                            }
                            2 -> {
                                entityType = "Diagnosis"
                                entityDrawable = R.drawable.ic_virus_art_icon

                            }
                            3 -> {
                                entityType = "Prescription"
                                entityDrawable = R.drawable.rx_sheet_icon
                            }
                            4 -> {
                                entityType = "Visit"
                                entityDrawable = R.drawable.visit_icon_png3
                            }
                            5 -> {
                                entityType = "General Practitioner"
                                entityDrawable = R.drawable.practitioners_pivot_icon
                            }
                            6 -> {
                                entityType = "Doctor"
                                entityDrawable = R.drawable.rodofasclepius_icon
                            }
                            7 -> {
                                entityType = "Registered Nurse"
                                entityDrawable = R.drawable.registered_nurse_ong
                            }
                            8 -> {
                                entityType = "Nurse Practitioner"
                                entityDrawable = R.drawable.practitioners_pivot_icon

                            }
                            9 -> {
                                entityType = "Pharmaceuticals"
                                entityDrawable = R.drawable.ic_pharmaceuticals_pivot
                            }
                        }
                        if (promptLabel.isBlank()) {
                            navController.navigateUp()
                            isEnabled = false
                        } else if (promptLabel.isNotBlank()) {
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Exiting $entityType Editor")
                            builder.setIcon(entityDrawable)
                            builder.setMessage(promptText)
                            builder.setPositiveButton("YES") { _, _ ->
                                navController.navigateUp()
                                isEnabled = false
                            }
                            builder.setNegativeButton("NO") { dialog, _ ->
                                dialog.dismiss()
                                isEnabled = true
                            }
                            builder.show()
                        }
                    }
                })
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