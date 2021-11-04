package iot.empiaurhouse.triage.controller

import android.app.DatePickerDialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.*
import java.util.*
import kotlin.properties.Delegates

class MultiRecordEditViewController {

    private lateinit var  buttonText: String
    private lateinit var editTitle: String
    private lateinit var practitionerTitle: String
    private var isNew by Delegates.notNull<Boolean>()

    fun initPatientEditorView(context: Context, patient: Patient, patientEditView: ConstraintLayout,
                              editorMode: TextView, fNameField: TextInputEditText,
                              lNameField: TextInputEditText,
                              bloodGroupField: AutoCompleteTextView,bloodGroupFieldLayout: TextInputLayout,
                              addressField: TextInputEditText, insurerField: TextInputEditText,
                              insurerIDField: TextInputEditText, doBField: TextInputEditText, doBFieldLayout: TextInputLayout,
                              phoneField: TextInputEditText,
                              editorButton: MaterialButton){

        patientEditView.visibility = View.VISIBLE
        val bloodGroups = listOf("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-")
        val adapter = ArrayAdapter(context, R.layout.blood_group_item, bloodGroups)
        (bloodGroupFieldLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        if (patient.id != null){
            buttonText = "UPDATE"
            editTitle = "EDIT"
            isNew = false
            editorMode.text = editTitle
            editorButton.text = buttonText
            editorButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_patient_pivot_icon_24)
            fNameField.setText(patient.firstName)
            lNameField.setText(patient.lastName)
            if (patient.city.isNullOrBlank()) {
                addressField.setText(patient.address)
            }
            else if (!patient.city.isNullOrBlank()){
                val addressFieldText = "${patient.address}, ${patient.city}"
                addressField.setText(addressFieldText)
            }
            if (!patient.bloodGroup.isNullOrBlank()){
                (bloodGroupFieldLayout.editText as? AutoCompleteTextView)?.setText(patient.bloodGroup, false)

            }
            insurerField.setText(patient.insuranceVendor)
            insurerIDField.setText(patient.insuranceVendorID)
            doBField.setText(patient.birthDate)
            doBField.inputType = InputType.TYPE_NULL
            doBField.setTextIsSelectable(false)
            doBField.isFocusable = false
            if (!patient.phoneNumber.isNullOrBlank()){
                phoneField.setText(patient.phoneNumber)
            }
            doBField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!doBField.text.isNullOrBlank()){
                    val doB = doBField.text!!.split("-")
                    y = doB[0].toInt()
                    m = doB[1].toInt() - 1
                    d = doB[2].toInt()
                }


                val datePickerDialog:DatePickerDialog = DatePickerDialog(patientEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    doBField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
        }
        else {
            buttonText = "CREATE"
            editTitle = "CREATE"
            isNew = true
            editorButton.text = buttonText
            editorButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_patient_24)
            editorMode.text = editTitle
            doBField.inputType = InputType.TYPE_NULL
            doBField.setTextIsSelectable(false)
            doBField.isFocusable = false
            doBField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!doBField.text.isNullOrBlank()){
                    val doB = doBField.text!!.split("-")
                    y = doB[0].toInt()
                    m = doB[1].toInt() - 1
                    d = doB[2].toInt()
                }
                val datePickerDialog:DatePickerDialog = DatePickerDialog(patientEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    doBField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
        }

    }

    fun initPractitionerEditorView(context: Context, recordID: Int, practitioner: Practitioner? = null,
                                   nursePractitioner: NursePractitioner? = null,
                                   registeredNurse: RegisteredNurse? = null,
                                   practitionerEditView: ConstraintLayout,
                                   editorMode: TextView, editorTitle: TextView,
                                   fNameField: TextInputEditText,
                                   lNameField: TextInputEditText,practitionerIDField: TextInputEditText,
                                   phoneField: TextInputEditText, emailField: TextInputEditText, editIcon: ImageView,
                                   editorButton: MaterialButton){



        editTitle = "CREATE"
        buttonText = editTitle
        when(recordID){
            5 ->{
                practitionerEditView.visibility = View.VISIBLE
                practitionerTitle = "General Practitioner"
                if (practitioner!!.id != null){
                    editTitle = "EDIT"
                    buttonText = "UPDATE"
                    fNameField.setText(practitioner.firstName)
                    lNameField.setText(practitioner.lastName)
                    practitionerIDField.setText(practitioner.practitionerID)
                    phoneField.setText(practitioner.contactInfo)
                    emailField.setText(practitioner.emailInfo)

                }
                editorMode.text = editTitle
                editorTitle.text = practitionerTitle
                editorButton.text = buttonText



            }
            7 ->{
                practitionerEditView.visibility = View.VISIBLE
                practitionerTitle = "Registered Nurse"
                editIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.registered_nurse_ong))
                if (registeredNurse!!.id != null){
                    editTitle = "EDIT"
                    buttonText = "UPDATE"
                    fNameField.setText(registeredNurse.firstName)
                    lNameField.setText(registeredNurse.lastName)
                    practitionerIDField.setText(registeredNurse.practitionerID)
                    phoneField.setText(registeredNurse.contactInfo)
                    emailField.setText(registeredNurse.emailInfo)
                }
                editorMode.text = editTitle
                editorTitle.text = practitionerTitle
                editorButton.text = buttonText



            }
            8 ->{
                practitionerEditView.visibility = View.VISIBLE
                practitionerTitle = "Nurse Practitioner"
                if (nursePractitioner!!.id != null){
                    editTitle = "EDIT"
                    buttonText = "UPDATE"
                    fNameField.setText(nursePractitioner.firstName)
                    lNameField.setText(nursePractitioner.lastName)
                    practitionerIDField.setText(nursePractitioner.practitionerID)
                    phoneField.setText(nursePractitioner.contactInfo)
                    emailField.setText(nursePractitioner.emailInfo)
                }
                editorMode.text = editTitle
                editorTitle.text = practitionerTitle
                editorButton.text = buttonText



            }
        }
        
    }


    fun initDoctorEditorView(context: Context, doctor: Doctor,
                             doctorEditView: ConstraintLayout,
                             editorMode: TextView,
                             fNameField: TextInputEditText,
                             lNameField: TextInputEditText, practitionerIDField: TextInputEditText,
                             specialityField: AutoCompleteTextView, specialityFieldLayout: TextInputLayout,
                             phoneField: TextInputEditText, emailField: TextInputEditText,
                             editorButton: MaterialButton){

        editTitle = "CREATE"
        buttonText = editTitle
        doctorEditView.visibility = View.VISIBLE
        if (doctor.id != null){
            editTitle = "EDIT"
            buttonText = "UPDATE"
            fNameField.setText(doctor.firstName)
            lNameField.setText(doctor.lastName)
            practitionerIDField.setText(doctor.practitionerID)
            phoneField.setText(doctor.contactInfo)
            emailField.setText(doctor.emailInfo)
        }
        editorMode.text = editTitle
        editorButton.text = buttonText
        val dentistry = Speciality(1, "Dentistry", false)
        val radiology = Speciality(2, "Radiology", false)
        val neurology = Speciality(3, "Neurology", false)
        val diagnostics = Speciality(4, "Diagnostics", false)
        val immunology = Speciality(5, "Immunology", false)
        val surgery = Speciality(6, "Surgery", false)
        val specialities = listOf(dentistry, radiology, neurology, diagnostics, immunology, surgery)
        val specialitiesDescription = arrayListOf<String>()
        for (speciality in specialities){
            specialitiesDescription.add(speciality.specialityDescription!!)
        }
        val adapter = ArrayAdapter(context, R.layout.blood_group_item, specialitiesDescription)
        (specialityFieldLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        if (doctor.specialities != null && doctor.specialities.isNotEmpty()){
            (specialityFieldLayout.editText as? AutoCompleteTextView)?.setText(diagnostics.specialityDescription, false)
        }

    }


    fun initPharmaceuticalEditorView(pharmaceuticals: Pharmaceuticals, pharmaceuticalEditView: ConstraintLayout,
                                     editorMode: TextView, brandField: TextInputEditText, genericField: TextInputEditText,
                                     chemicalField: TextInputEditText, makeDateField: TextInputEditText,
                                     expiryDateField: TextInputEditText, manufacturerField: TextInputEditText,
                                     batchNumberField: TextInputEditText, approvalNumberField: TextInputEditText,
                                     inStockField: TextInputEditText, editorButton: MaterialButton){

        editTitle = "CREATE"
        buttonText = editTitle
        pharmaceuticalEditView.visibility = View.VISIBLE
        if (pharmaceuticals.id != null){
            editTitle = "EDIT"
            buttonText = "UPDATE"
            brandField.setText(pharmaceuticals.brandName)
            genericField.setText(pharmaceuticals.genericName)
            chemicalField.setText(pharmaceuticals.chemicalName)
            manufacturerField.setText(pharmaceuticals.manufacturerName)
            batchNumberField.setText(pharmaceuticals.batchNumber)
            approvalNumberField.setText(pharmaceuticals.approvalNumber)
            inStockField.setText(pharmaceuticals.inStock.toString())
            makeDateField.setText(pharmaceuticals.manufactureDate)
            expiryDateField.setText(pharmaceuticals.expiryDate)
            makeDateField.inputType = InputType.TYPE_NULL
            makeDateField.setTextIsSelectable(false)
            makeDateField.isFocusable = false
            expiryDateField.inputType = InputType.TYPE_NULL
            expiryDateField.setTextIsSelectable(false)
            expiryDateField.isFocusable = false
            makeDateField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!makeDateField.text.isNullOrBlank()){
                    val makeDate = makeDateField.text!!.split("-")
                    y = makeDate[0].toInt()
                    m = makeDate[1].toInt() - 1
                    d = makeDate[2].toInt()
                }


                val datePickerDialog:DatePickerDialog = DatePickerDialog(pharmaceuticalEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    makeDateField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
            expiryDateField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!expiryDateField.text.isNullOrBlank()){
                    val expiryDate = expiryDateField.text!!.split("-")
                    y = expiryDate[0].toInt()
                    m = expiryDate[1].toInt() - 1
                    d = expiryDate[2].toInt()
                }


                val datePickerDialog:DatePickerDialog = DatePickerDialog(pharmaceuticalEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    expiryDateField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }

        }else {
            makeDateField.inputType = InputType.TYPE_NULL
            makeDateField.setTextIsSelectable(false)
            makeDateField.isFocusable = false
            expiryDateField.inputType = InputType.TYPE_NULL
            expiryDateField.setTextIsSelectable(false)
            expiryDateField.isFocusable = false
            makeDateField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!makeDateField.text.isNullOrBlank()){
                    val makeDate = makeDateField.text!!.split("-")
                    y = makeDate[0].toInt()
                    m = makeDate[1].toInt() - 1
                    d = makeDate[2].toInt()
                }
                val datePickerDialog:DatePickerDialog = DatePickerDialog(pharmaceuticalEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    makeDateField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
            expiryDateField.setOnClickListener {
                val cal = Calendar.getInstance()
                var y = cal.get(Calendar.YEAR)
                var m = cal.get(Calendar.MONTH)
                var d = cal.get(Calendar.DAY_OF_MONTH)
                if (!expiryDateField.text.isNullOrBlank()){
                    val expiryDate = expiryDateField.text!!.split("-")
                    y = expiryDate[0].toInt()
                    m = expiryDate[1].toInt() - 1
                    d = expiryDate[2].toInt()
                }
                val datePickerDialog:DatePickerDialog = DatePickerDialog(pharmaceuticalEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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
                    expiryDateField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }

        }
        editorMode.text = editTitle
        editorButton.text = buttonText


    }


}