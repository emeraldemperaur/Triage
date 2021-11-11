package iot.empiaurhouse.triage.utils

import android.graphics.Color
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import java.time.LocalDate
import java.util.*

class RecordEditValidator {

    private lateinit var viewContext: View

   fun initValidator(context: View){
       viewContext = context
   }

    fun isValidText(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
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

    fun isValidIText(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false
        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Required"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required metadata | ${inputLabel.capitalize(
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

    fun isValidBloodText(inputField: AutoCompleteTextView, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
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

    fun isValidPOIText(inputField: AutoCompleteTextView, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false

        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Required"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.requestFocus()
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required metadata | ${inputLabel.capitalize(
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

    fun isNumber(inputField: TextInputEditText, inputFieldLayout: TextInputLayout): Boolean{
        var isNumber = false
        if (inputField.text.isNullOrBlank()){
            isNumber = false
            inputFieldLayout.error = "Required pharmaceutical data"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide an inventory count", Snackbar.LENGTH_LONG)
            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()
        }else if (!inputField.text.isNullOrBlank()){
            if (inputField.text.toString().toInt() == 0 || inputField.text.toString().toInt() > 0){
                isNumber = true
                inputFieldLayout.error = null
                inputFieldLayout.boxStrokeColor = Color.parseColor("#0c204f")
            }else if (inputField.text.toString().toInt() < 0 || inputField.text.toString().toIntOrNull() == null){
                isNumber = false
            }
        }
        return isNumber
    }

    private fun isValidRxText(inputField: TextInputEditText, inputFieldLayout: TextInputLayout, inputLabel: String): Boolean{
        var isValid = false
        if (inputField.text.isNullOrBlank()){
            isValid = false
            inputFieldLayout.error = "Required Pharmaceutical data"
            inputFieldLayout.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout.isFocusable = true
            inputFieldLayout.isFocusable = false
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide required data | ${inputLabel.capitalize(
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

    @RequiresApi(Build.VERSION_CODES.O)
     fun isValidTimeText(inputField1: TextInputEditText, inputFieldLayout1: TextInputLayout,
                                inputField2: TextInputEditText, inputFieldLayout2: TextInputLayout): Boolean{
        var isValid = false
        val makeDate = LocalDate.parse(inputField1.text.toString())
        val expiryDate = LocalDate.parse(inputField2.text.toString())
        if (makeDate > expiryDate || makeDate == expiryDate){
            isValid = false
            inputFieldLayout1.error = "Invalid Date"
            inputFieldLayout1.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout1.isFocusable = true
            inputFieldLayout1.isFocusable = false
            inputFieldLayout2.error = "Invalid Expiry"
            inputFieldLayout2.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout2.isFocusable = true
            inputFieldLayout2.isFocusable = false
            inputFieldLayout1.requestFocus()
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide a valid Make & Expiry Date", Snackbar.LENGTH_LONG)
            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()
        } else if (expiryDate > makeDate){
            isValid = true
            inputFieldLayout1.error = null
            inputFieldLayout1.boxStrokeColor = Color.parseColor("#0c204f")
            inputFieldLayout1.isFocusable = false
            inputFieldLayout2.isFocusable = false
            inputFieldLayout2.error = null
            inputFieldLayout2.boxStrokeColor = Color.parseColor("#0c204f")

        }

        return isValid
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isValidITimeText(inputField1: TextInputEditText, inputFieldLayout1: TextInputLayout,
                        inputField2: TextInputEditText, inputFieldLayout2: TextInputLayout): Boolean{
        var isValid = false
        val makeDate = LocalDate.parse(inputField1.text.toString())
        val expiryDate = LocalDate.parse(inputField2.text.toString())
        if (makeDate > expiryDate || makeDate == expiryDate){
            isValid = false
            inputFieldLayout1.error = "Invalid Start Date"
            inputFieldLayout1.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout1.isFocusable = true
            inputFieldLayout1.isFocusable = false
            inputFieldLayout2.error = "Invalid End Date"
            inputFieldLayout2.boxStrokeColor = Color.parseColor("#800020")
            inputFieldLayout2.isFocusable = true
            inputFieldLayout2.isFocusable = false
            inputFieldLayout1.requestFocus()
            val missingFieldPrompt = Snackbar.make(viewContext,"Please provide a valid Start & End Date", Snackbar.LENGTH_LONG)
            missingFieldPrompt.anchorView = viewContext.rootView.findViewById(R.id.hub_foot_nav)
            missingFieldPrompt.show()
        } else if (expiryDate > makeDate){
            isValid = true
            inputFieldLayout1.error = null
            inputFieldLayout1.boxStrokeColor = Color.parseColor("#0c204f")
            inputFieldLayout1.isFocusable = false
            inputFieldLayout2.isFocusable = false
            inputFieldLayout2.error = null
            inputFieldLayout2.boxStrokeColor = Color.parseColor("#0c204f")

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


    @RequiresApi(Build.VERSION_CODES.O)
    fun isValidPharmaceutical(brandField: TextInputEditText, brandInputFieldLayout: TextInputLayout,
                              genericInputField: TextInputEditText, genericInputFieldLayout: TextInputLayout,
                              chemicalInputField: TextInputEditText, chemicalInputFieldLayout: TextInputLayout,
                              makeDateInputField: TextInputEditText, makeDateInputFieldLayout: TextInputLayout,
                              expiryDateInputField: TextInputEditText, expiryDateInputFieldLayout: TextInputLayout,
                              manufacturerInputField: TextInputEditText, manufacturerInputFieldLayout: TextInputLayout,
                              batchNumberInputField: TextInputEditText, batchNumberInputFieldLayout: TextInputLayout,
                              approvalNumberInputField: TextInputEditText, approvalNumberInputFieldLayout: TextInputLayout,
                              inStockInputField: TextInputEditText, inStockInputFieldLayout: TextInputLayout): Boolean{
        var isPharmaceutical = false
        isPharmaceutical = (isValidRxText(brandField, brandInputFieldLayout, "Brand Name") && isValidRxText(genericInputField, genericInputFieldLayout, "Generic Name")
                && isValidRxText(chemicalInputField, chemicalInputFieldLayout, "Chemical Name") && isValidRxText(makeDateInputField, makeDateInputFieldLayout, "Make Date")
                && isValidRxText(expiryDateInputField, expiryDateInputFieldLayout, "Expiry Date") && isValidRxText(manufacturerInputField, manufacturerInputFieldLayout, "Manufacturer")
                && isValidRxText(approvalNumberInputField, approvalNumberInputFieldLayout, "Approval Number") && isValidRxText(batchNumberInputField, batchNumberInputFieldLayout, "Batch Number")
                && isNumber(inStockInputField, inStockInputFieldLayout))
        if (isPharmaceutical){
            isPharmaceutical = isValidTimeText(makeDateInputField, makeDateInputFieldLayout, expiryDateInputField, expiryDateInputFieldLayout)
        }

        return isPharmaceutical
    }

}