package iot.empiaurhouse.triage.controller

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.adapter.DiagnosesPagerAdapter
import iot.empiaurhouse.triage.adapter.DiagnosesRecyclerAdapter
import iot.empiaurhouse.triage.model.*
import java.util.*


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
        if (patientRecord.insuranceVendorID.isNullOrBlank()){
            val holderText = "Not Provided"
            insurer.text = holderText
        }
        insurerID.text = patientRecord.insuranceVendorID
        if (patientRecord.insuranceVendorID.isNullOrBlank()){
            val holderID = "UNKNOWN ID"
            insurerID.text = holderID
        }
        bloodGroup.text = patientRecord.bloodGroup
        var addressStr = "${patientRecord.address}, ${patientRecord.city}"
        if (patientRecord.city.isNullOrBlank()){
            addressStr = "${patientRecord.address}"
        }
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun initPrescriptionRecordView(context: FragmentActivity, prescriptionRecord: Prescription, prescriptionRecordView: ConstraintLayout,
                                   prescriptionName: TextView, prescriptionDate: TextView,
                                   prescriber: TextView, prescriberID: TextView, patient: TextView,
                                   dosageAmount: TextView, dosageRegimen: TextView,
                                   dosageDuration: TextView, dosageType: ImageView, batchNumber: TextView){

        formatTool = PivotController()
        prescriptionRecordView.visibility = View.VISIBLE
        prescriptionName.text = prescriptionRecord.prescriptionName
        prescriptionDate.text = formatTool.pivotObjectDateFormat(prescriptionRecord.prescriptionDate)
        prescriber.text = prescriptionRecord.prescribedBy
        prescriberID.text = prescriptionRecord.prescriptionPractitionerID
        if (prescriptionRecord.prescriptionPractitionerID.isNullOrBlank()){
            val holderID = "UNKNOWN ID"
            prescriberID.text = holderID
        }
        patient.text = prescriptionRecord.patientFullName
        dosageAmount.text = prescriptionRecord.prescribedDosageAmount
        dosageRegimen.text = prescriptionRecord.prescriptionDosageRegimen
        dosageDuration.text = prescriptionRecord.prescribedDuration
        if (prescriptionRecord.batchNumber !== null){
            batchNumber.visibility = View.VISIBLE
            batchNumber.text = prescriptionRecord.batchNumber
        }else if (prescriptionRecord.approvalNumber !== null){
            batchNumber.visibility = View.VISIBLE
            batchNumber.text = prescriptionRecord.approvalNumber
        }
        dosageType.setImageDrawable(ContextCompat.getDrawable(context,iconRxController(prescriptionRecord.prescribedDosageType)))

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initVisitRecordView(visitRecord: Visit, visitRecordView: ConstraintLayout,
                            visitDate: TextView, visitTime: TextView, visitHost: TextView, visitHostID: TextView,
                            visitPatient: TextView, visitLogText: TextView){

        formatTool = PivotController()
        visitRecordView.visibility = View.VISIBLE
        visitDate.text = formatTool.pivotObjectDateFormat(visitRecord.visitDate)
        visitTime.text = visitRecord.visitTime
        visitHost.text = visitRecord.hostPractitioner
        visitHostID.text = visitRecord.hostPractitionerID
        if (visitRecord.hostPractitionerID.isNullOrBlank()){
            val holderID = "UNKNOWN ID"
            visitHostID.text = holderID
        }
        visitPatient.text = visitRecord.patientFullName
        visitLogText.text = visitRecord.visitDescription

    }


    fun initPractitionerRecordView(context: FragmentActivity, practitionerRecord: Practitioner, practitionerRecordView: ConstraintLayout,
                                   firstName: TextView, lastName: TextView, speciality: TextView,
                                   generalPractitionerID: TextView, generalPractitionerPhone: TextView,
                                   generalPractitionerEmail: TextView, generalPractitionerPhoneButton: MaterialButton,
                                   generalPractitionerEmailButton: MaterialButton, practitionerProfile: ImageView){

        practitionerRecordView.visibility = View.VISIBLE
        firstName.text = practitionerRecord.firstName
        lastName.text = practitionerRecord.lastName
        val holderText = "General Practitioner"
        speciality.text = holderText
        if (practitionerRecord.practitionerID.isNullOrBlank()){
            val holderID = "Unknown ID"
            generalPractitionerID.text = holderID
        }
        generalPractitionerID.text = practitionerRecord.practitionerID
        generalPractitionerPhone.text = practitionerRecord.contactInfo
        generalPractitionerEmail.text = practitionerRecord.emailInfo
        val holderContact = "Not Provided"
        if (practitionerRecord.contactInfo.isNullOrBlank()){
            generalPractitionerPhone.text = holderContact
        }
        if (practitionerRecord.emailInfo.isNullOrBlank()){
            generalPractitionerEmail.text = holderContact
        }
        val initialFN = practitionerRecord.firstName!!.first().toUpperCase()
        val initialLN = practitionerRecord.lastName!!.first().toUpperCase()
        val initials = "$initialFN$initialLN"
        val callButtonTxt = "CALL $initials"
        val emailButtonTxt = "EMAIL $initials"
        if (practitionerRecord.contactInfo.isNullOrBlank()){
            generalPractitionerPhoneButton.isEnabled = false
        }
        else if (!practitionerRecord.contactInfo.isNullOrBlank()){
            generalPractitionerPhoneButton.isEnabled = true
            generalPractitionerPhoneButton.text = callButtonTxt
            generalPractitionerPhoneButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + "${practitionerRecord.contactInfo}")
                context.startActivity(dialIntent)
            }
        }
        if (practitionerRecord.emailInfo.isNullOrBlank()){
            generalPractitionerEmailButton.isEnabled = false
        }
        else if (!practitionerRecord.emailInfo.isNullOrBlank()){
            generalPractitionerEmailButton.isEnabled = true
            generalPractitionerEmailButton.text = emailButtonTxt
            generalPractitionerEmailButton.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                val toRecipient = practitionerRecord.emailInfo
                val subject = "TRIAGE BOT - GP PAGER | ${practitionerRecord.delimitedFullName}"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    println("Error Exception Encountered (generalPractitionerEmail)\n \t ${e.message}")
                }

            }

        }

    }

    fun initDoctorRecordView(context: FragmentActivity, doctorRecord: Doctor, doctorRecordView: ConstraintLayout,
                             firstName: TextView, lastName: TextView, speciality: TextView,
                             doctorPractitionerID: TextView, doctorPhone: TextView,
                             doctorEmail: TextView, doctorPhoneButton: MaterialButton,
                             doctorEmailButton: MaterialButton, doctorProfile: ImageView){

        doctorRecordView.visibility = View.VISIBLE
        firstName.text = doctorRecord.firstName
        lastName.text = doctorRecord.lastName
        if (doctorRecord.specialities != null && doctorRecord.specialities.isNotEmpty()){
            val dS = doctorRecord.specialities.firstOrNull()
            speciality.text = dS!!.specialityDescription
        }
        else if (doctorRecord.specialities != null && doctorRecord.specialities.isEmpty()){
            val holderSpeciality = "MEDICINE"
            speciality.text = holderSpeciality
        }
        if (doctorRecord.practitionerID.isNullOrBlank()){
            val holderID = "Unknown ID"
            doctorPractitionerID.text = holderID
        }
        doctorPractitionerID.text = doctorRecord.practitionerID
        doctorPhone.text = doctorRecord.contactInfo
        doctorEmail.text = doctorRecord.emailInfo
        val holderContact = "Not Provided"
        if (doctorRecord.contactInfo.isNullOrBlank()){
            doctorPhone.text = holderContact
        }
        if (doctorRecord.emailInfo.isNullOrBlank()){
            doctorEmail.text = holderContact
        }
        val initialFN = doctorRecord.firstName!!.first().toUpperCase()
        val initialLN = doctorRecord.lastName!!.first().toUpperCase()
        val initials = "$initialFN$initialLN"
        val callButtonTxt = "CALL $initials"
        val emailButtonTxt = "EMAIL $initials"
        if (doctorRecord.contactInfo.isNullOrBlank()){
            doctorPhoneButton.isEnabled = false
        }
        else if (!doctorRecord.contactInfo.isNullOrBlank()){
            doctorPhoneButton.isEnabled = true
            doctorPhoneButton.text = callButtonTxt
            doctorPhoneButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + "${doctorRecord.contactInfo}")
                context.startActivity(dialIntent)
            }
        }
        if (doctorRecord.emailInfo.isNullOrBlank()){
            doctorEmailButton.isEnabled = false
        }
        else if (!doctorRecord.emailInfo.isNullOrBlank()){
            doctorEmailButton.isEnabled = true
            doctorEmailButton.text = emailButtonTxt
            doctorEmailButton.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                val toRecipient = doctorRecord.emailInfo
                val subject = "TRIAGE BOT - MD PAGER | ${doctorRecord.delimitedFullName}"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    println("Error Exception Encountered (doctorEmail)\n \t ${e.message}")
                }

            }

        }

    }


    fun initRegisteredNurseRecordView(context: FragmentActivity, registeredNurseRecord: RegisteredNurse,
                                      registeredNurseRecordView: ConstraintLayout,
                                      firstName: TextView, lastName: TextView, speciality: TextView,
                                      registeredNursePractitionerID: TextView, registeredNursePhone: TextView,
                                      registeredNurseEmail: TextView, registeredNursePhoneButton: MaterialButton,
                                      registeredNurseEmailButton: MaterialButton, registeredNurseProfile: ImageView){

        registeredNurseRecordView.visibility = View.VISIBLE
        firstName.text = registeredNurseRecord.firstName
        lastName.text = registeredNurseRecord.lastName
        val holderText = "Registered Nurse"
        speciality.text = holderText
        if (registeredNurseRecord.practitionerID.isNullOrBlank()){
            val holderID = "Unknown ID"
            registeredNursePractitionerID.text = holderID
        }
        registeredNursePractitionerID.text = registeredNurseRecord.practitionerID
        registeredNursePhone.text = registeredNurseRecord.contactInfo
        registeredNurseEmail.text = registeredNurseRecord.emailInfo
        val holderContact = "Not Provided"
        if (registeredNurseRecord.contactInfo.isNullOrBlank()){
            registeredNursePhone.text = holderContact
        }
        if (registeredNurseRecord.emailInfo.isNullOrBlank()){
            registeredNurseEmail.text = holderContact
        }
        val initialFN = registeredNurseRecord.firstName!!.first().toUpperCase()
        val initialLN = registeredNurseRecord.lastName!!.first().toUpperCase()
        val initials = "$initialFN$initialLN"
        val callButtonTxt = "CALL $initials"
        val emailButtonTxt = "EMAIL $initials"
        if (registeredNurseRecord.contactInfo.isNullOrBlank()){
            registeredNursePhoneButton.isEnabled = false
        }
        else if (!registeredNurseRecord.contactInfo.isNullOrBlank()){
            registeredNursePhoneButton.isEnabled = true
            registeredNursePhoneButton.text = callButtonTxt
            registeredNursePhoneButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + "${registeredNurseRecord.contactInfo}")
                context.startActivity(dialIntent)
            }
        }
        if (registeredNurseRecord.emailInfo.isNullOrBlank()){
            registeredNurseEmailButton.isEnabled = false
        }
        else if (!registeredNurseRecord.emailInfo.isNullOrBlank()){
            registeredNurseEmailButton.isEnabled = true
            registeredNurseEmailButton.text = emailButtonTxt
            registeredNurseEmailButton.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                val toRecipient = registeredNurseRecord.emailInfo
                val subject = "TRIAGE BOT - RN PAGER | ${registeredNurseRecord.delimitedFullName}"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    println("Error Exception Encountered (registeredNurseEmail)\n \t ${e.message}")
                }

            }

        }
    }


    fun initNursePractitionerRecordView(context: FragmentActivity, nursePractitionerRecord: NursePractitioner,
                                        nursePractitionerRecordView: ConstraintLayout,
                                      firstName: TextView, lastName: TextView, speciality: TextView,
                                        nursePractitionerPractitionerID: TextView, nursePractitionerPhone: TextView,
                                        nursePractitionerEmail: TextView, nursePractitionerPhoneButton: MaterialButton,
                                        nursePractitionerEmailButton: MaterialButton, nursePractitionerProfile: ImageView){

        nursePractitionerRecordView.visibility = View.VISIBLE
        firstName.text = nursePractitionerRecord.firstName
        lastName.text = nursePractitionerRecord.lastName
        val holderText = "Nurse Practitioner"
        speciality.text = holderText
        if (nursePractitionerRecord.practitionerID.isNullOrBlank()){
            val holderID = "Unknown ID"
            nursePractitionerPractitionerID.text = holderID
        }
        nursePractitionerPractitionerID.text = nursePractitionerRecord.practitionerID
        nursePractitionerPhone.text = nursePractitionerRecord.contactInfo
        nursePractitionerEmail.text = nursePractitionerRecord.emailInfo
        val holderContact = "Not Provided"
        if (nursePractitionerRecord.contactInfo.isNullOrBlank()){
            nursePractitionerPhone.text = holderContact
        }
        if (nursePractitionerRecord.emailInfo.isNullOrBlank()){
            nursePractitionerEmail.text = holderContact
        }
        val initialFN = nursePractitionerRecord.firstName!!.first().toUpperCase()
        val initialLN = nursePractitionerRecord.lastName!!.first().toUpperCase()
        val initials = "$initialFN$initialLN"
        val callButtonTxt = "CALL $initials"
        val emailButtonTxt = "EMAIL $initials"
        if (nursePractitionerRecord.contactInfo.isNullOrBlank()){
            nursePractitionerPhoneButton.isEnabled = false
        }
        else if (!nursePractitionerRecord.contactInfo.isNullOrBlank()){
            nursePractitionerPhoneButton.isEnabled = true
            nursePractitionerPhoneButton.text = callButtonTxt
            nursePractitionerPhoneButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + "${nursePractitionerRecord.contactInfo}")
                context.startActivity(dialIntent)
            }
        }
        if (nursePractitionerRecord.emailInfo.isNullOrBlank()){
            nursePractitionerEmailButton.isEnabled = false
        }
        else if (!nursePractitionerRecord.emailInfo.isNullOrBlank()){
            nursePractitionerEmailButton.isEnabled = true
            nursePractitionerEmailButton.text = emailButtonTxt
            nursePractitionerEmailButton.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                val toRecipient = nursePractitionerRecord.emailInfo
                val subject = "TRIAGE BOT - NP PAGER | ${nursePractitionerRecord.delimitedFullName}"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    println("Error Exception Encountered (registeredNurseEmail)\n \t ${e.message}")
                }

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initPharmaceuticalRecordView(context: FragmentActivity, pharmaceuticalRecord: Pharmaceuticals,
                                     pharmaceuticalRecordView: ConstraintLayout, pharmaceuticalRxName: TextView,
                                     pharmaceuticalManufacturer: TextView, pharmaceuticalMakeDate: TextView,
                                     pharmaceuticalExpiryDate: TextView, pharmaceuticalGenericName: TextView,
                                     pharmaceuticalChemicalName: TextView, pharmaceuticalLotID: TextView, pharmaceuticalLotIcon: ImageView,
                                     pharmaceuticalApprovalID: TextView, pharmaceuticalCount: TextView,
                                     restockButton: MaterialButton){

        formatTool = PivotController()
        pharmaceuticalRecordView.visibility = View.VISIBLE
        pharmaceuticalRxName.text = pharmaceuticalRecord.brandName
        pharmaceuticalManufacturer.text = pharmaceuticalRecord.manufacturerName
        pharmaceuticalMakeDate.text = formatTool.pivotObjectDateFormat(pharmaceuticalRecord.manufactureDate)
        pharmaceuticalExpiryDate.text = formatTool.pivotObjectDateFormat(pharmaceuticalRecord.expiryDate)
        pharmaceuticalGenericName.text = pharmaceuticalRecord.genericName
        if (pharmaceuticalRecord.genericName.isNullOrBlank()){
            pharmaceuticalGenericName.text = pharmaceuticalRecord.brandName
        }
        pharmaceuticalChemicalName.text = pharmaceuticalRecord.chemicalName
        if (pharmaceuticalRecord.chemicalName.isNullOrBlank()){
            val holderTxt = "Not Provided"
            pharmaceuticalChemicalName.text = holderTxt
        }
        if (!pharmaceuticalRecord.batchNumber.isNullOrBlank()){
            pharmaceuticalLotID.text = pharmaceuticalRecord.batchNumber
            pharmaceuticalLotID.setTextColor(Color.parseColor("#0c204f"))
            pharmaceuticalLotIcon.setColorFilter(Color.parseColor("#0c204f"))
        }
        if (pharmaceuticalRecord.batchNumber.isNullOrBlank()){
            val lotHolderTxt = "No Lot #"
            pharmaceuticalLotID.text = lotHolderTxt
        }
        pharmaceuticalApprovalID.text = pharmaceuticalRecord.approvalNumber
        if (pharmaceuticalRecord.approvalNumber.isNullOrBlank()){
            pharmaceuticalApprovalID.visibility = View.INVISIBLE
        }
        pharmaceuticalCount.text = pharmaceuticalRecord.inStock.toString()
        if (pharmaceuticalRecord.inStock!! < 151){
            restockButton.isEnabled = true
            restockButton.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"
                val toRecipient = ""
                val subject = "TRIAGE BOT - Rx INVENTORY RESTOCK | ${pharmaceuticalRecord.brandName}"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    println("Error Exception Encountered (pharmaceuticalRestock)\n \t ${e.message}")
                }

            }
        }

    }



    private fun iconRxController(rxType: String?): Int {
        var iconResInt = R.drawable.pills_bottle_icon
        when(rxType!!.toUpperCase(Locale.ROOT)){
            "PILL(S)" ->{
                iconResInt = R.drawable.pills_bottle_icon
            }
            "CAPSULE(S)" ->{
                iconResInt = R.drawable.capsule_rx_icon
            }
            "TABLET(S)" ->{
                iconResInt = R.drawable.tablet_rx_icon
            }
            "TEASPOON(S)" ->{
                iconResInt = R.drawable.teaspoon_rx_icon
            }
            "DROP(S)" ->{
                iconResInt = R.drawable.drop_rx_icon
            }
            "BAG(S)" ->{
                iconResInt = R.drawable.mthc_rx_icon
            }
            "GRAM(S)" ->{
                iconResInt = R.drawable.gram_rx_icon
            }

        }
        return iconResInt
    }

  

}