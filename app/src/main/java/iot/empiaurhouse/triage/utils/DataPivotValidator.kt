package iot.empiaurhouse.triage.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import iot.empiaurhouse.triage.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataPivotValidator {

    private val fail : String = "#800020"
    private val success : String = "#0c204f"
    private val ignored : String = "#A9A9A9"
    private var timeStreamResult: Boolean? = null
    private var timeStreamCode: Int? = null
    private lateinit var editContext: Context
    private lateinit var dateParameterLayout: ConstraintLayout
    private lateinit var valueParameterLayout: ConstraintLayout




    fun validateAlias(context: Context,  parameterLayout: ConstraintLayout, pivotAliasInput: EditText, pivotAliasLabel: TextView): Boolean{
        var isValid = false
        if (pivotAliasInput.nonEmpty()){
            isValid = true
            pivotAliasLabel.setTextColor(Color.parseColor(success))
            // pivotAliasInput.error = null

        }
        else if (!pivotAliasInput.nonEmpty()){
            isValid = false
            pivotAliasLabel.setTextColor(Color.parseColor(fail))
            pivotAliasInput.error = "Please provide a Pivot alias"
            val pivotAliasInputError = Snackbar.make(context, parameterLayout, "Heads up!...You need to provide a unique label or alias for this data pivot model", Snackbar.LENGTH_LONG)
            pivotAliasInputError.show()

        }

        return isValid
    }

    fun validateValueParameter(context: Context, parameterLayout: ConstraintLayout, alphaValue: EditText, alphaValueIcon: ImageView,
                               betaValue: EditText, betaValueIcon: ImageView,
                               epsilonValue: EditText, epsilonValueIcon: ImageView): Int{
        var isValid = 0
        valueParameterLayout = parameterLayout
        if (alphaValue.nonEmpty()){
            alphaValueIcon.setColorFilter(Color.parseColor(success))
            betaValueIcon.setColorFilter(Color.parseColor(ignored))
            epsilonValueIcon.setColorFilter(Color.parseColor(ignored))
            isValid = 1
        }
        if (alphaValue.nonEmpty() && betaValue.nonEmpty()){
            alphaValueIcon.setColorFilter(Color.parseColor(success))
            betaValueIcon.setColorFilter(Color.parseColor(success))
            epsilonValueIcon.setColorFilter(Color.parseColor(success))
            isValid = 2
        }
        if (alphaValue.nonEmpty() && betaValue.nonEmpty() && epsilonValue.nonEmpty()){
            alphaValueIcon.setColorFilter(Color.parseColor(success))
            betaValueIcon.setColorFilter(Color.parseColor(success))
            epsilonValueIcon.setColorFilter(Color.parseColor(success))
            isValid = 3
        }
        else if (!alphaValue.nonEmpty() && parameterLayout.visibility == View.VISIBLE){
            alphaValueIcon.setColorFilter(Color.parseColor(fail))
            betaValueIcon.setColorFilter(Color.parseColor(ignored))
            epsilonValueIcon.setColorFilter(Color.parseColor(ignored))
            isValid = 0
            val alphaValueNotFound = Snackbar.make(context, parameterLayout, "Heads up!...You need to provide an alpha (α) criterion for a value based data pivot", Snackbar.LENGTH_LONG)
            alphaValueNotFound.show()

        }

        return isValid
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun validateDateParameter(context: Context, parameterLayout: ConstraintLayout,
                              timeStream: RadioGroup, chiDatePicker: DatePicker,
                              chiDatePickerIcon: ImageView, psiDatePicker: DatePicker,
                              psiDatePickerIcon: ImageView): Boolean{
        dateParameterLayout = parameterLayout
        var isValid = false
        //val timeStreamSelected = timeStreamSelected(timeStream)
        editContext = context
        val timeFlow = timeStream.checkedRadioButtonId
        var timeFlowCode = 0
        when(timeFlow){
            R.id.onDate -> {
                timeFlowCode = 1
                chiDatePickerIcon.setColorFilter(Color.parseColor(success))
                psiDatePickerIcon.setColorFilter(Color.parseColor(ignored))
                timeStreamCode = timeFlowCode
                isValid = true
            }
            R.id.beforeDate -> {
                isValid = true
                timeFlowCode = 2
                chiDatePickerIcon.setColorFilter(Color.parseColor(success))
                psiDatePickerIcon.setColorFilter(Color.parseColor(ignored))
                timeStreamCode = timeFlowCode

            }
            R.id.afterDate -> {
                isValid = true
                timeFlowCode = 3
                chiDatePickerIcon.setColorFilter(Color.parseColor(success))
                psiDatePickerIcon.setColorFilter(Color.parseColor(ignored))
                timeStreamCode = timeFlowCode

            }
            R.id.betweenDate -> {
                timeFlowCode = 4
                timeStreamCode = timeFlowCode
            }

            }

        if (timeFlowCode == 4){

            isValid = timelineValidation(chiDatePicker, psiDatePicker, chiDatePickerIcon, psiDatePickerIcon)
            if (!isValid){

                val timeStreamError = Snackbar.make(context, parameterLayout, "Uh Oh...Looks like those epoch dates would break a time continuum. χ comes before ψ!", Snackbar.LENGTH_LONG)
                timeStreamError.show()
            }
        }

        return isValid
    }

    fun fetchTimeFlowCode(): Int?{
        return timeStreamCode
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun timelineValidation(chiDatePicker: DatePicker, psiDatePicker: DatePicker, chiDatePickerIcon: ImageView, psiDatePickerIcon: ImageView): Boolean{
        var isValidDate = false
        val chiDate = LocalDate.of(chiDatePicker.year, chiDatePicker.month + 1, chiDatePicker.dayOfMonth)
        val psiDate = LocalDate.of(psiDatePicker.year, psiDatePicker.month + 1, psiDatePicker.dayOfMonth)
        if (psiDate < chiDate || psiDate == chiDate){
            isValidDate = false
            chiDatePickerIcon.setColorFilter(Color.parseColor(fail))
            psiDatePickerIcon.setColorFilter(Color.parseColor(ignored))
        }
        else if (psiDate > chiDate){
            isValidDate = true
            chiDatePickerIcon.setColorFilter(Color.parseColor(success))
            psiDatePickerIcon.setColorFilter(Color.parseColor(success))
        }

        return isValidDate
    }

    fun timeStreamNotSelected(radioGroup: RadioGroup): Boolean {
        var unselected = false
        if (dateParameterLayout.visibility == View.VISIBLE &&  radioGroup.checkedRadioButtonId == -1)
        {
            unselected = false
           val timeStreamUnselected = Snackbar.make(editContext, dateParameterLayout, "Heads up!...You need to pick a time stream option for a chronologic based data pivot", Snackbar.LENGTH_LONG)
           timeStreamUnselected.show()
        }
        else if (dateParameterLayout.visibility == View.VISIBLE && radioGroup.checkedRadioButtonId != -1){
            unselected = true
        }
        return unselected
    }

    fun timeStreamSelected(radioGroup: RadioGroup): Boolean?{
        if (valueParameterLayout.visibility == View.GONE && dateParameterLayout.visibility == View.VISIBLE && radioGroup.checkedRadioButtonId == -1)
        {
            timeStreamResult = false
            val timeStreamUnselected = Snackbar.make(editContext, dateParameterLayout, "Heads up!...You need to pick a time stream option for a chronologic based data pivot", Snackbar.LENGTH_LONG)
            timeStreamUnselected.show()
        }
        else if (valueParameterLayout.visibility == View.GONE && dateParameterLayout.visibility == View.VISIBLE && radioGroup.checkedRadioButtonId != -1){
            timeStreamResult = true
        }
     return timeStreamResult
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun pivotTimeStamp(): String {
        val timeNow = LocalDateTime.now()
        val timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return timeNow.format(timeFormat)
    }


}