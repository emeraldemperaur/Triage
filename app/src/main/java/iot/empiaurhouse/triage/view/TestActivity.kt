package iot.empiaurhouse.triage.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.controller.QuickPivotController
import iot.empiaurhouse.triage.databinding.FragmentPivotEditorBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: FragmentPivotEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPivotEditorBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        setContentView(viewSetup)
        val masterControl = QuickPivotController()
        val optionLayouts = arrayListOf<ConstraintLayout>()
        val entityOptions = arrayListOf<MaterialCardView>()
        val patientEntity = binding.entityTypeEditorViewInclude.patientEntity
        val diagnosisEntity = binding.entityTypeEditorViewInclude.diagnosisEntity
        val prescriptionEntity = binding.entityTypeEditorViewInclude.prescriptionEntity
        val visitEntity = binding.entityTypeEditorViewInclude.visitEntity
        val pharmaceuticalEntity = binding.entityTypeEditorViewInclude.pharmaceuticalEntity
        val practitionerEntities = binding.entityTypeEditorViewInclude.practitionerEntity
        entityOptions.add(patientEntity)
        entityOptions.add(diagnosisEntity)
        entityOptions.add(prescriptionEntity)
        entityOptions.add(visitEntity)
        entityOptions.add(pharmaceuticalEntity)
        entityOptions.add(practitionerEntities)
        val patientEndPoints = binding.endpointsEditorViewInclude.patientEndpointsLayout
        val diagnosisEndPoints = binding.diagnosisEndpointsEditorViewInclude.diagnosisEndpointsLayout
        val prescriptionEndPoints = binding.prescriptionEndpointsEditorViewInclude.prescriptionsEndpointsLayout
        val visitEndPoints = binding.visitEndpointsEditorViewInclude.visitEndpointsLayout
        val pharmaceuticalEndPoints = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalEndpointsLayout
        val practitionerEndPoints = binding.practitionersOptionsEditorViewInclude.practitionerOptionsLayout
        optionLayouts.add(patientEndPoints)
        optionLayouts.add(diagnosisEndPoints)
        optionLayouts.add(prescriptionEndPoints)
        optionLayouts.add(visitEndPoints)
        optionLayouts.add(pharmaceuticalEndPoints)
        optionLayouts.add(practitionerEndPoints)
        val paramLayouts = arrayListOf<ConstraintLayout>()
        val valueParam = binding.valueParametersEditorViewInclude.valueParametersEditorView
        val dateParam = binding.dateParametersEditorViewInclude.dateParametersEditorView
        val editButtonView = binding.pivotEditorBottomline
        val editButton = binding.createDataPivotButton
        paramLayouts.add(valueParam)
        paramLayouts.add(dateParam)
        val practitionerEntityEndPoints = arrayListOf<ConstraintLayout>()
        val practitionerLayout = binding.practitionersEndpointsEditorViewInclude.practitionerEndpointsLayout
        practitionerEntityEndPoints.add(practitionerLayout)
        var optionID = masterControl.initPivotEditor(2,patientEntity, diagnosisEntity, prescriptionEntity,
            visitEntity, pharmaceuticalEntity, practitionerEntities, optionLayouts, this)
        optionID = masterControl.initEndPointViewConductor(this, entityOptions, optionLayouts, paramLayouts, practitionerEntityEndPoints, editButtonView, editButton)

        val practitionerEntityOptions = arrayListOf<MaterialCardView>()
        val patientEndPointOptions = arrayListOf<MaterialCardView>()
        val patientLN = binding.endpointsEditorViewInclude.lastNamePatientEndpoint
        val patientFN = binding.endpointsEditorViewInclude.firstNamePatientEndpoint
        val patientDoB = binding.endpointsEditorViewInclude.birthDatePatientEndpoint
        val patientBG = binding.endpointsEditorViewInclude.bloodGroupPatientEndpoint
        val patientInsurer = binding.endpointsEditorViewInclude.insurerPatientEndpoint
        val patientInsurerID = binding.endpointsEditorViewInclude.insurerIdPatientEndpoint
        val diagnosisSynopsis = binding.diagnosisEndpointsEditorViewInclude.synopsisDiagnosisEndpoint
        val diagnosisVisitDate = binding.diagnosisEndpointsEditorViewInclude.visitDateDiagnosisEndpoint
        val diagnosisInsurerID = binding.diagnosisEndpointsEditorViewInclude.insurerIdDiagnosisEndpoint
        val diagnosisLevel = binding.diagnosisEndpointsEditorViewInclude.levelDiagnosisEndpoint
        val generalPractitionerOption = binding.practitionersOptionsEditorViewInclude.gpPractitionerOption
        val doctorPractitionerOption = binding.practitionersOptionsEditorViewInclude.doctorPractitionerOption
        val nursePractitionerOption = binding.practitionersOptionsEditorViewInclude.npPractitionerOption
        val registeredNurseOption = binding.practitionersOptionsEditorViewInclude.rnPractitionerOption
        val practitionerFN = binding.practitionersEndpointsEditorViewInclude.practitionerFirstNameEndpoint
        val practitionerLN = binding.practitionersEndpointsEditorViewInclude.practitionerLastNameEndpoint
        val practitionerID = binding.practitionersEndpointsEditorViewInclude.practitionerIdEndpoint
        practitionerEntityOptions.add(generalPractitionerOption)
        practitionerEntityOptions.add(doctorPractitionerOption)
        practitionerEntityOptions.add(nursePractitionerOption)
        practitionerEntityOptions.add(registeredNurseOption)
        patientEndPointOptions.add(patientLN)
        patientEndPointOptions.add(patientFN)
        patientEndPointOptions.add(patientDoB)
        patientEndPointOptions.add(patientBG)
        patientEndPointOptions.add(patientInsurer)
        patientEndPointOptions.add(patientInsurerID)
        patientEndPointOptions.add(diagnosisSynopsis)
        patientEndPointOptions.add(diagnosisVisitDate)
        patientEndPointOptions.add(diagnosisInsurerID)
        patientEndPointOptions.add(diagnosisLevel)
        val practitionerEndPointOptions = arrayListOf<MaterialCardView>()
        practitionerEndPointOptions.add(practitionerFN)
        practitionerEndPointOptions.add(practitionerLN)
        practitionerEndPointOptions.add(practitionerID)
        val diagnosisEndPointOptions = arrayListOf<MaterialCardView>()
        val diagnosesSynopsis = binding.diagnosisEndpointsEditorViewInclude.synopsisDiagnosisEndpoint
        val diagnosesDate = binding.diagnosisEndpointsEditorViewInclude.visitDateDiagnosisEndpoint
        val diagnosesID = binding.diagnosisEndpointsEditorViewInclude.insurerIdDiagnosisEndpoint
        val diagnosesLevel = binding.diagnosisEndpointsEditorViewInclude.levelDiagnosisEndpoint
        diagnosisEndPointOptions.add(diagnosesSynopsis)
        diagnosisEndPointOptions.add(diagnosesDate)
        diagnosisEndPointOptions.add(diagnosesID)
        diagnosisEndPointOptions.add(diagnosesLevel)
        val prescriptionEndPointOptions = arrayListOf<MaterialCardView>()
        val prescriptionName = binding.prescriptionEndpointsEditorViewInclude.prescriptionNameEndpoint
        val prescriptionPrescriber = binding.prescriptionEndpointsEditorViewInclude.prescriptionPrescriberEndpoint
        val prescriptionPrescriberID = binding.prescriptionEndpointsEditorViewInclude.prescriptionPrescriberIdEndpoint
        val prescriptionInsurerID = binding.prescriptionEndpointsEditorViewInclude.prescriptionInsurerIdEndpoint
        val prescriptionDate = binding.prescriptionEndpointsEditorViewInclude.prescriptionDateEndpoint
        prescriptionEndPointOptions.add(prescriptionName)
        prescriptionEndPointOptions.add(prescriptionPrescriber)
        prescriptionEndPointOptions.add(prescriptionPrescriberID)
        prescriptionEndPointOptions.add(prescriptionInsurerID)
        prescriptionEndPointOptions.add(prescriptionDate)
        val visitEndPointOptions = arrayListOf<MaterialCardView>()
        val visitHost = binding.visitEndpointsEditorViewInclude.visitHostEndpoint
        val visitHostID = binding.visitEndpointsEditorViewInclude.visitHostIdEndpoint
        val visitTime = binding.visitEndpointsEditorViewInclude.visitTimeEndpoint
        val visitDescription = binding.visitEndpointsEditorViewInclude.visitDescriptionEndpoint
        val visitDate = binding.visitEndpointsEditorViewInclude.visitDateEndpoint
        val visitInsurerID = binding.visitEndpointsEditorViewInclude.visitInsurerIdEndpoint
        visitEndPointOptions.add(visitHost)
        visitEndPointOptions.add(visitHostID)
        visitEndPointOptions.add(visitTime)
        visitEndPointOptions.add(visitDescription)
        visitEndPointOptions.add(visitDate)
        visitEndPointOptions.add(visitInsurerID)
        val pharmaceuticalEndPointOptions = arrayListOf<MaterialCardView>()
        val pharmaceuticalBrandName = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalBrandNameEndpoint
        val pharmaceuticalGenericName = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalGenericNameEndpoint
        val pharmaceuticalChemicalName = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalChemicalNameEndpoint
        val pharmaceuticalManufacturerName = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalManufacturerNameEndpoint
        val pharmaceuticalMakeDate = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalMakeDateEndpoint
        val pharmaceuticalExpiryDate = binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalExpiryDateEndpoint
        pharmaceuticalEndPointOptions.add(pharmaceuticalBrandName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalGenericName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalChemicalName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalManufacturerName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalMakeDate)
        pharmaceuticalEndPointOptions.add(pharmaceuticalExpiryDate)
        masterControl.initPatientParametersViewConductor(this, patientEndPointOptions, paramLayouts, editButtonView, editButton)
        masterControl.initPractitionersOptionsView(practitionerEntityOptions, practitionerEntityEndPoints, paramLayouts, editButtonView, editButton)
        masterControl.initPractitionerParametersViewConductor(this, practitionerEndPointOptions, paramLayouts, editButtonView, editButton)
        masterControl.initDiagnosisParametersViewConductor(this, diagnosisEndPointOptions, paramLayouts, editButtonView, editButton)
        masterControl.initPrescriptionParametersViewConductor(this, prescriptionEndPointOptions, paramLayouts, editButtonView, editButton)
        masterControl.initVisitParametersViewConductor(this, visitEndPointOptions, paramLayouts, editButtonView, editButton)
        masterControl.initPharmaceuticalParametersViewConductor(this, pharmaceuticalEndPointOptions, paramLayouts, editButtonView, editButton)


    }
}