package iot.empiaurhouse.triage.utils

import android.graphics.Color
import android.telephony.PhoneNumberUtils
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

    private fun isValidEmail(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false
        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Required Biodata"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.requestFocus()
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required biodata | ${inputLabel.capitalize(
                Locale.ROOT
            )
            }", Snackbar.LENGTH_LONG)

            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()

        }
        else if (!inputField.text.isNullOrBlank()){
            var isEmail = false
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(inputField.text.toString()).matches()){
                    isEmail = true
                    isValid = isEmail
                    inputFieldLayout.error = null
                    inputFieldLayout.boxStrokeColor = Color.parseColor("#0c204f")
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputField.text.toString()).matches()){
                    isEmail = false
                    isValid = isEmail
                    inputFieldLayout.error = "Invalid Email"
                    inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
                    inputFieldLayout.isFocusable = true
                    inputFieldLayout.requestFocus()
                    inputFieldLayout.isFocusable = false
                val invalidEmailFieldPrompt = Snackbar.make(viewContext,"Please provide a valid email address", Snackbar.LENGTH_SHORT)

                    invalidEmailFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
                    invalidEmailFieldPrompt.show()
            }
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

    private fun isValidPhone(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false
        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Required Biodata"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required biodata | ${inputLabel.capitalize(
                Locale.ROOT
            )
            }", Snackbar.LENGTH_LONG)

            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()

        }

        else if (!inputField.text.isNullOrBlank()){
            if (PhoneNumberUtils.isGlobalPhoneNumber(inputField.text.toString())){
                isValid = true
                inputFieldLayout.error = null
                inputFieldLayout.boxStrokeColor = Color.parseColor("#0c204f")
            } else if (!PhoneNumberUtils.isGlobalPhoneNumber(inputField.text.toString())){
                isValid = false
                inputFieldLayout.error = "Invalid Phone Number"
                inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
                val invalidPhoneFieldPrompt = Snackbar.make(viewContext,"Please provide a valid phone number", Snackbar.LENGTH_SHORT)

                invalidPhoneFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
                invalidPhoneFieldPrompt.show()
            }


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


    fun isValidPractitioner(fNInputField: TextInputEditText, fNInputFieldLayout: TextInputLayout,
                            lNInputField: TextInputEditText, lNInputFieldLayout: TextInputLayout,
                            pIDInputField: TextInputEditText, pIDInputFieldLayout: TextInputLayout,
                            phoneInputField: TextInputEditText, phoneInputFieldLayout: TextInputLayout,
                            emailInputField: TextInputEditText, emailInputFieldLayout: TextInputLayout): Boolean{
        var isPractitioner = false
        isPractitioner = (isValidText(fNInputField, fNInputFieldLayout, "First Name") && isValidText(lNInputField, lNInputFieldLayout,"Last Name")
                && isValidText(pIDInputField, pIDInputFieldLayout, "Practitioner ID") && isValidPhone(phoneInputField, phoneInputFieldLayout, "Phone")
                && isValidEmail(emailInputField, emailInputFieldLayout, "Email"))

        return isPractitioner
    }

}