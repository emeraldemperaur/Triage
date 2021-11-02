package iot.empiaurhouse.triage.controller

import android.app.DatePickerDialog
import android.content.Context
import android.text.InputType
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

    fun initDiagnosisEditorView(){
        
    }


}