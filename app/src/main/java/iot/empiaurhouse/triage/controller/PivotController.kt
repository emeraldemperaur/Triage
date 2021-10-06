package iot.empiaurhouse.triage.controller

import android.content.Context
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PivotController {

    private lateinit var slideRightAnimation : Animation
    private lateinit var fadeInLabelAnimation : Animation
    private lateinit var fadeInModelTitleAnimation : Animation
    private lateinit var fadeInModelTextAnimation : Animation
    private lateinit var fadeInEndPointTitleAnimation : Animation
    private lateinit var fadeInEndPointTextAnimation : Animation
    private lateinit var fadeInPivotTypeTitleAnimation : Animation


    fun initPivotDialog(context: Context,
                        dataPivot: DataPivot,
                        triageBot: ImageView, pivotDialogView: MaterialCardView,
                        pivotingText: TypeWriterTextView, pivotLabel: TextView,
                        dataModelTitle: TextView, dataModelText: TextView,
                        endPointTitle: TextView, endPointText: TextView,
                        pivotTypeTitle: TextView, pivotTypeText: TextView,
                        parameterTitle: TextView,
                        AlphaParamTitle: TextView, AlphaParamText: TextView,
                        BetaParamTitle: TextView, BetaParamText: TextView,
                        EpsilonParamTitle: TextView, EpsilonParamText: TextView,
                        timeStreamTitle: TextView, timeStreamText: TextView,
                        ChiParamTitle: TextView, ChiParamText: TextView,
                        PsiParamTitle: TextView, PsiParamText: TextView,
                        pivotProgress: ProgressBar){


        triageBot.visibility = View.GONE
        slideRightAnimation = AnimationUtils.loadAnimation(context, R.anim.pull_in_left)
        fadeInLabelAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInModelTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInModelTextAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInEndPointTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInEndPointTextAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInPivotTypeTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)


        triageBot.startAnimation(slideRightAnimation)
        triageBot.visibility = View.VISIBLE
        slideRightAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                pivotLabel.startAnimation(fadeInLabelAnimation)
                pivotLabel.text = dataPivot.alias.capitalize(Locale.ROOT)
                pivotLabel.visibility = View.VISIBLE
                triageBot.clearAnimation()


            }

            override fun onAnimationStart(animation: Animation?) {
                pivotDialogView.visibility = View.VISIBLE
                pivotingText.visibility = View.VISIBLE
                pivotingText.setCharacterDelay(96)
                pivotingText.displayTextWithAnimation("Pivoting Data...")

            }
        })

        fadeInLabelAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                dataModelTitle.startAnimation(fadeInModelTitleAnimation)
                dataModelTitle.visibility = View.VISIBLE
                dataModelText.text = pivotObjectModelCheck(dataPivot)
                dataModelText.startAnimation(fadeInModelTextAnimation)
                dataModelText.visibility = View.VISIBLE
                endPointTitle.startAnimation(fadeInEndPointTitleAnimation)
                endPointTitle.visibility = View.VISIBLE
                endPointText.text = pivotObjectEndPointCheck(dataPivot)
                endPointText.startAnimation(fadeInEndPointTextAnimation)
                endPointText.visibility = View.VISIBLE

            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

        fadeInModelTitleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                dataModelText.clearAnimation()
                endPointTitle.clearAnimation()
                endPointText.clearAnimation()
                pivotTypeTitle.startAnimation(fadeInPivotTypeTitleAnimation)
                pivotTypeText.text = pivotObjectTypeCheck(dataPivot)
                pivotTypeText.startAnimation(fadeInEndPointTitleAnimation)
                pivotTypeTitle.visibility = View.VISIBLE
                pivotTypeText.visibility = View.VISIBLE
                pivotTypeText.clearAnimation()

            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

        fadeInPivotTypeTitleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onAnimationEnd(animation: Animation?) {
                val isDate = pivotObjectChronoCheck(dataPivot)
                if (isDate){
                    parameterTitle.visibility = View.VISIBLE
                    timeStreamTitle.visibility = View.VISIBLE
                    timeStreamText.text = pivotObjectTimeFlowInterpreter(dataPivot)
                    timeStreamText.visibility = View.VISIBLE
                    ChiParamTitle.visibility = View.VISIBLE
                    ChiParamText.visibility = View.VISIBLE
                    ChiParamText.text = pivotObjectDateFormat(dataPivot.dateParameterA)
                    PsiParamTitle.visibility = View.VISIBLE
                    if (dataPivot.timeStreamCode == 4){
                    PsiParamText.text = pivotObjectDateFormat(dataPivot.dateParameterB)
                    }
                    else{
                        val holderText = "Not Applicable"
                        PsiParamText.text = holderText
                    }
                    PsiParamText.visibility = View.VISIBLE
                    pivotProgress.visibility = View.VISIBLE
                    pivotProgress.isFocusable = true
                    pivotProgress.requestFocus()
                    pivotProgress.isFocusable = false
                }
                else if (!isDate){
                    parameterTitle.visibility = View.VISIBLE
                    AlphaParamTitle.visibility = View.VISIBLE
                    AlphaParamText.text = pivotObjectValueHolderCheck(dataPivot.valueParameterA)
                    AlphaParamText.visibility = View.VISIBLE
                    BetaParamTitle.visibility = View.VISIBLE
                    BetaParamText.text = pivotObjectValueHolderCheck(dataPivot.valueParameterB)
                    BetaParamText.visibility = View.VISIBLE
                    EpsilonParamTitle.visibility = View.VISIBLE
                    EpsilonParamText.text = pivotObjectValueHolderCheck(dataPivot.valueParameterC)
                    EpsilonParamText.visibility = View.VISIBLE
                    pivotProgress.visibility = View.VISIBLE
                    pivotProgress.isFocusable = true
                    pivotProgress.requestFocus()
                    pivotProgress.isFocusable = false

                }

                pivotTypeTitle.clearAnimation()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

    }



    private fun pivotObjectChronoCheck(dataPivot: DataPivot): Boolean{
        var isDate = false
        if (dataPivot.timeStreamCode != null){
            isDate = true
        }

        return isDate
    }


    private fun pivotObjectModelCheck(dataPivot: DataPivot): String{
        var modelEntity = ""
        when(dataPivot.entityCode){
            1 -> {
                modelEntity = "Patient"
            }
            2 -> {
                modelEntity = "Diagnosis"
            }
            3 -> {
                modelEntity = "Prescription"
            }
            4 -> {
                modelEntity = "Visit"
            }
            5 -> {
                modelEntity = "Pharmaceutical"

            }
            6 -> {
                when (dataPivot.practitionerCode) {
                    10 -> {
                        modelEntity = "General Practitioner"
                    }
                    20 -> {
                        modelEntity = "Doctor"
                    }
                    30 -> {
                        modelEntity = "Nurse Practitioner"
                    }
                    40 -> {
                        modelEntity = "Registered Nurse"
                    }
                }

            }
        }
        return modelEntity
    }

    private fun pivotObjectTimeFlowInterpreter(dataPivot: DataPivot): String{
        var timeFlow = ""
        when(dataPivot.timeStreamCode){
            1 -> {
                timeFlow = "On"
            }
            2 -> {
                timeFlow = "Before"
            }
            3 -> {
                timeFlow = "After"
            }
            4 -> {
                timeFlow = "Between"
            }
        }
                return timeFlow
    }


    private fun pivotObjectEndPointCheck(dataPivot: DataPivot): String{
        var entityEndPoint = ""
        when(dataPivot.endPointCode){
            1 -> {
                entityEndPoint = "First Name"
            }
            2 -> {
                entityEndPoint = "Last Name"
            }
            3 -> {
               entityEndPoint = "Birth Date"
            }
            4 -> {
                entityEndPoint = "Blood Group"
            }
            5 -> {
                entityEndPoint = "Insurer"
            }
            6 -> {
                entityEndPoint = "Insurer ID"
            }
            7 -> {
                entityEndPoint = "First Name"
            }
            8 -> {
                entityEndPoint = "Last Name"
            }
            9 -> {
                entityEndPoint = "Practitioner ID"
            }
            10 -> {
                entityEndPoint = "Synopsis"
            }
            11 -> {
                entityEndPoint = "Visit Date"
            }
            12 -> {
               entityEndPoint = "Insurer ID"
            }
            13 -> {
                entityEndPoint = "Level"
            }
            14 -> {
                entityEndPoint = "Name"
            }
            15 -> {
                entityEndPoint = "Prescriber"
            }
            16 -> {
                entityEndPoint = "Prescriber ID"
            }
            17 -> {
                entityEndPoint = "Insurer ID"
            }
            18 -> {
                entityEndPoint = "Date"
            }
            19 -> {
                entityEndPoint = "Host"
            }
            20 -> {
                entityEndPoint = "Host ID"
            }
            21 -> {
                entityEndPoint = "Visit Time"
            }
            22 -> {
                entityEndPoint = "Description"
            }
            23 -> {
                entityEndPoint = "Visit Date"
            }
            24 -> {
                entityEndPoint = "Insurer ID"
            }
            25 -> {
                entityEndPoint = "Brand Name"
            }
            26 -> {
                entityEndPoint = "Generic Name"
            }
            27 -> {
                entityEndPoint = "Chemical Name"
            }
            28 -> {
                entityEndPoint = "Manufacturer"
            }
            29 -> {
                entityEndPoint = "Make Date"
            }
            30 -> {
                entityEndPoint = "Expiry Date"

            }



        }
        return entityEndPoint
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun pivotObjectDateFormat(stringDate: String?): String {
        val dateObject = LocalDate.parse(stringDate)
        val formatter = DateTimeFormatter.ofPattern("dd, MMMM yyyy")
        return dateObject.format(formatter)
    }

    fun pivotObjectTypeCheck(dataPivot: DataPivot): String{
        var pivotType = ""
        val tSC = dataPivot.timeStreamCode
        val vPC = dataPivot.valueParamCode
        if (tSC != null){
            pivotType = "Chronological"
        }
        else if (vPC != null || dataPivot.valueParamCode!! > 0){
            pivotType = "Value Based"
        }

        return pivotType

        }

    fun pivotObjectValueHolderCheck(pivotValue: String?): String?{
        var valueString = pivotValue
        if (pivotValue != null) {
            if (pivotValue.isBlank() || pivotValue == ""){
                valueString = "not provided"
            }
        }

        return valueString!!.capitalize(Locale.ROOT)
    }





}