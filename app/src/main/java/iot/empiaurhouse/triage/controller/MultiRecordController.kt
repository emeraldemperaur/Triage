package iot.empiaurhouse.triage.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iot.empiaurhouse.triage.adapter.DiagnosesPagerAdapter
import iot.empiaurhouse.triage.adapter.DiagnosesRecyclerAdapter
import iot.empiaurhouse.triage.model.Diagnosis
import iot.empiaurhouse.triage.model.Patient


class MultiRecordController {

    private lateinit var patientDiagnosesRVA: DiagnosesRecyclerAdapter
    private lateinit var formatTool: PivotController
    private lateinit  var diagnosesPagerAdapter: DiagnosesPagerAdapter



    fun fetchRecordName(recordID: Int): String{
        var recordName = ""
        when(recordID){
            1 ->{
                recordName = "Patients"
            }
            2 ->{
                recordName = "Diagnoses"
            }
            3 ->{
                recordName = "Prescriptions"
            }
            4 ->{
                recordName = "Visits"
            }
            5 ->{
                recordName = "General Practitioners"
            }
            6 ->{
                recordName = "Doctors"
            }
            7 ->{
                recordName = "Registered Nurses"
            }
            8 ->{
                recordName = "Nurse Practitioners"
            }
            9 ->{
                recordName = "Pharmaceuticals"
            }
        }
        return recordName
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initPatientRecordView(context: Context, patientRecord: Patient, patientRecordView: ConstraintLayout, firstName: TextView,
                              lastName: TextView, birthDate: TextView, insurer: TextView,
                              insurerID: TextView, bloodGroup: TextView, address: TextView,
                              phoneNumber: TextView, diagnosesTitle: TextView,
                              noDiagnosesFound: TextView, recordProfile: ImageView,
                              recordDiagnosesRV: RecyclerView){
        formatTool = PivotController()
        patientRecordView.visibility = View.VISIBLE
        noDiagnosesFound.visibility = View.INVISIBLE
        firstName.text = patientRecord.firstName
        lastName.text = patientRecord.lastName
        birthDate.text = formatTool.pivotObjectDateFormat(patientRecord.birthDate)
        insurer.text = patientRecord.insuranceVendor
        insurerID.text = patientRecord.insuranceVendorID
        bloodGroup.text = patientRecord.bloodGroup
        val addressStr = "${patientRecord.address}, ${patientRecord.city}"
        address.text = addressStr
        phoneNumber.text = patientRecord.phoneNumber
        val diagnosisCount = patientRecord.diagnoses!!.size
        var diagnosesText = ""
        if (diagnosisCount > 0){
            diagnosesText = "($diagnosisCount) Diagnoses"
            noDiagnosesFound.visibility = View.INVISIBLE
        }
        else if (diagnosisCount < 1){
            diagnosesText = "Diagnoses"
            noDiagnosesFound.visibility = View.VISIBLE
        }
        if(diagnosisCount > 99){
            diagnosesTitle.letterSpacing = 0.33F
        }
        diagnosesTitle.text = diagnosesText
        if (patientRecord.image != null){
        val patientProfileRAW = patientRecord.image

        val bmp = BitmapFactory.decodeByteArray(patientProfileRAW, 0, patientProfileRAW.size)
        if (bmp != null){
            recordProfile.setImageBitmap(Bitmap.createScaledBitmap(bmp, 69, 69, false))
        }
        }
        recordDiagnosesRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        patientDiagnosesRVA = DiagnosesRecyclerAdapter(patientRecord.diagnoses, patientRecordView)
        recordDiagnosesRV.adapter = patientDiagnosesRVA


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initDiagnosisRecordView(context: FragmentActivity, diagnosesRecord: Diagnosis, diagnosisRecordView: ConstraintLayout,
                                diagnosisSynopsis: TextView, diagnosisDate: TextView,
                                diagnosisLevel: TextView, diagnosisPatient: TextView,
                                diagnosisLevelLine: TextView, diagnosisNote: TextView,
                                diagnosisTabs: TabLayout, diagnosisViewPager2: ViewPager2
    ){

        formatTool = PivotController()
        diagnosisRecordView.visibility = View.VISIBLE
        diagnosisSynopsis.text = diagnosesRecord.diagnosisSynopsis
        diagnosisDate.text = formatTool.pivotObjectDateFormat(diagnosesRecord.visitDate)
        diagnosisLevel.text = diagnosesRecord.diagnosisLevel.diagnosisLevelName
        diagnosisPatient.text = diagnosesRecord.patientFullName
        diagnosisLevelLine.setTextColor(Color.parseColor(diagnosesRecord.diagnosisLevel.diagnosisLevelHexCode))
        diagnosisNote.text = diagnosesRecord.diagnosisDetails
        diagnosesPagerAdapter = DiagnosesPagerAdapter(context, diagnosesRecord.prescriptions, diagnosesRecord.visits)
        diagnosesPagerAdapter.notifyDataSetChanged()
        diagnosisViewPager2.adapter = diagnosesPagerAdapter
        TabLayoutMediator(diagnosisTabs, diagnosisViewPager2) { tab, position ->
            if (position == 0){
                tab.text = "PRESCRIPTIONS"
            }
            else if (position == 1){
                tab.text = "VISITS"
            }

        }.attach()

    }

  

}