package iot.empiaurhouse.triage.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentDashboardBinding
import iot.empiaurhouse.triage.model.ChironRecords
import iot.empiaurhouse.triage.model.Pivot
import iot.empiaurhouse.triage.utils.InsightChart
import iot.empiaurhouse.triage.utils.ListStore
import iot.empiaurhouse.triage.utils.PivotRecyclerAdapter
import iot.empiaurhouse.triage.utils.RecordRecyclerAdapter
import iot.empiaurhouse.triage.viewmodel.SetupActivityViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class DashboardFragment : Fragment(), OnChartValueSelectedListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var pivotRecyclerView: RecyclerView
    private var recordRecyclerView: RecyclerView? = null
    private lateinit var insightChart: PieChart
    private lateinit var chironNote: TextView
    private lateinit var pivotsRVA: PivotRecyclerAdapter
    private var recordsRVA: RecordRecyclerAdapter? = null
    private lateinit var hubInsightChart: InsightChart
    private var recordsFound = arrayListOf<ChironRecords>()
    private var recordsCache = arrayListOf<ChironRecords>()
    private lateinit var dashboardViewModel: SetupActivityViewModel





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)




        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        binding.insightRequestTime.text = getTimeStamp()
        pivotRecyclerView = binding.hubPivotsRecyclerView
        recordRecyclerView = binding.hubRecordsRecyclerView
        recordRecyclerView!!.setItemViewCacheSize(30)
        pivotRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recordRecyclerView!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        insightChart = binding.insightChart
        chironNote = binding.chironNote
        dashboardViewModel = ViewModelProvider(this).get(SetupActivityViewModel::class.java)
        dashboardViewModel.pingServer()
        val fontFace = resources.getFont(R.font.montserratlight)
        InsightChart().renderRecordsPieChart(requireContext(),fontFace,insightChart,recordsFound,this)
        pivotsRVA = PivotRecyclerAdapter(fetchPivotsList())
        recordsFound = fetchRecordsData()
        ListStore.cacheRecordsList(fetchRecordsData())
        recordsFound = ListStore.getCachedRecordsList()
        pivotRecyclerView.adapter = pivotsRVA







    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }




    override fun onStart() {
        super.onStart()


    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
        recordRecyclerView = binding.hubRecordsRecyclerView
        recordsRVA = RecordRecyclerAdapter(fetchRecordsData())
        recordRecyclerView!!.adapter = recordsRVA
        }, 1000)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        recordRecyclerView?.adapter = null
        recordsRVA = null
        recordRecyclerView = null

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            recordRecyclerView = binding.hubRecordsRecyclerView
            recordsRVA = RecordRecyclerAdapter(fetchRecordsData())
            recordRecyclerView!!.adapter = recordsRVA
        }, 1000)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()

    }

    private fun  fetchRecords(): ArrayList<ChironRecords>{
        var result: Boolean
        var fetchedRecords = arrayListOf<ChironRecords>()
        fetchedRecords = dashboardViewModel.fetchRecords()!!
        println("Dashboard Records Results: $fetchedRecords")

        return fetchedRecords
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRecordsData(): ArrayList<ChironRecords>{
        var result: Boolean
        val fetchedRecords = arrayListOf<ChironRecords>()
        dashboardViewModel.serverStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                if (fetchedRecords.isEmpty()) {
                    fetchedRecords.addAll(reply)
                    dashboardViewModel.stashRecordsList(reply)
                    recordsFound = fetchedRecords
                    println("Records response object is not empty: $result")
                    println("See Chiron Records response result: $reply")
                }
            }
        })

        return fetchedRecords
    }


    private fun fetchPivotsList(): ArrayList<Pivot>{
        val pivotList = arrayListOf<Pivot>()
        pivotList.add(Pivot(1, "Patients", R.drawable.patient_pivot_icon))
        pivotList.add(Pivot(2, "Diagnoses", R.drawable.ic_virus_art_icon))
        pivotList.add(Pivot(3, "Prescriptions", R.drawable.rx_sheet_icon))
        pivotList.add(Pivot(4, "Visits", R.drawable.visit_icon_png3))
        pivotList.add(Pivot(5, "Pharmaceuticals", R.drawable.ic_pharmaceuticals_pivot))
        pivotList.add(Pivot(6, " Practitioners", R.drawable.nurse_practitioner_stafficon))
        pivotList.add(Pivot(7, " Doctors", R.drawable.rodofasclepius_icon))
        pivotList.add(Pivot(8, "Nurse Practitioners", R.drawable.nurse_practitioner_stafficon))
        pivotList.add(Pivot(9, "Registered\nNurses", R.drawable.registered_nurse_ong))

        return pivotList
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    fun renderInsightChart(recordsData: List<ChironRecords>){
//        insightChart.setUsePercentValues(true)
//        insightChart.description.isEnabled = false
//        insightChart.setExtraOffsets(5F, 10F, 5F, 5F)
//        insightChart.dragDecelerationFrictionCoef = 0.95f
//        insightChart.isDrawHoleEnabled = true
//        insightChart.setHoleColor(Color.WHITE)
//
//        insightChart.setTransparentCircleColor(Color.WHITE)
//        insightChart.setTransparentCircleAlpha(110)
//
//        insightChart.holeRadius = 58f
//        insightChart.transparentCircleRadius = 61f
//
//        insightChart.setDrawCenterText(false)
//
//        insightChart.rotationAngle = 0F
//        // enable rotation of the chart by touch
//        insightChart.isRotationEnabled = true
//        insightChart.isHighlightPerTapEnabled = true
//        insightChart.setOnChartValueSelectedListener(this)
//
//        insightChart.animateY(1400, Easing.EaseInOutQuad)
//
//
//        val dataEntries = arrayListOf<PieEntry>()
//        val entryColors = arrayListOf<Int>()
//        var count = 0
//        var recordsTitle = ""
//        for (record in recordsData) {
//            count = record.recordCount?.toInt()!!
//            recordsTitle = record.recordName.toString()
//            dataEntries.add(PieEntry(count.toFloat(), recordsTitle ))
//        }
//        val recordsDataSet = PieDataSet(dataEntries,"Records")
//        recordsDataSet.sliceSpace = 3F
//        recordsDataSet.selectionShift = 5F
//        entryColors.add(R.color.recordColor1)
//        entryColors.add(R.color.recordColor2)
//        entryColors.add(R.color.recordColor3)
//        entryColors.add(R.color.recordColor4)
//        entryColors.add(R.color.recordColor5)
//        entryColors.add(R.color.recordColor6)
//        entryColors.add(R.color.recordColor7)
//        entryColors.add(R.color.recordColor8)
//        entryColors.add(R.color.recordColor9)
//        recordsDataSet.colors = entryColors
//        recordsDataSet.valueLinePart1Length = 0.2F
//        recordsDataSet.valueLinePart2Length = 0.4F
//        recordsDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
//        val recordsChartData = PieData(recordsDataSet)
//        recordsChartData.setValueFormatter(PercentFormatter())
//        recordsChartData.setValueTextSize(11F)
//        recordsChartData.setValueTextColor(R.color.chiron_grey)
//        val fontFace = resources.getFont(R.font.montserratlight)
//        recordsChartData.setValueTypeface(fontFace)
//        insightChart.data = recordsChartData
//        insightChart.highlightValues(null)
//        insightChart.invalidate()
//
//
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeStamp(): String {
        val currentTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
        val formattedTime = currentTime.format(timeFormatter)

        return "As at $formattedTime"
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return
        if (h != null) {
            Log.i("Record Insight SELECTED",
                "Value: " + e.y + ", xIndex: " + e.x
                        + ", DataSet index: " + h.dataSetIndex
            )
        }
    }

    override fun onNothingSelected() {
        Log.i("Records Insight Chart", "nothing selected")

    }


}