package iot.empiaurhouse.triage.view

import android.app.Application
import android.content.SharedPreferences
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
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.InsightModelsAdapter
import iot.empiaurhouse.triage.adapter.SwipeToDeleteCallback
import iot.empiaurhouse.triage.databinding.FragmentInsightBinding
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.viewmodel.InsightModelViewModel
import java.util.*


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""

class InsightFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInsightBinding
    private lateinit var insightSwipeRefresh: SwipeRefreshLayout
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var insightButton: FloatingActionButton
    private lateinit var navController: NavController
    private lateinit var insightsView: ConstraintLayout
    private var insightsRecyclerView: RecyclerView? = null
    private var insightModelsFound = arrayListOf<InsightModel>()
    private var insightModelRVA: InsightModelsAdapter? = null
    private var cleanDBEnabled = false
    private lateinit var app: Application
    private lateinit var noResultsText: TextView
    private lateinit var loadingText: TextView
    private lateinit var insightViewModel: InsightModelViewModel
    private lateinit var dbPreferences: SharedPreferences


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
        dbPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        cleanDBEnabled = dbPreferences.getBoolean("cleanServerInsightMode", false)
        return inflater.inflate(R.layout.fragment_insight, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInsightBinding.bind(view)
        val insightsMainTitle = "INSIGHT MODELS"
        binding.insightModelsTitle.text = insightsMainTitle
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        insightsView = binding.root
        insightSwipeRefresh = binding.insightsSwipeRefresh
        insightSwipeRefresh.setColorSchemeColors(ResourcesCompat.getColor(resources, R.color.chiron_blue, null))
        insightsRecyclerView = binding.insightModelsViewRecyclerview
        noResultsText = binding.noInsightPointsFound
        loadingText = binding.loadingInsightModels
        insightButton = binding.createInsightModel
        navController = findNavController()
        hubUserName.visibility = View.VISIBLE
        searchButton.visibility = View.VISIBLE
        toolbarView.visibility = View.VISIBLE
        insightViewModel = ViewModelProvider(this)[InsightModelViewModel::class.java]
        app = requireActivity().application
        insightViewModel.processPivot(app)
        insightsRecyclerView!!.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        initInsightView()
        insightModelsFound = fetchInsightsDB()
        insightModelRVA = InsightModelsAdapter(insightModelsFound, insightsView, this, app)
        insightsRecyclerView!!.adapter = insightModelRVA

    }


    private fun initInsightView(){
        onBackPressed()
        initSwipeDeleteGesture()
        initRefresh()
        insightButton.setOnClickListener {
            val input = InsightFragmentDirections.newInsightModelAction()
            navController.navigate(input)
        }

    }


    private fun initSwipeDeleteGesture(){
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = insightModelRVA
                adapter!!.deleteInsightModel(viewHolder.adapterPosition)
                noResultsView(insightModelsFound.size)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(insightsRecyclerView)

    }


    private fun fetchInsightsDB(): ArrayList<InsightModel> {
        var result: Boolean
        val fetchedInsights = arrayListOf<InsightModel>()
        if (view != null) {
            if (!cleanDBEnabled) {
                insightViewModel.insightModel.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { reply ->
                        reply?.let {
                            result = reply.isNotEmpty()
                            if (fetchedInsights.isEmpty()) {
                                fetchedInsights.addAll(reply)
                                insightModelsFound = fetchedInsights
                                println("Insight Models response object is not empty: $result")
                                println("See Insight Models response result: $reply")
                            }
                        }
                    })
            }else if (cleanDBEnabled){
                insightViewModel.fetchInsightsListClean()
                insightViewModel.insightModelClean.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer { reply ->
                        reply?.let {
                            result = reply.isNotEmpty()
                            if (fetchedInsights.isEmpty()) {
                                fetchedInsights.addAll(reply)
                                insightModelsFound = fetchedInsights
                                println("Insight Models (Clean) response object is not empty: $result")
                                println("See Insight Models (Clean) response result: $reply")
                            }
                        }
                    })
            }
        }
        return fetchedInsights
    }


    private fun noResultsView(recordsFound: Int){
        loadingText.visibility = View.GONE
        if (recordsFound < 1){
            if (insightsRecyclerView != null) {
                insightsRecyclerView!!.visibility = View.GONE
            }
            noResultsText.visibility = View.VISIBLE
        }
        else if (recordsFound > 0){
            noResultsText.visibility = View.GONE
            if (insightsRecyclerView != null) {
                insightsRecyclerView!!.visibility = View.VISIBLE
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


    private fun viewRefresh(){
        insightsRecyclerView?.adapter = null
        insightModelRVA = null
        insightsRecyclerView = null
        noResultsText.visibility = View.INVISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            insightModelsFound = fetchInsightsDB()
            insightsRecyclerView = binding.insightModelsViewRecyclerview
            insightModelRVA = InsightModelsAdapter(insightModelsFound, insightsView, this, app)
            insightsRecyclerView!!.adapter = insightModelRVA
            noResultsView(insightModelsFound.size)
        }, 1000)
        initSwipeDeleteGesture()
    }

    private fun initRefresh(){
        insightSwipeRefresh.setOnRefreshListener {
            loadingText.visibility = View.VISIBLE
            viewRefresh()
            insightSwipeRefresh.isRefreshing = false
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        insightsRecyclerView?.adapter = null
        insightModelRVA = null
        insightsRecyclerView = null

    }

    override fun onPause() {
        super.onPause()
        if (insightModelsFound.size < 0){
            loadingText.visibility = View.VISIBLE
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsightFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}