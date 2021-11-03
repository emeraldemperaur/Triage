package iot.empiaurhouse.triage.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.CollapsingToolbarLayout
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.FragmentRecordEditorDialogBinding
import iot.empiaurhouse.triage.model.NursePractitioner
import iot.empiaurhouse.triage.model.Patient
import iot.empiaurhouse.triage.model.Practitioner
import iot.empiaurhouse.triage.model.RegisteredNurse
import iot.empiaurhouse.triage.utils.TypeWriterTextView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RecordEditorDialogFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecordEditorDialogBinding
    private lateinit var recordEditStatus: TypeWriterTextView
    private lateinit var recordEditStatusIcon: ImageView
    private lateinit var recordEditEntity: TextView
    private lateinit var rotateAnimation: Animation
    private lateinit var patient: Patient
    private lateinit var practitioner: Practitioner
    private lateinit var registeredNurse: RegisteredNurse
    private lateinit var nursePractitioner: NursePractitioner
    private lateinit var entityType: String
    private lateinit var editStatus: String
    private lateinit var recordEditLabel: TextView
    private lateinit var toolbarView: CollapsingToolbarLayout
    private var metaDataID: Int? = null
    private var recordID: Int? = null
    private val args: RecordEditorDialogFragmentArgs by navArgs()

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
        return inflater.inflate(R.layout.fragment_record_editor_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecordEditorDialogBinding.bind(view)
        recordEditStatus = binding.triageEditDialogTitle
        recordEditStatusIcon = binding.triageEditBotStatusIcon
        recordEditEntity = binding.triageEditDialogEntity
        recordEditLabel = binding.triageEditDialogEntityLabel
        toolbarView = requireActivity().findViewById(R.id.hub_collapsing_toolbar)
        toolbarView.visibility = View.GONE
        rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        recordID = args.recordID
        if (args.patient != null){
            patient = args.patient!!
            if (patient.id != null){
                metaDataID = patient.id
            }
        }
        if (args.practitioner != null){
            practitioner = args.practitioner!!
            if (practitioner.id != null){
                metaDataID = practitioner.id
            }
        }
        if (args.registeredNurse != null){
            registeredNurse = args.registeredNurse!!
            if (registeredNurse.id != null){
                metaDataID = registeredNurse.id
            }
        }
        if (args.nursePractitioner != null){
            nursePractitioner = args.nursePractitioner!!
            if (nursePractitioner.id != null){
                metaDataID = nursePractitioner.id
            }
        }
        initRecordEditDialog(recordID!!)
        onBackPressed()

    }


    private fun initRecordEditDialog(recordID: Int){
        if (metaDataID == null){
            editStatus = "CREATING..."
        }
        else if (metaDataID != null){
            editStatus = "UPDATING..."
        }
        recordEditStatusIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_autorenew_24))
        recordEditStatusIcon.setColorFilter(Color.parseColor("#0c204f"))
        recordEditStatusIcon.startAnimation(rotateAnimation)
        recordEditStatus.visibility = View.VISIBLE
        recordEditStatus.setCharacterDelay(96)
        recordEditStatus.displayTextWithAnimation(editStatus)
        Handler(Looper.getMainLooper()).postDelayed({
            recordEditEntity.visibility = View.VISIBLE
            recordEditLabel.visibility = View.VISIBLE
        }, 2569)
        when(recordID){
            1 ->{
                entityType = "Patient"
                recordEditEntity.text = entityType
                recordEditLabel.text = patient.fullName
            }
            2 ->{
                entityType = "Diagnosis"
                recordEditEntity.text = entityType
            }
            3 ->{
                entityType = "Prescription"
                recordEditEntity.text = entityType
            }
            4 ->{
                entityType = "Visit"
                recordEditEntity.text = entityType
            }
            5 ->{
                entityType = "General Practitioner"
                recordEditEntity.text = entityType
                recordEditLabel.text = practitioner.fullName
            }
            6 ->{
                entityType = "Doctor"
                recordEditEntity.text = entityType
            }
            7 ->{
                entityType = "Registered Nurse"
                recordEditEntity.text = entityType
                recordEditLabel.text = registeredNurse.fullName

            }
            8 ->{
                entityType = "Nurse Practitioner"
                recordEditEntity.text = entityType
                recordEditLabel.text = nursePractitioner.fullName

            }
            9 ->{
                entityType = "Pharmaceutical"
                recordEditEntity.text = entityType
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
            RecordEditorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}