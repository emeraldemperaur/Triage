package iot.empiaurhouse.triage.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
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
    private lateinit var pivotEditButton: MaterialButton




    fun validateAlias(context: Context,  parameterLayout: ConstraintLayout, pivotAliasInput: EditText,
                      pivotAliasLabel: TextView, pivotTitleFocus: View, pivotButton: MaterialButton): Boolean{
        var isValid = false
        pivotEditButton = pivotButton
        if (pivotAliasInput.nonEmpty() && pivotAliasInput.text.toString().trim() != ""){
            isValid = true
            pivotAliasLabel.setTextColor(Color.parseColor(success))

        }
        else if (!pivotAliasInput.nonEmpty() || pivotAliasInput.text.toString().trim() == ""){
            isValid = false
            pivotAliasLabel.setTextColor(Color.parseColor(fail))
            pivotAliasInput.error = "Please provide a Pivot alias"
            pivotTitleFocus.requestFocus()
            val pivotAliasInputError = Snackbar.make(context, parameterLayout, "Heads up!...You need to provide a unique label or alias for this data pivot model", Snackbar.LENGTH_LONG)
            pivotAliasInputError.show()
            pivotAliasInputError.anchorView = pivotAliasLabel
            pivotTitleFocus.clearFocus()


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
            epsilonValueIcon.setColorFilter(Color.parseColor(ignored))
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
            alphaValueIcon.requestFocus()
            isValid = 0
            val alphaValueNotFound = Snackbar.make(context, parameterLayout, "Heads up!...You need to provide an alpha (α) criterion for a value based data pivot", Snackbar.LENGTH_LONG)
            alphaValueNotFound.anchorView = pivotEditButton
            alphaValueNotFound.show()
            alphaValueIcon.clearFocus()

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
                chiDatePickerIcon.requestFocus()
                val timeStreamError = Snackbar.make(context, parameterLayout, "Uh Oh...Looks like those epoch dates would break a time continuum. χ comes before ψ!", Snackbar.LENGTH_LONG)
                timeStreamError.anchorView = pivotEditButton
                timeStreamError.show()
                chiDatePickerIcon.clearFocus()
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

    @RequiresApi(Build.VERSION_CODES.P)
    fun timeStreamNotSelected(radioGroup: RadioGroup): Boolean {
        var unselected = false
        if (dateParameterLayout.visibility == View.VISIBLE &&  radioGroup.checkedRadioButtonId == -1)
        {
            unselected = false
            radioGroup.requestFocus()
            radioGroup.outlineAmbientShadowColor = Color.parseColor(fail)
            val timeStreamUnselected = Snackbar.make(editContext, dateParameterLayout, "Heads up!...You need to pick a time stream option for a chronologic based data pivot", Snackbar.LENGTH_LONG)
            timeStreamUnselected.anchorView = pivotEditButton
            timeStreamUnselected.show()
            radioGroup.clearFocus()
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
            radioGroup.requestFocus()
            val timeStreamUnselected = Snackbar.make(editContext, dateParameterLayout, "Heads up!...You need to pick a time stream option for a chronologic based data pivot", Snackbar.LENGTH_LONG)
            timeStreamUnselected.anchorView = pivotEditButton
            timeStreamUnselected.show()
            radioGroup.clearFocus()
        }
        else if (valueParameterLayout.visibility == View.GONE && dateParameterLayout.visibility == View.VISIBLE && radioGroup.checkedRadioButtonId != -1){
            timeStreamResult = true
        }
     return timeStreamResult
    }


    fun cleanDateString(year: Int, month: Int, day: Int): String{
        var dateString = ""
        var dayString = day.toString()
        var monthInt = month + 1
        var monthString = ""
        if (monthInt  < 10){
            monthString = "0${monthInt + 1}"
        }
        if (monthInt  > 9){
            monthString = monthInt.toString()
        }
        if (day < 10){
            dayString = "0$day"

        }

        dateString = "$year-$monthString-$dayString"


        return dateString
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun pivotTimeStamp(): String {
        val timeNow = LocalDateTime.now()
        val timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return timeNow.format(timeFormat)
    }


}