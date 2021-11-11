package iot.empiaurhouse.triage.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.InsightModelController
import iot.empiaurhouse.triage.databinding.FragmentInsightModelEditorBinding
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InsightModelEditorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInsightModelEditorBinding
    private lateinit var insightEditorView: View
    private lateinit var histogramButton: MaterialCardView
    private lateinit var pieChartButton: MaterialCardView
    private lateinit var lineChartButton: MaterialCardView
    private lateinit var scatterPlotButton: MaterialCardView
    private lateinit var vistaInfoView: LinearLayout
    private lateinit var vistaInfoBorder: View
    private lateinit var vistaInfoTitle: TextView
    private lateinit var vistaInfoSubTitle: TextView
    private lateinit var vistaModelLayout: LinearLayout
    private lateinit var vistaModelLayoutLine: View
    private lateinit var insightController: InsightModelController
    private lateinit var vistaDescription: TextView
    private lateinit var renderInsightButton: MaterialButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private var vistaCode: Int? = null
    private var entityCode: Int? = null
    private lateinit var patientButton: MaterialCardView
    private lateinit var diagnosesButton: MaterialCardView
    private lateinit var prescriptionButton: MaterialCardView
    private lateinit var visitButton: MaterialCardView
    private lateinit var pharmaceuticalButton: MaterialCardView
    private lateinit var pointOfInterestView: ConstraintLayout
    private lateinit var pointOfIntLine: View
    private lateinit var entityTitle: TextView
    private lateinit var vistaPointOfInterestField: TextInputLayout
    private lateinit var vistaPointOfInterestFieldText: AutoCompleteTextView
    private lateinit var startDateFieldText: TextInputEditText
    private lateinit var startDateField: TextInputLayout
    private lateinit var endDateFieldText: TextInputEditText
    private lateinit var endDateField: TextInputLayout
    private lateinit var aliasFieldText: TextInputEditText
    private lateinit var aliasField: TextInputLayout
    private lateinit var insightFocus: TextView
    private lateinit var insightRangeType: TextView
    private lateinit var thresholdTitle: TextView
    private lateinit var thresholdBorderLine: View
    private lateinit var piThresholdFieldText: TextInputEditText
    private lateinit var piThresholdField: TextInputLayout
    private lateinit var omegaThresholdFieldText: TextInputEditText
    private lateinit var omegaThresholdField: TextInputLayout


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
        return inflater.inflate(R.layout.fragment_insight_model_editor, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInsightModelEditorBinding.bind(view)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        insightEditorView = binding.chironInsightEditorView
        histogramButton = binding.vistaTypeEditorViewInclude.histogramVista
        pieChartButton = binding.vistaTypeEditorViewInclude.pieChartVista
        lineChartButton = binding.vistaTypeEditorViewInclude.lineChartVista
        scatterPlotButton = binding.vistaTypeEditorViewInclude.scatterPlotVista
        vistaInfoView = binding.insightVistaDetailTitleView
        vistaInfoBorder = binding.insightEditorBottomline
        vistaInfoTitle = binding.insightVistaDetailTitleText
        vistaInfoSubTitle = binding.insightVistaDetailTitleSubtext
        vistaDescription = binding.vistaDescription
        vistaModelLayout = binding.vistaDataModelEditorViewInclude.vistaModelEditorLayout
        vistaModelLayoutLine = binding.insightEditorBottomliner
        patientButton = binding.vistaDataModelEditorViewInclude.vistaModelPatientEntity
        diagnosesButton = binding.vistaDataModelEditorViewInclude.vistaModelDiagnosisEntity
        prescriptionButton = binding.vistaDataModelEditorViewInclude.vistaModelPrescriptionEntity
        visitButton = binding.vistaDataModelEditorViewInclude.vistaModelVisitEntity
        pharmaceuticalButton = binding.vistaDataModelEditorViewInclude.vistaModelPharmaceuticalEntity
        pointOfInterestView = binding.vistaDataPointEditorViewInclude.root
        pointOfIntLine = binding.insightEditorBottomlinerBtn
        entityTitle = binding.vistaDataPointEditorViewInclude.vistaPointEditorViewEntityTitle
        vistaPointOfInterestField = binding.vistaDataPointEditorViewInclude.vistaPointOfInterestField
        vistaPointOfInterestFieldText = binding.vistaDataPointEditorViewInclude.vistaPointOfInterestFieldText
        insightFocus = binding.vistaDataPointEditorViewInclude.vistaPointInsightRangeViewFocus
        insightRangeType = binding.vistaDataPointEditorViewInclude.vistaPointInsightRangeViewDateType
        startDateField = binding.vistaDataPointEditorViewInclude.insightRangeStartDateField
        startDateFieldText = binding.vistaDataPointEditorViewInclude.insightRangeStartDateFieldText
        endDateField = binding.vistaDataPointEditorViewInclude.insightRangeEndDateField
        endDateFieldText = binding.vistaDataPointEditorViewInclude.insightRangeEndDateFieldText
        aliasFieldText = binding.insightEditorVistaLabelFieldText
        aliasField = binding.insightEditorVistaLabelField
        renderInsightButton = binding.createInsightButton
        omegaThresholdFieldText = binding.vistaDataPointEditorViewInclude.omegaThresholdFieldText
        omegaThresholdField = binding.vistaDataPointEditorViewInclude.omegaThresholdField
        piThresholdFieldText = binding.vistaDataPointEditorViewInclude.piThresholdFieldText
        piThresholdField = binding.vistaDataPointEditorViewInclude.piThresholdField
        //thresholdTitle = binding.vistaDataPointEditorViewInclude.vistaPointEditorThresholdTitle
        thresholdBorderLine = binding.insightEditorBottomlinerBtn
        insightController = InsightModelController()
        initInsightEditorView()
        onBackPressed()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initInsightEditorView(){
        vistaCode = insightController.initInsightModelEditor(requireContext(), insightEditorView, histogramButton,
            pieChartButton, lineChartButton, scatterPlotButton, vistaInfoView, vistaInfoBorder,
            vistaInfoTitle,vistaInfoSubTitle,vistaModelLayout,vistaModelLayoutLine, vistaDescription, hubUserName, searchButton, toolbarView)
        codeTalker()

        renderInsightButton.setOnClickListener {
            codeTalker()
            if (vistaCode != null){
            val insightCheck = insightController.isValidInsightModel(vistaCode!!, aliasFieldText,
                aliasField, startDateFieldText, startDateField, endDateFieldText, endDateField, piThresholdFieldText,
                piThresholdField, omegaThresholdFieldText, omegaThresholdField, vistaPointOfInterestField, vistaPointOfInterestFieldText)
                if (insightCheck){
                    // Insight Model Build
                    println("Insight Output Result: $insightCheck \n\t-- vistaCode: $vistaCode \n\t-- entityCode: $entityCode")
                }

            }

        }
    }



    override fun onPause() {
        super.onPause()
        if ((searchButton.visibility == View.GONE || hubUserName.visibility == View.GONE)
            && toolbarView.visibility == View.GONE){
            hubUserName.visibility = View.GONE
            searchButton.visibility = View.GONE
            toolbarView.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if ((searchButton.visibility == View.GONE || hubUserName.visibility == View.GONE)
            && toolbarView.visibility == View.GONE){
            hubUserName.visibility = View.GONE
            searchButton.visibility = View.GONE
            toolbarView.visibility = View.GONE
        }
    }


    private fun codeTalker(){
        vistaCode = insightController.initInsightModelEditor(requireContext(), insightEditorView, histogramButton,
            pieChartButton, lineChartButton, scatterPlotButton, vistaInfoView, vistaInfoBorder,
            vistaInfoTitle,vistaInfoSubTitle,vistaModelLayout,vistaModelLayoutLine, vistaDescription, hubUserName, searchButton, toolbarView)

        entityCode = insightController.initOptionsEditorView(patientButton, diagnosesButton, prescriptionButton,
            visitButton, pharmaceuticalButton, pointOfInterestView, pointOfIntLine, entityTitle,
            vistaPointOfInterestField, vistaPointOfInterestFieldText,startDateFieldText, endDateFieldText,
            renderInsightButton, insightFocus, insightRangeType, thresholdBorderLine, omegaThresholdFieldText, omegaThresholdField)

    }


    fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                isEnabled = true
                val insightEditTitle = aliasFieldText.text.toString().trim()
                val controller = findNavController()
                if (insightEditTitle.isEmpty()){
                    controller.navigateUp()
                    isEnabled = false
                }
                else if (insightEditTitle.isNotBlank()){
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Exiting Insight Model")
                    builder.setIcon(R.drawable.ic_baseline_insights_24)
                    builder.setMessage("Are you sure you'd like to discard this unsaved '${insightEditTitle.capitalize(
                        Locale.ROOT)}' insight model?")
                    builder.setPositiveButton("YES") { _, _ ->
                        controller.navigateUp()
                        isEnabled = false
                    }

                    builder.setNegativeButton("NO") { dialog, _ ->
                        dialog.dismiss()
                        isEnabled = true
                    }
                    builder.show()
                }
            }
        })
    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsightModelEditorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}