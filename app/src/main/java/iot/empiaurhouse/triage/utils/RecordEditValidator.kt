package iot.empiaurhouse.triage.utils

import android.graphics.Color
import android.view.View
import android.widget.AutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import java.util.*

class RecordEditValidator {

    private lateinit var viewContext: View

   fun initValidator(context: View){
       viewContext = context
   }

    private fun isValidText(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false
        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Missing Biodata"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide missing biodata | ${inputLabel.capitalize(
                Locale.ROOT
            )
            }", Snackbar.LENGTH_LONG)

            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()

        }
        else if (!inputField.text.isNullOrBlank()){
            isValid = true
            inputFieldLayout.error = null
            inputFieldLayout.boxStrokeColor = Color.parseColor("#0c204f")

        }
        return isValid
    }

    private fun isValidBloodText(inputField: AutoCompleteTextView, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false

        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Missing Biodata"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.requestFocus()
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required biodata | ${inputLabel.capitalize(
                Locale.ROOT
            )
            }", Snackbar.LENGTH_LONG)

            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()
        }
        else if (!inputField.text.isNullOrBlank()){
            isValid = true
            inputFieldLayout.error = null
            inputFieldLayout.boxStrokeColor = Color.parseColor("#0c204f")

        }
        return isValid
    }




    fun isValidPatient(fNInputField: TextInputEditText, fNInputFieldLayout: TextInputLayout,
                       lNInputField: TextInputEditText, lNInputFieldLayout: TextInputLayout,
                       doBInputField: TextInputEditText, doBInputFieldLayout: TextInputLayout,
                       bGroupInputField: AutoCompleteTextView, bGroupInputFieldLayout: TextInputLayout,
                       insurerInputField: TextInputEditText, insurerInputFieldLayout: TextInputLayout,
                       insurerIDInputField: TextInputEditText, insurerIDInputFieldLayout: TextInputLayout,
                       addressInputField: TextInputEditText, addressInputFieldLayout: TextInputLayout,
                       phoneInputField: TextInputEditText, phoneInputFieldLayout: TextInputLayout): Boolean{

        var isPatient = false
        isPatient = (isValidText(fNInputField, fNInputFieldLayout, "First Name") && isValidText(lNInputField, lNInputFieldLayout, "Last Name")
                && isValidText(doBInputField, doBInputFieldLayout, "Birth Date") && isValidBloodText(bGroupInputField, bGroupInputFieldLayout, "Blood Group")
                && isValidText(insurerInputField, insurerInputFieldLayout, "Insurer") && isValidText(insurerIDInputField, insurerIDInputFieldLayout, "Insurer ID")
                && isValidText(addressInputField, addressInputFieldLayout, "Address") && isValidText(phoneInputField, phoneInputFieldLayout, "Phone"))
     return isPatient
    }

}