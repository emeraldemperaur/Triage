package iot.empiaurhouse.triage.controller

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R

class InsightModelController {

    private var vistaOptions: ArrayList<MaterialCardView> = arrayListOf()
    private lateinit var vistaInfo: LinearLayout
    private lateinit var vistaInfoLine: View
    private lateinit var vistaInfoText: TextView
    private lateinit var vistaInfoSubText: TextView
    private lateinit var vistaDescribe: TextView
    private lateinit var modelLayout: LinearLayout
    private lateinit var modelLayoutLine: View
    private lateinit var modelType: TextView
    private lateinit var insightPointLayout: ConstraintLayout
    private lateinit var insightPointLayoutLine: View
    private lateinit var renderButton: MaterialButton
    private lateinit var slideUpAnimation : Animation
    private lateinit var slideDownAnimation : Animation
    private lateinit var editorViewContext: View
    private var modelOptions: ArrayList<MaterialCardView> = arrayListOf()
    private  var vistaCode: Int? = null
    private lateinit var controlContext: Context
    private lateinit var hubUserNameTV: TextView
    private lateinit var searchBtn: FloatingActionButton
    private lateinit var toolbar: CollapsingToolbarLayout

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

                    }
                    R.id.pie_chart_vista ->{
                        vistaCode = 2
                        toggleEntityOptions(vista, vistaOptions)
                        toggleEntityOptionLayouts(vistaCode!!)

                    }
                    R.id.line_chart_vista ->{
                        vistaCode = 3
                        toggleEntityOptions(vista, vistaOptions)
                        toggleEntityOptionLayouts(vistaCode!!)

                    }
                    R.id.scatter_plot_vista ->{
                        vistaCode = 4
                        toggleEntityOptions(vista, vistaOptions)
                        toggleEntityOptionLayouts(vistaCode!!)

                    }
                }

            }
        }
        return vistaCode
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


    private fun toggleEntityOptions(selectedOption: MaterialCardView, entityOptions: ArrayList<MaterialCardView>){
        for (entity in entityOptions){
            entity.isChecked = false
            if (entity == selectedOption){
                selectedOption.isChecked = true
            }
        }
    }


}