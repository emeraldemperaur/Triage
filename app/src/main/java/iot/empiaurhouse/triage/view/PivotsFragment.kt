package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentPivotsBinding


private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""


class PivotsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPivotsBinding
    private lateinit var newPivotButton: FloatingActionButton
    private lateinit var navControls: NavController

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPivotsBinding.bind(view)
        val pivotsMainTitle = "DATA PIVOTS"
        binding.dataPivotsTitle.text = pivotsMainTitle
        newPivotButton = binding.createDataPivot
        navControls = view.findNavController()
        initPivotsTabView()

    }


    private fun initPivotsTabView(){
        val newPivot = PivotsFragmentDirections.newPivotAction(0)
        newPivotButton.setOnClickListener {
            navControls.navigate(newPivot)
        }
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