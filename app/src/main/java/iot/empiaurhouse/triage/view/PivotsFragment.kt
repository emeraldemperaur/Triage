package iot.empiaurhouse.triage.view

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.DataPivotsAdapter
import iot.empiaurhouse.triage.adapter.SwipeToDeleteCallback
import iot.empiaurhouse.triage.databinding.FragmentPivotsBinding
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.viewmodel.DataPivotViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class PivotsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPivotsBinding
    private lateinit var pivotViewModel: DataPivotViewModel
    private lateinit var newPivotButton: FloatingActionButton
    private lateinit var pivotSwipeRefresh: SwipeRefreshLayout
    private lateinit var noResultsText: TextView
    private lateinit var loadingText: TextView
    private lateinit var navControls: NavController
    private lateinit var pivotsView: ConstraintLayout
    private lateinit var app: Application
    private var pivotsRecyclerView: RecyclerView? = null
    private var dataPivotsFound = arrayListOf<DataPivot>()
    private var dataPivotRVA: DataPivotsAdapter? = null
    private var cleanDBEnabled = false
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout


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
        return inflater.inflate(R.layout.fragment_pivots, container, false)
    }


    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotsBinding.bind(view)
        val pivotsMainTitle = "DATA PIVOTS"
        binding.dataPivotsTitle.text = pivotsMainTitle
        newPivotButton = binding.createDataPivot
        pivotsView = binding.pivotView
        noResultsText = binding.noDataPivotsFound
        loadingText = binding.loadingDataPivots
        pivotSwipeRefresh = binding.pivotsSwipeRefresh
        pivotSwipeRefresh.setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.chiron_blue, null))
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName.visibility = View.VISIBLE
        searchButton.visibility = View.VISIBLE
        toolbarView.visibility = View.VISIBLE
        pivotViewModel = ViewModelProvider(this).get(DataPivotViewModel::class.java)
        app = requireActivity().application
        pivotViewModel.processPivot(app)
        pivotsRecyclerView = binding.dataPivotsViewRecyclerview
        pivotsRecyclerView!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        navControls = view.findNavController()
        initPivotsTabView()
        dataPivotsFound = fetchPivotsDB()
        dataPivotRVA = DataPivotsAdapter(dataPivotsFound, pivotsView, this, app)
        pivotsRecyclerView!!.adapter = dataPivotRVA
    }


    private fun initPivotsTabView(){
        val newPivot = PivotsFragmentDirections.newPivotAction(0)
        newPivotButton.setOnClickListener {
            navControls.navigate(newPivot)
        }
        onBackPressed()
        initSwipeDeleteGesture()
        initRefresh()
    }

    private fun initSwipeDeleteGesture(){
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = dataPivotRVA
                adapter!!.deletePivot(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(pivotsRecyclerView)

        }


    private fun fetchPivotsDB(): ArrayList<DataPivot> {
        var result: Boolean
        val fetchedPivots = arrayListOf<DataPivot>()
        if (view != null) {
            if (!cleanDBEnabled) {
                pivotViewModel.dataPivots.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { reply ->
                        reply?.let {
                            result = reply.isNotEmpty()
                            if (fetchedPivots.isEmpty()) {
                                fetchedPivots.addAll(reply)
                                dataPivotsFound = fetchedPivots
                                println("Data Pivots response object is not empty: $result")
                                println("See Data Pivots response result: $reply")
                            }
                        }
                    })
            }else if (cleanDBEnabled){
                pivotViewModel.fetchPivotsListClean()
                pivotViewModel.dataPivotsClean.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { reply ->
                        reply?.let {
                            result = reply.isNotEmpty()
                            if (fetchedPivots.isEmpty()) {
                                fetchedPivots.addAll(reply)
                                dataPivotsFound = fetchedPivots
                                println("Data Pivots (Clean) response object is not empty: $result")
                                println("See Data Pivots (Clean) response result: $reply")
                            }
                        }
                    })
            }
        }
        return fetchedPivots
    }


    private fun viewRefresh(){
        pivotsRecyclerView?.adapter = null
        dataPivotRVA = null
        pivotsRecyclerView = null
        Handler(Looper.getMainLooper()).postDelayed({
            dataPivotsFound = fetchPivotsDB()
            pivotsRecyclerView = binding.dataPivotsViewRecyclerview
            dataPivotRVA = DataPivotsAdapter(dataPivotsFound, pivotsView, this, app)
            pivotsRecyclerView!!.adapter = dataPivotRVA
            noResultsView(dataPivotsFound.size)
        }, 1000)
        initSwipeDeleteGesture()
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pivotsRecyclerView?.adapter = null
        dataPivotRVA = null
        pivotsRecyclerView = null

    }


    private fun initRefresh(){
        pivotSwipeRefresh.setOnRefreshListener {
            loadingText.visibility = View.VISIBLE
            viewRefresh()
            pivotSwipeRefresh.isRefreshing = false
        }
    }


    override fun onPause() {
        super.onPause()
        loadingText.visibility = View.VISIBLE
    }


    private fun noResultsView(recordsFound: Int){
        loadingText.visibility = View.GONE
        if (recordsFound < 1){
            if (pivotsRecyclerView != null) {
                pivotsRecyclerView!!.visibility = View.GONE
            }
            noResultsText.visibility = View.VISIBLE
        }
        else if (recordsFound > 0){
            noResultsText.visibility = View.GONE
            if (pivotsRecyclerView != null) {
                pivotsRecyclerView!!.visibility = View.VISIBLE
            }
        }
    }

    fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                isEnabled = true
                requireActivity().moveTaskToBack(true)
            }
        })
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PivotsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}