package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.QuickPivotController
import iot.empiaurhouse.triage.databinding.FragmentPivotEditorBinding
import iot.empiaurhouse.triage.utils.DataPivotValidator
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import java.time.LocalDate


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class PivotEditorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPivotEditorBinding
    private lateinit var pivotEditorButton: MaterialButton
    private lateinit var pivotTitle: View
    private lateinit var pivotAlias: EditText
    private lateinit var pivotAliasLabel: TextView
    private lateinit var pivotValidator: DataPivotValidator
    private var optionCode: Int? = null
    private var practitionerCode: Int? = null
    private var endPointCode: Int? = null
    private var pivotLabel: String? = null
    private var pivotAlphaParam: String? = null
    private var pivotBetaParam: String? = null
    private var epsilonParam: String? = null
    private var chiParam: LocalDate? = null
    private var psiParam: LocalDate? = null
    private lateinit var pivotAlphaParamEdit: EditText
    private lateinit var pivotAlphaParamEditIcon: ImageView
    private lateinit var pivotBetaParamEdit: EditText
    private lateinit var pivotBetaParamEditIcon: ImageView
    private lateinit var pivotEpsilonParamEdit: EditText
    private lateinit var pivotEpsilonParamEditIcon: ImageView
    private lateinit var pivotChiParamPicker: DatePicker
    private lateinit var pivotChiParamPickerIcon: ImageView
    private lateinit var pivotPsiParamPicker: DatePicker
    private lateinit var pivotPsiParamPickerIcon: ImageView
    private lateinit var pivotDateParamPickerView: ConstraintLayout
    private lateinit var pivotValueParamPickerView: ConstraintLayout
    private lateinit var dateTimeStream: RadioGroup
    private lateinit var entityOptions: ArrayList<MaterialCardView>
    private lateinit var optionLayouts: ArrayList<ConstraintLayout>
    private lateinit var paramLayouts: ArrayList<ConstraintLayout>
    private lateinit var masterControl: QuickPivotController
    private lateinit var practitionerEntityEndPoints: ArrayList<ConstraintLayout>
    private lateinit var patientEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var practitionerEntityOptions: ArrayList<MaterialCardView>
    private lateinit var practitionerEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var diagnosisEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var prescriptionEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var visitEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var pharmaceuticalEndPointOptions: ArrayList<MaterialCardView>
    private lateinit var valueParameterType: TypeWriterTextView
    private lateinit var dateParameterType: TypeWriterTextView
    private lateinit var editButtonView: View
    private lateinit var editButton: MaterialButton
    private var editorInputs: ArrayList<EditText> = arrayListOf()
    private var editorIcons: ArrayList<ImageView> = arrayListOf()
    private var pivotIDArg: Int = 0
    private val args: PivotEditorFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pivot_editor, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotEditorBinding.bind(view)
        pivotEditorButton = binding.createDataPivotButton
        pivotAlias = binding.pivotEditorLabel
        pivotTitle = binding.dataPivotTitleFocus
        pivotAliasLabel = binding.pivotEditorInfoLabel
        pivotAlphaParamEdit = binding.valueParametersEditorViewInclude.alphaParameterInput
        pivotAlphaParamEditIcon = binding.valueParametersEditorViewInclude.alphaParameterIcon
        pivotBetaParamEdit = binding.valueParametersEditorViewInclude.betaParameterInput
        pivotBetaParamEditIcon = binding.valueParametersEditorViewInclude.betaParameterIcon
        pivotEpsilonParamEdit = binding.valueParametersEditorViewInclude.epsilonParameterInput
        pivotEpsilonParamEditIcon = binding.valueParametersEditorViewInclude.parameterEpsilonIcon
        pivotChiParamPicker = binding.dateParametersEditorViewInclude.chiParameterInput
        pivotChiParamPickerIcon = binding.dateParametersEditorViewInclude.dateChiParameterIcon
        pivotPsiParamPicker = binding.dateParametersEditorViewInclude.psiParameterInput
        pivotPsiParamPickerIcon = binding.dateParametersEditorViewInclude.datePsiParameterIcon
        dateTimeStream = binding.dateParametersEditorViewInclude.dateTimeStream
        pivotDateParamPickerView = binding.dateParametersEditorViewInclude.dateParametersEditorView
        pivotValueParamPickerView = binding.valueParametersEditorViewInclude.valueParametersEditorView
        pivotIDArg = args.pivotID
        initPivotEditorView()



    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initPivotEditorView(){
        masterControl = QuickPivotController()
        optionLayouts = arrayListOf<ConstraintLayout>()
        entityOptions = arrayListOf<MaterialCardView>()
        val patientEntity = binding.entityTypeEditorViewInclude.patientEntity
        val diagnosisEntity = binding.entityTypeEditorViewInclude.diagnosisEntity
        val prescriptionEntity = binding.entityTypeEditorViewInclude.prescriptionEntity
        val visitEntity = binding.entityTypeEditorViewInclude.visitEntity
        val pharmaceuticalEntity = binding.entityTypeEditorViewInclude.pharmaceuticalEntity
        val practitionerEntities = binding.entityTypeEditorViewInclude.practitionerEntity
        valueParameterType = binding.valueParametersEditorViewInclude.valueParameterEndpointType
        dateParameterType = binding.dateParametersEditorViewInclude.dateParameterEndpointType
        entityOptions.add(patientEntity)
        entityOptions.add(diagnosisEntity)
        entityOptions.add(prescriptionEntity)
        entityOptions.add(visitEntity)
        entityOptions.add(pharmaceuticalEntity)
        entityOptions.add(practitionerEntities)
        val patientEndPoints = binding.endpointsEditorViewInclude.patientEndpointsLayout
        val diagnosisEndPoints =
            binding.diagnosisEndpointsEditorViewInclude.diagnosisEndpointsLayout
        val prescriptionEndPoints =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionsEndpointsLayout
        val visitEndPoints = binding.visitEndpointsEditorViewInclude.visitEndpointsLayout
        val pharmaceuticalEndPoints =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalEndpointsLayout
        val practitionerEndPoints =
            binding.practitionersOptionsEditorViewInclude.practitionerOptionsLayout
        optionLayouts.add(patientEndPoints)
        optionLayouts.add(diagnosisEndPoints)
        optionLayouts.add(prescriptionEndPoints)
        optionLayouts.add(visitEndPoints)
        optionLayouts.add(pharmaceuticalEndPoints)
        optionLayouts.add(practitionerEndPoints)
        paramLayouts = arrayListOf<ConstraintLayout>()
        val valueParam = binding.valueParametersEditorViewInclude.valueParametersEditorView
        val dateParam = binding.dateParametersEditorViewInclude.dateParametersEditorView
        editButtonView = binding.pivotEditorBottomline
        editButton = binding.createDataPivotButton
        paramLayouts.add(valueParam)
        paramLayouts.add(dateParam)
        practitionerEntityEndPoints = arrayListOf<ConstraintLayout>()
        val practitionerLayout =
            binding.practitionersEndpointsEditorViewInclude.practitionerEndpointsLayout
        practitionerEntityEndPoints.add(practitionerLayout)
        editorInputs.add(pivotAlphaParamEdit)
        editorInputs.add(pivotBetaParamEdit)
        editorInputs.add(pivotEpsilonParamEdit)
        editorIcons.add(pivotAlphaParamEditIcon)
        editorIcons.add(pivotBetaParamEditIcon)
        editorIcons.add(pivotEpsilonParamEditIcon)
        editorIcons.add(pivotChiParamPickerIcon)
        editorIcons.add(pivotPsiParamPickerIcon)
        optionCode = masterControl.initPivotEditor(
            pivotIDArg, patientEntity, diagnosisEntity, prescriptionEntity,
            visitEntity, pharmaceuticalEntity, practitionerEntities, optionLayouts,
            editorInputs, editorIcons, dateTimeStream,requireContext()
        )

        practitionerEntityOptions = arrayListOf<MaterialCardView>()
        patientEndPointOptions = arrayListOf<MaterialCardView>()
        val patientLN = binding.endpointsEditorViewInclude.lastNamePatientEndpoint
        val patientFN = binding.endpointsEditorViewInclude.firstNamePatientEndpoint
        val patientDoB = binding.endpointsEditorViewInclude.birthDatePatientEndpoint
        val patientBG = binding.endpointsEditorViewInclude.bloodGroupPatientEndpoint
        val patientInsurer = binding.endpointsEditorViewInclude.insurerPatientEndpoint
        val patientInsurerID = binding.endpointsEditorViewInclude.insurerIdPatientEndpoint
        val diagnosisSynopsis =
            binding.diagnosisEndpointsEditorViewInclude.synopsisDiagnosisEndpoint
        val diagnosisVisitDate =
            binding.diagnosisEndpointsEditorViewInclude.visitDateDiagnosisEndpoint
        val diagnosisInsurerID =
            binding.diagnosisEndpointsEditorViewInclude.insurerIdDiagnosisEndpoint
        val diagnosisLevel = binding.diagnosisEndpointsEditorViewInclude.levelDiagnosisEndpoint
        val generalPractitionerOption =
            binding.practitionersOptionsEditorViewInclude.gpPractitionerOption
        val doctorPractitionerOption =
            binding.practitionersOptionsEditorViewInclude.doctorPractitionerOption
        val nursePractitionerOption =
            binding.practitionersOptionsEditorViewInclude.npPractitionerOption
        val registeredNurseOption =
            binding.practitionersOptionsEditorViewInclude.rnPractitionerOption
        val practitionerFN =
            binding.practitionersEndpointsEditorViewInclude.practitionerFirstNameEndpoint
        val practitionerLN =
            binding.practitionersEndpointsEditorViewInclude.practitionerLastNameEndpoint
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
        practitionerEndPointOptions = arrayListOf<MaterialCardView>()
        practitionerEndPointOptions.add(practitionerFN)
        practitionerEndPointOptions.add(practitionerLN)
        practitionerEndPointOptions.add(practitionerID)
        diagnosisEndPointOptions = arrayListOf<MaterialCardView>()
        val diagnosesSynopsis =
            binding.diagnosisEndpointsEditorViewInclude.synopsisDiagnosisEndpoint
        val diagnosesDate = binding.diagnosisEndpointsEditorViewInclude.visitDateDiagnosisEndpoint
        val diagnosesID = binding.diagnosisEndpointsEditorViewInclude.insurerIdDiagnosisEndpoint
        val diagnosesLevel = binding.diagnosisEndpointsEditorViewInclude.levelDiagnosisEndpoint
        diagnosisEndPointOptions.add(diagnosesSynopsis)
        diagnosisEndPointOptions.add(diagnosesDate)
        diagnosisEndPointOptions.add(diagnosesID)
        diagnosisEndPointOptions.add(diagnosesLevel)
        prescriptionEndPointOptions = arrayListOf<MaterialCardView>()
        val prescriptionName =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionNameEndpoint
        val prescriptionPrescriber =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionPrescriberEndpoint
        val prescriptionPrescriberID =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionPrescriberIdEndpoint
        val prescriptionInsurerID =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionInsurerIdEndpoint
        val prescriptionDate =
            binding.prescriptionEndpointsEditorViewInclude.prescriptionDateEndpoint
        prescriptionEndPointOptions.add(prescriptionName)
        prescriptionEndPointOptions.add(prescriptionPrescriber)
        prescriptionEndPointOptions.add(prescriptionPrescriberID)
        prescriptionEndPointOptions.add(prescriptionInsurerID)
        prescriptionEndPointOptions.add(prescriptionDate)
        visitEndPointOptions = arrayListOf<MaterialCardView>()
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
        pharmaceuticalEndPointOptions = arrayListOf<MaterialCardView>()
        val pharmaceuticalBrandName =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalBrandNameEndpoint
        val pharmaceuticalGenericName =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalGenericNameEndpoint
        val pharmaceuticalChemicalName =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalChemicalNameEndpoint
        val pharmaceuticalManufacturerName =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalManufacturerNameEndpoint
        val pharmaceuticalMakeDate =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalMakeDateEndpoint
        val pharmaceuticalExpiryDate =
            binding.pharmaceuticalsEndpointsEditorViewInclude.pharmaceuticalExpiryDateEndpoint
        pharmaceuticalEndPointOptions.add(pharmaceuticalBrandName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalGenericName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalChemicalName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalManufacturerName)
        pharmaceuticalEndPointOptions.add(pharmaceuticalMakeDate)
        pharmaceuticalEndPointOptions.add(pharmaceuticalExpiryDate)
        optionCode = masterControl.initEndPointViewConductor(
            requireContext(),
            entityOptions,
            optionLayouts,
            paramLayouts,
            practitionerEntityEndPoints,
            editButtonView,
            editButton
        )

        codeTalker()

        pivotEditorButton.setOnClickListener {

            codeTalker()
            pivotEditorButton.clearFocus()
            pivotValidator = DataPivotValidator()
            val pivotTitled = pivotValidator.validateAlias(requireContext(), pivotValueParamPickerView,
                pivotAlias, pivotAliasLabel, pivotTitle, pivotEditorButton)

            if (pivotTitled){
                pivotLabel = pivotAlias.text.toString()
                val valueParamValid = pivotValidator.validateValueParameter(requireContext(),
                    pivotValueParamPickerView, pivotAlphaParamEdit, pivotAlphaParamEditIcon, pivotBetaParamEdit,
                    pivotBetaParamEditIcon, pivotEpsilonParamEdit,pivotEpsilonParamEditIcon)
                val dateParamValid = pivotValidator.validateDateParameter(requireContext(),
                    pivotDateParamPickerView, dateTimeStream, pivotChiParamPicker,
                    pivotChiParamPickerIcon, pivotPsiParamPicker, pivotPsiParamPickerIcon)

                if (pivotValueParamPickerView.visibility == View.VISIBLE && valueParamValid > 0){
                    println("Found pivotAlias: ${pivotAlias.text}")
                    println("Found pivotValueParamCode: $valueParamValid")
                    println("Found pivotAlphaParam: ${pivotAlphaParamEdit.text}")
                    println("Found pivotBetaParam: ${pivotBetaParamEdit.text}")
                    println("Found pivotEpsilonParam: ${pivotEpsilonParamEdit.text}")
                    println("Detected optionCode: ${masterControl.fetchOptionCode()}")
                    println("Detected entityCode: ${masterControl.fetchEntityCode()}")
                    println("Detected practitionerCode: $practitionerCode")
                    println("Detected endPointCode: $endPointCode")
                }
                else if(pivotValidator.timeStreamSelected(dateTimeStream) == true && dateParamValid){
                    println("Found pivotAlias: ${pivotAlias.text}")
                    println("Detected optionCode: ${masterControl.fetchOptionCode()}")
                    println("Detected entityCode: ${masterControl.fetchEntityCode()}")
                    println("Detected practitionerCode: $practitionerCode")
                    println("Detected endPointCode: $endPointCode")
                    println("Detected timeStreamCode: ${pivotValidator.fetchTimeFlowCode()}")
                    println("Found pivotChiParam Date: ${pivotChiParamPicker.dayOfMonth} - ${pivotChiParamPicker.month + 1} - ${pivotChiParamPicker.year}")
                    println("Found pivotPsiParam Date: ${pivotPsiParamPicker.dayOfMonth} - ${pivotPsiParamPicker.month + 1} - ${pivotPsiParamPicker.year}")
                    println("Found pivotTimeStamp: ${pivotValidator.pivotTimeStamp()}")

                }

            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun codeTalker(){
        optionCode = masterControl.initEndPointViewConductor(
            requireContext(),
            entityOptions,
            optionLayouts,
            paramLayouts,
            practitionerEntityEndPoints,
            editButtonView,
            editButton
        )


        endPointCode = masterControl.initPatientParametersViewConductor(
            patientEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            dateParameterType
        )
        practitionerCode = masterControl.initPractitionersOptionsView(
            practitionerEntityOptions,
            practitionerEntityEndPoints,
            paramLayouts,
            editButtonView,
            editButton
        )
        endPointCode = masterControl.initPractitionerParametersViewConductor(
            practitionerEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            pivotEpsilonParamEdit
        )
        endPointCode = masterControl.initDiagnosisParametersViewConductor(
            diagnosisEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            dateParameterType
        )
        endPointCode = masterControl.initPrescriptionParametersViewConductor(
            prescriptionEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            dateParameterType
        )
        endPointCode = masterControl.initVisitParametersViewConductor(
            visitEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            dateParameterType
        )
        endPointCode = masterControl.initPharmaceuticalParametersViewConductor(
            pharmaceuticalEndPointOptions,
            paramLayouts,
            editButtonView,
            editButton,
            valueParameterType,
            dateParameterType
        )



    }





    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PivotEditorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}