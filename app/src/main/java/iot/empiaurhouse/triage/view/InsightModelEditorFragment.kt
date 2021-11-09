package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.InsightModelController
import iot.empiaurhouse.triage.databinding.FragmentInsightModelEditorBinding


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
        renderInsightButton = binding.createInsightButton
        insightController = InsightModelController()
        initInsightEditorView()

    }


    private fun initInsightEditorView(){
        vistaCode = insightController.initInsightModelEditor(requireContext(), insightEditorView, histogramButton,
            pieChartButton, lineChartButton, scatterPlotButton, vistaInfoView, vistaInfoBorder,
            vistaInfoTitle,vistaInfoSubTitle,vistaModelLayout,vistaModelLayoutLine, vistaDescription, hubUserName, searchButton, toolbarView)
        codeTalker()

        renderInsightButton.setOnClickListener {
            codeTalker()
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