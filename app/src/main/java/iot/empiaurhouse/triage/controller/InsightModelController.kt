package iot.empiaurhouse.triage.controller

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.utils.RecordEditValidator
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class InsightModelController {

    private var vistaOptions: ArrayList<MaterialCardView> = arrayListOf()
    private lateinit var vistaInfo: LinearLayout
    private lateinit var vistaInfoLine: View
    private lateinit var vistaInfoText: TextView
    private lateinit var vistaInfoSubText: TextView
    private lateinit var vistaDescribe: TextView
    private lateinit var modelLayout: LinearLayout
    private lateinit var modelLayoutLine: View
    private lateinit var omegaThresholdEditText: TextInputEditText
    private lateinit var omegaThresholdEdit: TextInputLayout
    private lateinit var modelType: TextView
    private lateinit var insightPointLayout: ConstraintLayout
    private lateinit var insightPointLayoutLine: View
    private lateinit var renderButton: MaterialButton
    private lateinit var slideUpAnimation : Animation
    private lateinit var slideDownAnimation : Animation
    private lateinit var editorViewContext: View
    private var modelOptions: ArrayList<MaterialCardView> = arrayListOf()
    private  var vistaCode: Int? = null
    private  var modelCode: Int? = null
    private lateinit var controlContext: Context
    private lateinit var hubUserNameTV: TextView
    private lateinit var searchBtn: FloatingActionButton
    private lateinit var toolbar: CollapsingToolbarLayout
    private var endPointList: ArrayList<String> = arrayListOf()
    private lateinit var slideRightAnimation : Animation
    private lateinit var fadeInLabelAnimation : Animation
    private lateinit var fadeInModelTitleAnimation : Animation
    private lateinit var fadeInModelTextAnimation : Animation
    private lateinit var fadeInEndPointTitleAnimation : Animation
    private lateinit var fadeInEndPointTextAnimation : Animation
    private lateinit var fadeInVistaTypeTitleAnimation : Animation
    private lateinit var pharmaceuticalsButton: MaterialCardView




    fun initInsightModelEditor(context: Context, insightEditorView: View,
                               histogramButton: MaterialCardView, pieChartButton: MaterialCardView,
                               lineChartButton: MaterialCardView, scatterPlotButton: MaterialCardView,
                               vistaInfoView: LinearLayout, vistaInfoBorder: View,
                               vistaInfoTitle: TextView, vistaInfoSubTitle: TextView,
                               vistaModelLayout: LinearLayout, vistaModelLayoutLine: View,
                               vistaDescription: TextView, hubUserName: TextView,
                               searchButton: FloatingActionButton, toolbarView: CollapsingToolbarLayout): Int?{

        vistaInfo = vistaInfoView
        controlContext = context
        vistaInfoLine = vistaInfoBorder
        editorViewContext = insightEditorView
        vistaInfoText = vistaInfoTitle
        vistaInfoSubText = vistaInfoSubTitle
        modelLayout = vistaModelLayout
        modelLayoutLine = vistaModelLayoutLine
        vistaDescribe = vistaDescription
        hubUserNameTV = hubUserName
        searchBtn = searchButton
        toolbar = toolbarView
        slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        vistaOptions.add(histogramButton)
        vistaOptions.add(pieChartButton)
        vistaOptions.add(lineChartButton)
        vistaOptions.add(scatterPlotButton)
        for (vista in vistaOptions){
            vista.setOnClickListener {
                hubUserNameTV.visibility = View.GONE
                searchBtn.visibility = View.GONE
                toolbar.visibility = View.GONE
                modelLayout.requestFocus()
                when(vista.id){
                    R.id.histogram_vista ->{
                        vistaCode = 1
                        toggleEntityOptions(vista, vistaOptions)
                        toggleEntityOptionLayouts(vistaCode!!)
                        initOmegaInput(vistaCode!!, omegaThresholdEditText, omegaThresholdEdit)

                    }
                    R.id.pie_chart_vista ->{
                        vistaCode = 2
                        toggleEntityOptions(vista, vistaOptions)
                        toggleEntityOptionLayouts(vistaCode!!)
                        initOmegaInput(vistaCode!!, omegaThresholdEditText, omegaThresholdEdit)

                    }
                    R.id.line_chart_vista ->{
                        vistaCode = 3
                        toggleEntityOptions(vista, vistaOptions)
                        disableEntities()
                        toggleEntityOptionLayouts(vistaCode!!)
                        initOmegaInput(vistaCode!!, omegaThresholdEditText, omegaThresholdEdit)


                    }
                    R.id.scatter_plot_vista ->{
                        vistaCode = 4
                        toggleEntityOptions(vista, vistaOptions)
                        disableEntities()
                        toggleEntityOptionLayouts(vistaCode!!)
                        initOmegaInput(vistaCode!!, omegaThresholdEditText, omegaThresholdEdit)

                    }
                }

            }

        }
        vistaInfo.clearAnimation()
        vistaInfoLine.clearAnimation()
        modelLayout.clearAnimation()
        return vistaCode
    }

    fun initOptionsEditorView(patientButton: MaterialCardView, diagnosesButton: MaterialCardView,
                              prescriptionButton: MaterialCardView, visitButton: MaterialCardView,
                              pharmaceuticalButton: MaterialCardView, pointOfInterestView: ConstraintLayout,
                              pointOfIntLine: View, entityTitle: TextView, vistaPointOfInterestField: TextInputLayout,
                              vistaPointOfInterestFieldText: AutoCompleteTextView, startDateField: TextInputEditText,
                              endDateField: TextInputEditText, renderButton: MaterialButton,
                              insightFocus: TextView, insightRangeType:TextView, thresholdLine: View,
                              omegaThresholdFieldText: TextInputEditText, omegaThresholdField: TextInputLayout): Int?{

        omegaThresholdEditText = omegaThresholdFieldText
        omegaThresholdEdit = omegaThresholdField
        modelOptions.add(patientButton)
        modelOptions.add(diagnosesButton)
        modelOptions.add(prescriptionButton)
        modelOptions.add(visitButton)
        modelOptions.add(pharmaceuticalButton)
        pharmaceuticalsButton = pharmaceuticalButton
        for (model in modelOptions){
            model.setOnClickListener {
                pointOfInterestView.visibility = View.GONE
                pointOfIntLine.visibility = View.GONE
                renderButton.visibility = View.GONE
                pointOfInterestView.startAnimation(slideUpAnimation)
                pointOfIntLine.startAnimation(slideUpAnimation)
                renderButton.startAnimation(slideUpAnimation)
                pointOfInterestView.visibility = View.VISIBLE
                pointOfIntLine.visibility = View.VISIBLE
                renderButton.visibility = View.VISIBLE
                if (!endPointList.isNullOrEmpty()){
                    endPointList.clear()
                }
                when(model.id){
                    R.id.vista_model_patient_entity ->{
                        modelCode = 1
                        toggleEntityOptions(model, modelOptions)
                        entityTitle.text = controlContext.getString(R.string.patient)
                        insightRangeType.text = controlContext.getString(R.string.birth_date)
                        initOmegaInput(vistaCode!!, omegaThresholdFieldText, omegaThresholdField)
                        endPointList.addAll(listOf("First Name", "Last Name", "Blood Group", "Insurer"))
                        thresholdLine.requestFocus()

                    }
                    R.id.vista_model_diagnosis_entity ->{
                        modelCode = 2
                        toggleEntityOptions(model, modelOptions)
                        entityTitle.text = controlContext.getString(R.string.diagnosis_uc)
                        insightRangeType.text = controlContext.getString(R.string.diagnosis_date)
                        initOmegaInput(vistaCode!!, omegaThresholdFieldText, omegaThresholdField)
                        endPointList.addAll(listOf("Synopsis", "Diagnosis Details", "Level"))
                        thresholdLine.requestFocus()

                    }
                    R.id.vista_model_prescription_entity ->{
                        modelCode = 3
                        toggleEntityOptions(model, modelOptions)
                        entityTitle.text = controlContext.getString(R.string.prescription)
                        insightRangeType.text = controlContext.getString(R.string.prescription_date_sc)
                        initOmegaInput(vistaCode!!, omegaThresholdFieldText, omegaThresholdField)
                        endPointList.addAll(listOf("Rx Name", "Prescriber", "Prescriber ID", "Patient Name"))
                        thresholdLine.requestFocus()

                    }
                    R.id.vista_model_visit_entity ->{
                        modelCode = 4
                        toggleEntityOptions(model, modelOptions)
                        entityTitle.text = controlContext.getString(R.string.visit)
                        insightRangeType.text = controlContext.getString(R.string.visit_date)
                        initOmegaInput(vistaCode!!, omegaThresholdFieldText, omegaThresholdField)
                        endPointList.addAll(listOf("Host", "Host ID", "Visit Time", "Description", "Patient Name"))
                        thresholdLine.requestFocus()

                    }
                    R.id.vista_model_pharmaceutical_entity ->{
                        modelCode = 5
                        toggleEntityOptions(model, modelOptions)
                        entityTitle.text = controlContext.getString(R.string.pharmaceutical)
                        (vistaPointOfInterestField.editText as? AutoCompleteTextView)?.setText("", false)
                        val holderText = controlContext.getString(R.string.expire_date)
                        initOmegaInput(vistaCode!!, omegaThresholdFieldText, omegaThresholdField)
                        insightRangeType.text = holderText
                        endPointList.addAll(listOf("Brand Name", "Generic Name", "Chemical Name", "Manufacturer"))
                        thresholdLine.requestFocus()

                    }
                }
                val adapter = ArrayAdapter(controlContext, R.layout.blood_group_item, endPointList)
                (vistaPointOfInterestField.editText as? AutoCompleteTextView)?.setText("", false)
                (vistaPointOfInterestField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                startDateField.inputType = InputType.TYPE_NULL
                startDateField.setTextIsSelectable(false)
                startDateField.isFocusable = false
                endDateField.inputType = InputType.TYPE_NULL
                endDateField.setTextIsSelectable(false)
                endDateField.isFocusable = false
                startDateField.setOnClickListener {
                    val cal = Calendar.getInstance()
                    var y = cal.get(Calendar.YEAR)
                    var m = cal.get(Calendar.MONTH)
                    var d = cal.get(Calendar.DAY_OF_MONTH)
                    if (!startDateField.text.isNullOrBlank()){
                        val startDate = startDateField.text!!.split("-")
                        y = startDate[0].toInt()
                        m = startDate[1].toInt() - 1
                        d = startDate[2].toInt()
                    }


                    val datePickerDialog: DatePickerDialog = DatePickerDialog(editorViewContext.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val monthInt = monthOfYear + 1
                        var monthStr = monthInt.toString()
                        var dayInt = dayOfMonth.toString()
                        if (dayOfMonth < 10){
                            dayInt = "0$dayInt"
                        }
                        if (monthInt < 10){
                            monthStr = "0$monthInt"
                        }
                        val datePicked = "$year-$monthStr-$dayInt"
                        startDateField.setText(datePicked)
                    }, y, m, d)

                    datePickerDialog.show()
                }
                endDateField.setOnClickListener {
                    val cal = Calendar.getInstance()
                    var y = cal.get(Calendar.YEAR)
                    var m = cal.get(Calendar.MONTH)
                    var d = cal.get(Calendar.DAY_OF_MONTH)
                    if (!endDateField.text.isNullOrBlank()){
                        val endDate = endDateField.text!!.split("-")
                        y = endDate[0].toInt()
                        m = endDate[1].toInt() - 1
                        d = endDate[2].toInt()
                    }


                    val datePickerDialog: DatePickerDialog = DatePickerDialog(editorViewContext.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val monthInt = monthOfYear + 1
                        var monthStr = monthInt.toString()
                        var dayInt = dayOfMonth.toString()
                        if (dayOfMonth < 10){
                            dayInt = "0$dayInt"
                        }
                        if (monthInt < 10){
                            monthStr = "0$monthInt"
                        }
                        val datePicked = "$year-$monthStr-$dayInt"
                        endDateField.setText(datePicked)
                    }, y, m, d)

                    datePickerDialog.show()
                }

            }

            //vistaInfo.clearAnimation()
            //vistaInfoLine.clearAnimation()
            //modelLayout.clearAnimation()
            pointOfInterestView.clearAnimation()
            pointOfIntLine.clearAnimation()
            renderButton.clearAnimation()

        }

        return modelCode
    }

    private fun initOmegaInput(vistaCode: Int, omegaThresholdFieldText: TextInputEditText, omegaThresholdField: TextInputLayout){
        if (omegaThresholdFieldText != null) {
            when (vistaCode) {
                1 -> {
                    omegaThresholdFieldText.inputType = InputType.TYPE_NULL
                    omegaThresholdFieldText.setTextIsSelectable(false)
                    omegaThresholdFieldText.isFocusable = false
                    omegaThresholdFieldText.isEnabled = false
                    omegaThresholdFieldText.visibility = View.GONE
                    omegaThresholdField.visibility = View.GONE
                }
                2 -> {
                    omegaThresholdFieldText.inputType = InputType.TYPE_NULL
                    omegaThresholdFieldText.setTextIsSelectable(false)
                    omegaThresholdFieldText.isFocusable = false
                    omegaThresholdFieldText.isEnabled = false
                    omegaThresholdFieldText.visibility = View.GONE
                    omegaThresholdField.visibility = View.GONE

                }
                3 -> {
                    omegaThresholdFieldText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    omegaThresholdFieldText.setTextIsSelectable(true)
                    omegaThresholdFieldText.isFocusable = true
                    omegaThresholdFieldText.isEnabled = true
                    omegaThresholdFieldText.visibility = View.VISIBLE
                    omegaThresholdField.visibility = View.VISIBLE

                }
                4 -> {
                    omegaThresholdFieldText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    omegaThresholdFieldText.setTextIsSelectable(true)
                    omegaThresholdFieldText.isFocusable = true
                    omegaThresholdFieldText.isEnabled = true
                    omegaThresholdFieldText.visibility = View.VISIBLE
                    omegaThresholdField.visibility = View.VISIBLE

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isValidInsightModel(vistaCode: Int, insightTitle: TextInputEditText, insightTitleField: TextInputLayout,
                            insightDateRangeS: TextInputEditText, insightDateRangeSField: TextInputLayout,
                            insightDateRangeE: TextInputEditText, insightDateRangeEField: TextInputLayout,
                            piThresholdText: TextInputEditText, piThresholdTextField: TextInputLayout,
                            omegaThresholdText: TextInputEditText, omegaThresholdTextField: TextInputLayout,
                            vistaPointOfInterestField: TextInputLayout, vistaPointOfInterestFieldText: AutoCompleteTextView): Boolean{
        val recordEditCheck = RecordEditValidator()
        recordEditCheck.initValidator(editorViewContext)
        var isInsight = false
        if (vistaCode == 1 || vistaCode == 2) {
            isInsight = (recordEditCheck.isValidIText(insightTitle, insightTitleField, "Alias")
                    && recordEditCheck.isValidPOIText(vistaPointOfInterestFieldText, vistaPointOfInterestField, "Point of Interest")
                    && recordEditCheck.isValidIText(insightDateRangeS, insightDateRangeSField, "Range Start")
                    && recordEditCheck.isValidIText(insightDateRangeE, insightDateRangeEField, "Range End")
                    && recordEditCheck.isValidIText(piThresholdText, piThresholdTextField, "Pi Threshold"))
            if (isInsight){
                isInsight = recordEditCheck.isValidITimeText(insightDateRangeS, insightDateRangeSField, insightDateRangeE, insightDateRangeEField)
            }
        }else if (vistaCode == 3 || vistaCode == 4){
            isInsight = (recordEditCheck.isValidIText(insightTitle, insightTitleField, "Alias")
                    && recordEditCheck.isValidPOIText(vistaPointOfInterestFieldText, vistaPointOfInterestField, "Point of Interest")
                    && recordEditCheck.isValidIText(insightDateRangeS, insightDateRangeSField, "Range Start")
                    && recordEditCheck.isValidIText(insightDateRangeE, insightDateRangeEField, "Range End")
                    && recordEditCheck.isValidIText(piThresholdText, piThresholdTextField, "Pi Threshold")
                    && recordEditCheck.isValidIText(omegaThresholdText, omegaThresholdTextField, "Omega Threshold"))
            if (isInsight){
                isInsight = recordEditCheck.isValidITimeText(insightDateRangeS, insightDateRangeSField, insightDateRangeE, insightDateRangeEField)
            }
        }
        return isInsight
    }


    private fun toggleEntityOptionLayouts(optionPosition: Int){

        vistaInfo.startAnimation(slideUpAnimation)
        vistaInfoLine.startAnimation(slideUpAnimation)
        modelLayout.startAnimation(slideUpAnimation)
        vistaInfo.visibility = View.VISIBLE
        vistaInfoLine.visibility = View.VISIBLE
        modelLayout.visibility = View.VISIBLE
        modelLayoutLine.visibility = View.VISIBLE
        modelLayoutLine.requestFocus()
        var infoText = ""
        var infoSubText = ""
        var vistaDescribeText = ""
        when(optionPosition){
            1 ->{
                infoText = "Histogram"
                infoSubText = "Chart"
                vistaDescribeText = controlContext.getString(R.string.histogram_info)
                vistaDescribe.text = vistaDescribeText
                vistaInfoText.text = infoText
                vistaInfoSubText.text = infoSubText
                vistaInfoText.visibility = View.VISIBLE
                vistaInfoSubText.visibility = View.VISIBLE


            }
            2 ->{
                infoText = "Pie"
                infoSubText = "Chart"
                vistaDescribeText = controlContext.getString(R.string.pie_chart_info)
                vistaDescribe.text = vistaDescribeText
                vistaInfoText.text = infoText
                vistaInfoSubText.text = infoSubText
                vistaInfoText.visibility = View.VISIBLE
                vistaInfoSubText.visibility = View.VISIBLE

            }
            3 ->{
                infoText = "Line"
                infoSubText = "Chart"
                vistaDescribeText = controlContext.getString(R.string.line_chart_info)
                vistaDescribe.text = vistaDescribeText
                vistaInfoText.text = infoText
                vistaInfoSubText.text = infoSubText
                vistaInfoText.visibility = View.VISIBLE
                vistaInfoSubText.visibility = View.VISIBLE

            }
            4 ->{
                infoText = "Scatter"
                infoSubText = "Plot"
                vistaDescribeText = controlContext.getString(R.string.scatter_plot_info)
                vistaDescribe.text = vistaDescribeText
                vistaInfoText.text = infoText
                vistaInfoSubText.text = infoSubText
                vistaInfoText.visibility = View.VISIBLE
                vistaInfoSubText.visibility = View.VISIBLE
            }

        }

    }

    fun fetchInsightEntityTitle(entityCode: Int): String{
        var title = ""
        when(entityCode){
            1 ->{
                title = "Patient"
            }
            2 ->{
                title = "Diagnosis"
            }
            3 ->{
                title = "Prescription"
            }
            4 ->{
                title = "Visit"
            }
            5 ->{
                title = "Pharmaceutical"
            }

        }
        return title
    }

    fun fetchInsightTypeTitle(vistaCode: Int): String{
        var vistaType= ""
        when(vistaCode){
            1 ->{
                vistaType = "Histogram"
            }
            2 ->{
                vistaType = "Pie Chart"
            }
            3 ->{
                vistaType = "Line Chart"
            }
            4 ->{
                vistaType = "Scatter Plot"
            }
        }
        return vistaType
    }

    fun fetchInsightIcon(vistaCode: Int): Int{
        var icon = R.drawable.histogram_chart
        when(vistaCode){
            1 ->{
                icon = R.drawable.histogram_chart
            }
            2 ->{
                icon = R.drawable.pie_chart
            }
            3 ->{
                icon = R.drawable.line_charts_icon
            }
            4 ->{
                icon = R.drawable.plot_charts_icon
            }


        }
        return icon
    }

    fun fetchRangeBaseTitle(entityCode: Int): String{
        var rangeBaseTitle = ""
        when(entityCode){
            1 ->{
                rangeBaseTitle = "Birth Date"
            }
            2 ->{
                rangeBaseTitle = "Diagnosis Date"
            }
            3 ->{
                rangeBaseTitle = "Prescription Date"
            }
            4 ->{
                rangeBaseTitle = "Visit Date"
            }
            5 ->{
                rangeBaseTitle = "Expiry Date"
            }
        }
        return rangeBaseTitle
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insightObjectDateFormat(stringDate: String?): String {
        val dateObject = LocalDate.parse(stringDate)
        val formatter = DateTimeFormatter.ofPattern("dd, MMMM yyyy")
        return dateObject.format(formatter)
    }


    private fun toggleEntityOptions(selectedOption: MaterialCardView, entityOptions: ArrayList<MaterialCardView>){
        for (entity in entityOptions){
            entity.isChecked = false
            if (entity == selectedOption){
                selectedOption.isChecked = true
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initInsightDialog(context: Context,
                        insightModel: InsightModel,
                        triageBot: ImageView, insightDialogView: MaterialCardView,
                        renderingText: TypeWriterTextView, insightLabel: TextView,
                        dataModelTitle: TextView, dataModelText: TextView,
                        endPointTitle: TextView, endPointText: TextView,
                        insightTypeTitle: TextView, insightTypeText: TextView,
                        insightRangeTitle: TextView, insightRangeText: TextView,
                          piThresholdParamText: TextView,
                        piThresholdParamTitle: TextView, omegaThresholdParamText: TextView,
                          omegaThresholdParamTitle: TextView,
                        renderProgress: ProgressBar
    ): Boolean{

        var isFinished = false
        triageBot.visibility = View.GONE
        slideRightAnimation = AnimationUtils.loadAnimation(context, R.anim.pull_in_left)
        fadeInLabelAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInModelTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInModelTextAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInEndPointTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInEndPointTextAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInVistaTypeTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)


        triageBot.startAnimation(slideRightAnimation)
        triageBot.visibility = View.VISIBLE
        slideRightAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                insightLabel.startAnimation(fadeInLabelAnimation)
                insightLabel.text = insightModel.alias.capitalize(Locale.ROOT)
                insightLabel.visibility = View.VISIBLE
                triageBot.clearAnimation()


            }

            override fun onAnimationStart(animation: Animation?) {
                insightDialogView.visibility = View.VISIBLE
                renderingText.visibility = View.VISIBLE
                renderingText.setCharacterDelay(96)
                renderingText.displayTextWithAnimation("Rendering Data...")

            }
        })

        fadeInLabelAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                dataModelTitle.startAnimation(fadeInModelTitleAnimation)
                dataModelTitle.visibility = View.VISIBLE
                dataModelText.text = fetchInsightEntityTitle(insightModel.entityCode!!)
                dataModelText.startAnimation(fadeInModelTextAnimation)
                dataModelText.visibility = View.VISIBLE
                endPointTitle.startAnimation(fadeInEndPointTitleAnimation)
                endPointTitle.visibility = View.VISIBLE
                endPointText.text = insightModel.pointOfInterest
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
                insightTypeTitle.startAnimation(fadeInVistaTypeTitleAnimation)
                insightTypeText.text = fetchInsightTypeTitle(insightModel.vistaCode!!)
                insightTypeText.startAnimation(fadeInEndPointTitleAnimation)
                insightTypeTitle.visibility = View.VISIBLE
                insightTypeText.visibility = View.VISIBLE


            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

        fadeInVistaTypeTitleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onAnimationEnd(animation: Animation?) {

                if (insightModel.vistaCode == 1 || insightModel.vistaCode == 2){
                    insightRangeTitle.visibility = View.VISIBLE
                    val insightDateRange = "${insightObjectDateFormat(insightModel.rangeStartDate)}  -\n${insightObjectDateFormat(insightModel.rangeEndDate)}"
                    insightRangeText.text = insightDateRange
                    insightRangeText.visibility = View.VISIBLE
                    piThresholdParamText.text = insightModel.piThresholdValue
                    piThresholdParamTitle.visibility = View.VISIBLE
                    piThresholdParamText.visibility = View.VISIBLE
                    renderProgress.visibility = View.VISIBLE
                    renderProgress.isFocusable = true
                    renderProgress.requestFocus()
                    renderProgress.isFocusable = false
                }else if (insightModel.vistaCode == 3 || insightModel.vistaCode == 4){
                    insightRangeTitle.visibility = View.VISIBLE
                    val insightDateRange = "${insightObjectDateFormat(insightModel.rangeStartDate)}  -\n${insightObjectDateFormat(insightModel.rangeEndDate)}"
                    insightRangeText.text = insightDateRange
                    insightRangeText.visibility = View.VISIBLE
                    piThresholdParamText.text = insightModel.piThresholdValue
                    piThresholdParamTitle.visibility = View.VISIBLE
                    piThresholdParamText.visibility = View.VISIBLE
                    omegaThresholdParamText.text = insightModel.omegaThresholdValue
                    omegaThresholdParamTitle.visibility = View.VISIBLE
                    omegaThresholdParamText.visibility = View.VISIBLE
                    renderProgress.visibility = View.VISIBLE
                    renderProgress.isFocusable = true
                    renderProgress.requestFocus()
                    renderProgress.isFocusable = false
                }

                triageBot.clearAnimation()
                insightDialogView.clearAnimation()
                renderingText.clearAnimation()
                insightLabel.clearAnimation()
                dataModelTitle.clearAnimation()
                dataModelText.clearAnimation()
                endPointTitle.clearAnimation()
                endPointText.clearAnimation()
                insightTypeTitle.clearAnimation()
                insightTypeText.clearAnimation()
                insightRangeText.clearAnimation()
                insightRangeTitle.clearAnimation()
                piThresholdParamTitle.clearAnimation()
                piThresholdParamText.clearAnimation()
                omegaThresholdParamTitle.clearAnimation()
                omegaThresholdParamText.clearAnimation()
                renderProgress.clearAnimation()
                isFinished = true




            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

        return isFinished
    }


    fun disableEntities(){
        val offEntities = arrayListOf<MaterialCardView>()
        offEntities.add(pharmaceuticalsButton)
        for (entity in offEntities){
            entity.isEnabled = false
            entity.isClickable = false
            entity.isCheckable = false
            entity.isChecked = false
        }
    }


}