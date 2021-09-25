package iot.empiaurhouse.triage.controller

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.utils.TypeWriterTextView

class QuickPivotController {


    private var editorInputs: ArrayList<EditText> = arrayListOf()
    private var editorIcons: ArrayList<ImageView> = arrayListOf()
    private var entitySelected: Boolean = false
    private var entityOptions: ArrayList<MaterialCardView> = arrayListOf()
    private var optionsLayouts: ArrayList<ConstraintLayout> = arrayListOf()
    private lateinit var timeStream: RadioGroup
    private lateinit var slideUpAnimation : Animation
    private var optionCode: Int? = null
    private var entityCode: Int? = null
    private var endPointCode: Int? = null
    private var practitionerCode: Int? = null


    // Include init reference to respective subview view IDs


    //initView on click return data to next function in control flow

    fun initPivotEditor(pivotID: Int, patientCard: MaterialCardView, diagnosisCard: MaterialCardView,
                        prescriptionCard: MaterialCardView, visitCard: MaterialCardView,
                        pharmaceuticalCard: MaterialCardView, practitionersCard: MaterialCardView,
                        optionLayouts: ArrayList<ConstraintLayout>, editInputs: ArrayList<EditText>, editIcons: ArrayList<ImageView>, timeStreamGroup: RadioGroup,context: Context): Int?{
        optionsLayouts = optionLayouts
        editorInputs = editInputs
        editorIcons = editIcons
        timeStream = timeStreamGroup
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
                                  optionLayouts: ArrayList<ConstraintLayout>,
                                  parameterLayouts: ArrayList<ConstraintLayout>
                                  , parameterSubLayouts: ArrayList<ConstraintLayout>?,
                                  editorButtonView: View, editButton: MaterialButton): Int?{
        if (optionCode != null){
            entityCode = optionCode
        }
        optionsLayouts = optionLayouts
        slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        for (entity in entityOptions){
            entity.setOnClickListener {
                when(entity.id){
                     R.id.patient_entity -> {
                         clearInputLayouts(parameterLayouts)
                         parameterSubLayouts?.get(0)?.visibility = View.GONE
                         toggleEntityOptions(entity, entityOptions)
                         toggleEntityOptionLayouts(0, optionsLayouts)
                         refreshParamsInput(editorInputs, editorIcons, timeStream)
                         hideEditorButton(editorButtonView, editButton)
                         entityCode = 1
                            }

                    R.id.diagnosis_entity -> {
                        clearInputLayouts(parameterLayouts)
                        parameterSubLayouts?.get(0)?.visibility = View.GONE
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(1, optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        entityCode = 2
                    }
                    R.id.prescription_entity -> {
                        clearInputLayouts(parameterLayouts)
                        parameterSubLayouts?.get(0)?.visibility = View.GONE
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(2, optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        entityCode = 3
                    }
                    R.id.visit_entity -> {
                        clearInputLayouts(parameterLayouts)
                        parameterSubLayouts?.get(0)?.visibility = View.GONE
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(3, optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        entityCode = 4
                    }
                    R.id.pharmaceutical_entity -> {
                        clearInputLayouts(parameterLayouts)
                        parameterSubLayouts?.get(0)?.visibility = View.GONE
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(4, optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        entityCode = 5

                    }
                    R.id.practitioner_entity -> {
                        clearInputLayouts(parameterLayouts)
                        parameterSubLayouts?.get(0)?.visibility = View.GONE
                        toggleEntityOptions(entity, entityOptions)
                        toggleEntityOptionLayouts(5, optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        entityCode = 6
                    }
                }
            }

        }

        return entityCode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initPatientParametersViewConductor(endPointOptionsPatients: ArrayList<MaterialCardView>,
                                           parameterLayouts: ArrayList<ConstraintLayout>,
                                           editorButtonView: View, editButton: MaterialButton,
                                           valueParameterType: TypeWriterTextView,
                                           dateParameterType: TypeWriterTextView): Int?{
        editButton.focusable = View.FOCUSABLE_AUTO
        for (endpointPatient in endPointOptionsPatients){
            endpointPatient.setOnClickListener {
                when(endpointPatient.id){
                    R.id.first_name_patient_endpoint -> {
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "First Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 1
                    }
                    R.id.last_name_patient_endpoint ->{
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Last Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 2
                    }
                    R.id.birth_date_patient_endpoint ->{
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Birth Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 3
                    }
                    R.id.blood_group_patient_endpoint ->{
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Blood Group")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 4
                    }
                    R.id.insurer_patient_endpoint ->{
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Insurer")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 5
                    }
                    R.id.insurer_id_patient_endpoint ->{
                        toggleEndPointOptions(endpointPatient, endPointOptionsPatients)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Insurer ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 6
                    }


                }
            }
        }

        return endPointCode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initDiagnosisParametersViewConductor(endPointOptionsDiagnosis: ArrayList<MaterialCardView>,
                                             parameterLayouts: ArrayList<ConstraintLayout>
                                             , editorButtonView: View, editButton: MaterialButton,
                                             valueParameterType: TypeWriterTextView,
                                             dateParameterType: TypeWriterTextView): Int?{

        editButton.focusable = View.FOCUSABLE_AUTO
        for (endpointDiagnosis in endPointOptionsDiagnosis){
            endpointDiagnosis.setOnClickListener {
                when(endpointDiagnosis.id){
                    R.id.synopsis_diagnosis_endpoint -> {
                        toggleEndPointOptions(endpointDiagnosis, endPointOptionsDiagnosis)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Synopsis")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 10
                    }
                    R.id.visit_date_diagnosis_endpoint -> {
                        toggleEndPointOptions(endpointDiagnosis, endPointOptionsDiagnosis)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Diagnosis Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 11
                    }
                    R.id.insurer_id_diagnosis_endpoint -> {
                        toggleEndPointOptions(endpointDiagnosis, endPointOptionsDiagnosis)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Insurer ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 12
                    }
                    R.id.level_diagnosis_endpoint -> {
                        toggleEndPointOptions(endpointDiagnosis, endPointOptionsDiagnosis)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Diagnosis Level")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 13

                    }
                }

            }
        }



            return endPointCode
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initPrescriptionParametersViewConductor(endPointOptionsPrescription: ArrayList<MaterialCardView>,
                                                parameterLayouts: ArrayList<ConstraintLayout>,
                                                editorButtonView: View, editButton: MaterialButton,
                                                valueParameterType: TypeWriterTextView,
                                                dateParameterType: TypeWriterTextView): Int? {
        editButton.focusable = View.FOCUSABLE_AUTO
        for (endpointPrescription in endPointOptionsPrescription){
            endpointPrescription.setOnClickListener {
                when(endpointPrescription.id){
                    R.id.prescription_name_endpoint -> {
                        toggleEndPointOptions(endpointPrescription, endPointOptionsPrescription)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Prescription Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 14
                    }
                    R.id.prescription_prescriber_endpoint -> {
                        toggleEndPointOptions(endpointPrescription, endPointOptionsPrescription)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Prescriber")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 15
                    }
                    R.id.prescription_prescriber_id_endpoint -> {
                        toggleEndPointOptions(endpointPrescription, endPointOptionsPrescription)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Prescriber ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 16
                    }
                    R.id.prescription_insurer_id_endpoint -> {
                        toggleEndPointOptions(endpointPrescription, endPointOptionsPrescription)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Insurer ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 17
                    }
                    R.id.prescription_date_endpoint -> {
                        toggleEndPointOptions(endpointPrescription, endPointOptionsPrescription)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Prescription Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        endPointCode = 18

                    }

                    }
            }
        }

        return endPointCode
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initVisitParametersViewConductor(context: Context, endPointOptionsVisit: ArrayList<MaterialCardView>,
                                         parameterLayouts: ArrayList<ConstraintLayout>,
                                         editorButtonView: View, editButton: MaterialButton,
                                         valueParameterType: TypeWriterTextView,
                                         dateParameterType: TypeWriterTextView): Int? {
        editButton.focusable = View.FOCUSABLE_AUTO
        for (endpointVisit in endPointOptionsVisit){
             endpointVisit.setOnClickListener {
                when(endpointVisit.id) {
                    R.id.visit_host_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Host Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 19
                    }
                    R.id.visit_host_id_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Host ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 20

                    }
                    R.id.visit_time_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Visit Time")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 21
                    }
                    R.id.visit_description_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Visit Description")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 22
                    }
                    R.id.visit_date_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Visit Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 23

                    }
                    R.id.visit_insurer_id_endpoint -> {
                        toggleEndPointOptions(endpointVisit, endPointOptionsVisit)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Insurer ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 24

                    }


                    }
            }
        }


        return endPointCode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initPharmaceuticalParametersViewConductor(endPointOptionsPharmaceutical: ArrayList<MaterialCardView>,
                                                  parameterLayouts: ArrayList<ConstraintLayout>,
                                                  editorButtonView: View,
                                                  editButton: MaterialButton,
                                                  valueParameterType: TypeWriterTextView,
                                                  dateParameterType: TypeWriterTextView): Int? {
        editButton.focusable = View.FOCUSABLE_AUTO
        for (endpointPharmaceutical in endPointOptionsPharmaceutical){
            endpointPharmaceutical.setOnClickListener {
                when(endpointPharmaceutical.id) {
                    R.id.pharmaceutical_brand_name_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Brand Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 25

                    }
                    R.id.pharmaceutical_generic_name_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Generic Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 26

                    }
                    R.id.pharmaceutical_chemical_name_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Chemical Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 27

                    }
                    R.id.pharmaceutical_manufacturer_name_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        setValueEndPointType(valueParameterType, "Manufacturer")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 28

                    }
                    R.id.pharmaceutical_make_date_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Manufacture Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 29

                    }
                    R.id.pharmaceutical_expiry_date_endpoint -> {
                        toggleEndPointOptions(endpointPharmaceutical, endPointOptionsPharmaceutical)
                        toggleEndPointOptionLayouts(2, parameterLayouts, editButton)
                        setDateEndPointType(dateParameterType, "Expiry Date")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        editButton.focusable = View.NOT_FOCUSABLE
                        endPointCode = 30

                    }
                }
            }
        }

        return endPointCode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initPractitionersOptionsView(practitionerEntityOptions: ArrayList<MaterialCardView>,
                                     practitionerOptionLayout: ArrayList<ConstraintLayout>,
                                     endPointParamLayouts: ArrayList<ConstraintLayout>, editorButtonView: View, editButton: MaterialButton): Int?{
        editButton.isFocusable = true
        editButton.focusable = View.FOCUSABLE
        for (entityOption in practitionerEntityOptions){
            entityOption.setOnClickListener {
                when(entityOption.id){
                    R.id.gp_practitioner_option -> {
                        clearInputLayouts(endPointParamLayouts)
                        toggleOptions(entityOption, practitionerEntityOptions)
                        entityOption.isChecked = true
                        toggleEntityOptionLayouts(0, practitionerOptionLayout)
                        hideOptionsLayouts(optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        editButton.clearFocus()
                        practitionerCode = 10
                    }
                    R.id.doctor_practitioner_option -> {
                        clearInputLayouts(endPointParamLayouts)
                        toggleOptions(entityOption, practitionerEntityOptions)
                        entityOption.isChecked = true
                        toggleEntityOptionLayouts(0, practitionerOptionLayout)
                        hideOptionsLayouts(optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        editButton.clearFocus()
                        practitionerCode = 20
                    }
                    R.id.np_practitioner_option -> {
                        clearInputLayouts(endPointParamLayouts)
                        toggleOptions(entityOption, practitionerEntityOptions)
                        entityOption.isChecked = true
                        toggleEntityOptionLayouts(0, practitionerOptionLayout)
                        hideOptionsLayouts(optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        editButton.clearFocus()
                        practitionerCode = 30
                    }
                    R.id.rn_practitioner_option -> {
                        clearInputLayouts(endPointParamLayouts)
                        toggleOptions(entityOption, practitionerEntityOptions)
                        entityOption.isChecked = true
                        toggleEntityOptionLayouts(0, practitionerOptionLayout)
                        hideOptionsLayouts(optionsLayouts)
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        hideEditorButton(editorButtonView, editButton)
                        editButton.clearFocus()
                        practitionerCode = 40
                    }


                    }
                }
            }

     return practitionerCode
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initPractitionerParametersViewConductor(endPointOptionsPractitioners: ArrayList<MaterialCardView>,
                                                parameterLayouts: ArrayList<ConstraintLayout>,
                                                editorButtonView: View, editButton: MaterialButton,
                                                valueParameterType: TypeWriterTextView, inputFocus: EditText): Int?{
        inputFocus.isFocusable = true
        inputFocus.focusable = View.FOCUSABLE
        for (endpointPractitioner in endPointOptionsPractitioners) {
            endpointPractitioner.setOnClickListener {
                when(endpointPractitioner.id){
                    R.id.practitioner_first_name_endpoint -> {
                        toggleOptions(endpointPractitioner, endPointOptionsPractitioners)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        hideOptionsLayouts(optionsLayouts)
                        setValueEndPointType(valueParameterType, "First Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        inputFocus.requestFocus()
                        inputFocus.clearFocus()
                        endPointCode = 7
                    }
                    R.id.practitioner_last_name_endpoint -> {
                        toggleOptions(endpointPractitioner, endPointOptionsPractitioners)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        hideOptionsLayouts(optionsLayouts)
                        setValueEndPointType(valueParameterType, "Last Name")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        inputFocus.requestFocus()
                        inputFocus.clearFocus()
                        endPointCode = 8
                    }
                    R.id.practitioner_id_endpoint -> {
                        toggleOptions(endpointPractitioner, endPointOptionsPractitioners)
                        toggleEndPointOptionLayouts(1, parameterLayouts, editButton)
                        hideOptionsLayouts(optionsLayouts)
                        setValueEndPointType(valueParameterType, "Practitioner ID")
                        refreshParamsInput(editorInputs, editorIcons, timeStream)
                        showEditorButton(editorButtonView, editButton)
                        inputFocus.requestFocus()
                        inputFocus.clearFocus()
                        endPointCode = 9
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

    private fun toggleOptions(selectedOption: MaterialCardView, entityOptions: ArrayList<MaterialCardView>){
        for (entity in entityOptions){
            entity.isChecked = false
            if (entity == selectedOption){
                selectedOption.isChecked = true
            }
        }
    }

    private fun setValueEndPointType(endPointTextView: TypeWriterTextView, endPointTitle: String){
        endPointTextView.text = endPointTitle
        endPointTextView.setCharacterDelay(190)
        endPointTextView.displayTextWithAnimation(endPointTitle)

    }

    private fun setDateEndPointType(endPointTextView: TextView, endPointTitle: String){
        endPointTextView.text = endPointTitle
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

    private fun clearEntityOptions(options: ArrayList<MaterialCardView>){
        for (option in options){
            option.isChecked = false
        }
    }

    private fun showEditorButton(buttonView: View, editButton: MaterialButton){
        buttonView.visibility = View.VISIBLE
        editButton.visibility = View.VISIBLE
    }

    private fun hideEditorButton(buttonView: View, editButton: MaterialButton){
        buttonView.visibility = View.GONE
        editButton.visibility = View.GONE
    }

    private fun toggleEditorButton(buttonView: View, editButton: MaterialButton){
        if (buttonView.visibility == View.VISIBLE || editButton.visibility == View.VISIBLE){
            buttonView.visibility = View.GONE
            editButton.visibility = View.GONE

        }
        else if (buttonView.visibility == View.GONE || editButton.visibility == View.GONE){
            buttonView.visibility = View.VISIBLE
            editButton.visibility = View.VISIBLE

        }

    }

    private fun  hideOptionsLayouts(optionLayouts: ArrayList<ConstraintLayout>){
        for (option in optionLayouts){
            option.visibility = View.GONE
        }

    }


    private fun toggleEndPointOptionLayouts(endPointType: Int, endPointParamLayouts: ArrayList<ConstraintLayout>, editButton: MaterialButton){
        endPointParamLayouts[0].visibility = View.GONE
        endPointParamLayouts[1].visibility = View.GONE
        if (endPointType == 1){
           endPointParamLayouts[0].startAnimation(slideUpAnimation)
           endPointParamLayouts[0].visibility = View.VISIBLE

            }
        else if (endPointType == 2){
            endPointParamLayouts[1].startAnimation(slideUpAnimation)
            endPointParamLayouts[1].visibility = View.VISIBLE
           editButton.isFocusable = false
       }

    }

    private fun refreshParamsInput(editInputs: ArrayList<EditText>, editIcons: ArrayList<ImageView>, timeStream: RadioGroup){
        for (editInput in editInputs){
            editInput.text.clear()
        }

        for (editIcon in editIcons){
            editIcon.setColorFilter(Color.parseColor("#0c204f"))
        }

        timeStream.clearCheck()

    }


    private fun clearInputLayouts(endPointParamLayouts: ArrayList<ConstraintLayout>){
            endPointParamLayouts[0].visibility = View.GONE
            endPointParamLayouts[1].visibility = View.GONE

    }



}


