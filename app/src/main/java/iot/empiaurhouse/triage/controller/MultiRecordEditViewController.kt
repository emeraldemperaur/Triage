package iot.empiaurhouse.triage.controller

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.Patient
import java.util.*
import kotlin.properties.Delegates

class MultiRecordEditViewController {

    private lateinit var  buttonText: String
    private lateinit var editTitle: String
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
            buttonText = "UPDATE PATIENT"
            editTitle = "UPDATE"
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
                bloodGroupField.setText(patient.bloodGroup)
            }
            insurerField.setText(patient.insuranceVendor)
            insurerIDField.setText(patient.insuranceVendorID)
            doBField.setText(patient.birthDate)
            if (!patient.phoneNumber.isNullOrBlank()){
                phoneField.setText(patient.phoneNumber)
            }



            doBField.setOnClickListener {
                val cal = Calendar.getInstance()
                val y = cal.get(Calendar.YEAR)
                val m = cal.get(Calendar.MONTH)
                val d = cal.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog:DatePickerDialog = DatePickerDialog(patientEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthInt = monthOfYear + 1
                    val datePicked = "$year-$monthInt-$dayOfMonth"
                    doBField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
            /*editorButton.setOnClickListener {
                //set Edit Text
                val stagedPatient = Patient(patient.id, patient.firstName, patient.lastName,
                    patient.bloodGroup, patient.address, patient.city, patient.phoneNumber,
                    patient.insuranceVendor, patient.insuranceVendorID, patient.profileImagePath,
                    patient.birthDate, patient.diagnoses, patient.image, patient.fullName, patient.shortName,
                    patient.delimitedFullName, patient.systemImagePath, patient.new)
                patientObject = stagedPatient
                println("Found Modified Staged Patient Meta:\n\t $stagedPatient")


            }*/

        }
        else {
            buttonText = "CREATE PATIENT"
            editTitle = "CREATE"
            isNew = true
            editorButton.text = buttonText
            editorButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_patient_24)
            editorMode.text = editTitle
            doBFieldLayout.setOnClickListener {
                val cal = Calendar.getInstance()
                val y = cal.get(Calendar.YEAR)
                val m = cal.get(Calendar.MONTH)
                val d = cal.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog:DatePickerDialog = DatePickerDialog(patientEditView.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthInt = monthOfYear + 1
                    val datePicked = "$year-$monthInt-$dayOfMonth"
                    doBField.setText(datePicked)
                }, y, m, d)

                datePickerDialog.show()
            }
            /*editorButton.setOnClickListener {

                val stagedPatient = Patient(null, fNameField.text.toString(), lNameField.text.toString(),
                    bloodGroupField.text.toString(), addressField.text.toString(), city, phone,
                    insurerField.text.toString(), insurerIDField.text.toString(), null, birthDate, arrayListOf(),
                    null, fullName, shortName, delimitedName, null, isNew)
                patientObject = stagedPatient
                println("Found Novel Staged Patient Meta:\n\t $stagedPatient")

            }*/
        }

    }


}