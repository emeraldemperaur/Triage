package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentInsightModelDialogBinding
import iot.empiaurhouse.triage.model.InsightModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InsightModelDialogFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentInsightModelDialogBinding
    private lateinit var toolbarView: CollapsingToolbarLayout
    private lateinit var hubUserName: TextView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var insightModel: InsightModel
    private val args: InsightModelDialogFragmentArgs by navArgs()


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
        return inflater.inflate(R.layout.fragment_insight_model_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInsightModelDialogBinding.bind(view)
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        hubUserName = requireActivity().findViewById(R.id.hub_username_title)
        searchButton = requireActivity().findViewById(R.id.hub_search_button)
        insightModel = args.insightModel
        initInsightDialogView()
        onBackPressed()
    }


    private fun initInsightDialogView(){
        toolbarView.visibility = View.GONE
        hubUserName.visibility = View.GONE
        searchButton.visibility = View.GONE
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
            InsightModelDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}