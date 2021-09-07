package iot.empiaurhouse.triage.controller

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R

class QuickPivotController {

    private lateinit var patientEntityCard: CardView
    private lateinit var diagnosisEntityCard: CardView
    private lateinit var prescriptionEntityCard: CardView
    private lateinit var visitEntityCard: CardView
    private lateinit var practitionersEntityCard: CardView
    private lateinit var doctorEntityCard: CardView
    private lateinit var nursePractitionerEntityCard: CardView
    private lateinit var registeredNurseEntityCard: CardView
    private lateinit var pharmaceuticalEntityCard: CardView
    private var entitySelected: Boolean = false
    private var entityOptions: ArrayList<MaterialCardView> = arrayListOf()
    private var optionsLayouts: ArrayList<ConstraintLayout> = arrayListOf()
    private lateinit var slideUpAnimation : Animation


    // Include init reference to respective subview view IDs


    //initView on click return data to next function in control flow

    fun initPivotEditor(pivotID: Int, patientCard: MaterialCardView, diagnosisCard: MaterialCardView,
                        prescriptionCard: MaterialCardView, visitCard: MaterialCardView,
                        pharmaceuticalCard: MaterialCardView, practitionersCard: MaterialCardView,
                        optionLayouts: ArrayList<ConstraintLayout>, context: Context): Int{
        var optionCode = 0
        optionsLayouts = optionLayouts
        slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        entityOptions.add(patientCard)
        entityOptions.add(diagnosisCard)
        entityOptions.add(prescriptionCard)
        entityOptions.add(visitCard)
        entityOptions.add(pharmaceuticalCard)
        entityOptions.add(practitionersCard)

        when(pivotID){
            0 -> {
                entitySelected = false
                // control color with theme/style
            }
            1 -> {
                toggleEntityOptions(patientCard, entityOptions)
                toggleEntityOptionLayouts(0, optionsLayouts)
                optionCode = 1
                entitySelected = true
            }
            2 -> {
                toggleEntityOptions(diagnosisCard, entityOptions)
                toggleEntityOptionLayouts(1, optionsLayouts)
                optionCode = 2
                entitySelected = true
            }
            3 -> {
                toggleEntityOptions(prescriptionCard, entityOptions)
                toggleEntityOptionLayouts(2, optionsLayouts)
                optionCode = 3
                entitySelected = true
            }
            4 -> {
                toggleEntityOptions(visitCard, entityOptions)
                toggleEntityOptionLayouts(3, optionsLayouts)
                optionCode = 4
                entitySelected = true
            }
            5 -> {
                toggleEntityOptions(pharmaceuticalCard, entityOptions)
                toggleEntityOptionLayouts(4, optionsLayouts)
                optionCode = 5
                entitySelected = true
            }
            6 -> {
                toggleEntityOptions(practitionersCard, entityOptions)
                toggleEntityOptionLayouts(5, optionsLayouts)
                optionCode = 6
                entitySelected = true
            }
            7 -> {
                entitySelected = true
            }
            8 -> {
                entitySelected = true
            }
            9 -> {
                entitySelected = true
            }

        }
        return optionCode
    }

    //listen for view IDs of selected view
    fun initEndPointViewConductor(context: Context, entityOptions: ArrayList<MaterialCardView>,
                                  optionLayouts: ArrayList<ConstraintLayout>): Int{
        var optionCode = 0
        optionsLayouts = optionLayouts
        slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        for (entity in entityOptions){
            entity.setOnClickListener {
                when(entity.id){
                     R.id.patient_entity -> {
                         toggleEntityOptions(entity, entityOptions)
                         toggleEntityOptionLayouts(0, optionsLayouts)
                         optionCode = 1
                            }

                    R.id.diagnosis_entity -> {
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(1, optionsLayouts)
                        optionCode = 2
                    }
                    R.id.prescription_entity -> {
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(2, optionsLayouts)
                        optionCode = 3
                    }
                    R.id.visit_entity -> {
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(3, optionsLayouts)
                        optionCode = 4
                    }
                    R.id.pharmaceutical_entity -> {
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(4, optionsLayouts)
                        optionCode = 5

                    }
                    R.id.practitioner_entity -> {
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(5, optionsLayouts)

                        optionCode = 6
                    }
                }
            }

        }

        return optionCode
    }

    fun initParametersViewConductor(context: Context, endPointOptions: ArrayList<MaterialCardView>,
                                    parameterLayouts: ArrayList<ConstraintLayout>): Int{
        var endPointCode = 0
        for (entity in entityOptions){
            entity.setOnClickListener {
                when(entity.id){
                    R.id.first_name_patient_endpoint -> {
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(1, parameterLayouts)
                        endPointCode = 1
                    }
                    R.id.last_name_patient_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(2, parameterLayouts)
                        endPointCode = 2
                    }
                    R.id.birth_date_patient_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(3, parameterLayouts)
                        endPointCode = 3
                    }
                    R.id.blood_group_patient_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(4, parameterLayouts)
                        endPointCode = 4
                    }
                    R.id.insurer_patient_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(5, parameterLayouts)
                        endPointCode = 5
                    }
                    R.id.insurer_id_patient_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(6, parameterLayouts)
                        endPointCode = 6
                    }
                    R.id.synopsis_diagnosis_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(7, parameterLayouts)
                        endPointCode = 7
                    }
                    R.id.visit_date_diagnosis_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(8, parameterLayouts)
                        endPointCode = 8
                    }
                    R.id.insurer_id_diagnosis_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(9, parameterLayouts)
                        endPointCode = 9
                    }
                    R.id.level_diagnosis_endpoint ->{
                        toggleEndPointOptions(entity, endPointOptions)
                        toggleEndPointOptionLayouts(10, parameterLayouts)
                        endPointCode = 10
                    }

                }
            }
        }

        return endPointCode
    }



    private fun toggleEntityOptions(selectedOption: MaterialCardView, entityOptions: ArrayList<MaterialCardView>){
        for (entity in entityOptions){
            entity.isChecked = false
            if (entity == selectedOption){
                selectedOption.isChecked = true
            }
        }
    }


    private fun toggleEntityOptionLayouts(optionPosition: Int, optionLayouts: ArrayList<ConstraintLayout>){
        for (option in optionLayouts){
            option.visibility = View.GONE
        }
        optionLayouts[optionPosition].startAnimation(slideUpAnimation)
        optionLayouts[optionPosition].visibility = View.VISIBLE

    }

    private fun toggleEndPointOptions(selectedEndPointOption: MaterialCardView, endPointOptions: ArrayList<MaterialCardView>){
        for (option in endPointOptions){
            option.isChecked = false
            if (option == selectedEndPointOption){
                selectedEndPointOption.isChecked = true
            }
        }
    }


    private fun toggleEndPointOptionLayouts(endPointCode: Int, endPointParamLayouts: ArrayList<ConstraintLayout>){
        for (option in endPointParamLayouts){
            option.visibility = View.GONE
        }
       if (endPointCode == 0 or 1 or 2 or 4){
           endPointParamLayouts[0].startAnimation(slideUpAnimation)
           endPointParamLayouts[0].visibility = View.VISIBLE
       }
        else if (endPointCode == 3 or 5 or 6){
            endPointParamLayouts[1].startAnimation(slideUpAnimation)
            endPointParamLayouts[1].visibility = View.VISIBLE
        }

    }


}