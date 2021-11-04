package iot.empiaurhouse.triage.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.controller.PivotController
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.utils.subscribeOnBackground
import iot.empiaurhouse.triage.view.AllRecordsFragmentDirections
import iot.empiaurhouse.triage.viewmodel.ChironRecordsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

class ChironRecordsRecyclerAdapter(private val recordID: Int, private val activity: ViewModelStoreOwner,
                                   private val recordsViewObject: View, private val patientsList: ArrayList<Patient>? = null, private val diagnosesList: ArrayList<Diagnosis>? = null,
                                   private val prescriptionsList: ArrayList<Prescription>? = null, private val visitsList: ArrayList<Visit>? = null, private val practitionersList: ArrayList<Practitioner>? = null,
                                   private val doctorsList: ArrayList<Doctor>? = null, private val nursePractitionersList: ArrayList<NursePractitioner>? = null,
                                   private val registeredNursesList: ArrayList<RegisteredNurse>? = null, private val pharmaceuticalsList: ArrayList<Pharmaceuticals>? = null): RecyclerView.Adapter<ChironRecordsRecyclerAdapter.ViewHolder>() {


    private lateinit var recordsContext: Context
    private var recordsInitID by Delegates.notNull<Int>()
    private lateinit var recordsView: View
    private var recordsItemLayout by Delegates.notNull<Int>()
    private lateinit var recordsViewModel: ChironRecordsViewModel


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChironRecordsRecyclerAdapter.ViewHolder {
        recordsContext = parent.context
        recordsView = recordsViewObject
        recordsViewModel = ViewModelProvider(activity).get(ChironRecordsViewModel::class.java)
        recordsInitID = recordID
        recordsItemLayout = R.layout.patient_list_view
        when(recordID){
            1 ->{
                recordsItemLayout = R.layout.patient_list_view

            }
            2 ->{
                recordsItemLayout = R.layout.diagnosis_list_view
            }
            3 ->{
                recordsItemLayout = R.layout.prescription_list_view
            }
            4 ->{
                recordsItemLayout = R.layout.visit_list_view
            }
            5 ->{
                recordsItemLayout = R.layout.practitioner_list_view
            }
            6 ->{
                recordsItemLayout = R.layout.doctor_list_view
            }
            7 ->{
                recordsItemLayout = R.layout.registerednurse_list_view
            }
            8 ->{
                recordsItemLayout = R.layout.nursepractitioner_list_view
            }
            9 ->{
                recordsItemLayout = R.layout.pharmaceutical_list_view
            }
        }
        val v = LayoutInflater.from(parent.context).inflate(recordsItemLayout, parent, false)
        return ViewHolder(v)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val navControls = holder.navControl
        val formatTools = PivotController()
        when(recordID) {
            1 -> {
                val focusPatient = patientsList!![position]
                holder.patientShortName!!.text = focusPatient.delimitedFullName
                holder.patientInsurer!!.text = focusPatient.insuranceVendor
                holder.patientInsurerID!!.text = focusPatient.insuranceVendorID
                holder.patientBloodGroup!!.text = focusPatient.bloodGroup
                iconController(holder.patientDiagnosesCountIcon!!, holder.patientDiagnosesCount!!, focusPatient.diagnoses!!.size)
                holder.patientDiagnosesCount.text = focusPatient.diagnoses.size.toString()
                holder.patientItem!!.setOnClickListener {
                    // go to detail view with navigation + focusPatientObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, focusPatient,
                        null, null, null, null, null,
                        null, null, null)
                    navControls.navigate(input)
                }

            }
            2 -> {
                val focusDiagnosis = diagnosesList!![position]
                holder.diagnosisSynopsis!!.text = focusDiagnosis.diagnosisSynopsis
                holder.diagnosisPatientName!!.text = focusDiagnosis.patientFullName
                holder.diagnosisDate!!.text = formatTools.pivotObjectDateFormat(focusDiagnosis.visitDate)
                holder.diagnosisPrescriptionsCount!!.text = focusDiagnosis.prescriptions!!.size.toString()
                iconController(holder.diagnosisPrescriptionsIcon!!, holder.diagnosisPrescriptionsCount, focusDiagnosis.prescriptions.size)
                holder.diagnosisVisitsCount!!.text = focusDiagnosis.visits!!.size.toString()
                iconController(holder.diagnosisVisitsIcon!!, holder.diagnosisVisitsCount, focusDiagnosis.visits.size)
                holder.diagnosisLevel!!.text = focusDiagnosis.diagnosisLevel.diagnosisLevelName
                holder.diagnosisLevel.setTextColor(Color.parseColor(focusDiagnosis.diagnosisLevel.diagnosisLevelHexCode))
                holder.diagnosisItem!!.setOnClickListener {
                    // go to detail view with navigation + focusDiagnosisObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        focusDiagnosis, null, null, null, null,
                        null, null, null)
                    navControls.navigate(input)

                }
            }
            3 -> {
                val focusPrescription = prescriptionsList!![position]
                holder.prescriptionRxName!!.text = focusPrescription.prescriptionName
                if (focusPrescription.prescriptionName == ""){
                    holder.prescriptionRxName.text = focusPrescription.brandName
                }
                holder.prescriptionPrescriberName!!.text = focusPrescription.prescribedBy
                holder.prescriptionPrescriberID!!.text = focusPrescription.prescriptionPractitionerID
                holder.prescriptionPatientName!!.text = focusPrescription.patientFullName
                holder.prescriptionTypeIcon!!.setImageDrawable(ContextCompat.getDrawable(recordsContext,iconRxController(focusPrescription.prescribedDosageType)))
                holder.prescriptionItem!!.setOnClickListener {
                    // go to detail view with navigation + focusPrescriptionObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, focusPrescription, null, null, null,
                        null, null, null)
                    navControls.navigate(input)

                }

            }
            4 -> {
                val focusVisit = visitsList!![position]
                holder.visitHost!!.text = focusVisit.hostPractitioner
                holder.visitHostID!!.text = focusVisit.hostPractitionerID
                if (focusVisit.hostPractitionerID.isNullOrEmpty()){
                    val idHolder = "UNKNOWN ID"
                    holder.visitHostID.text = idHolder
                }
                holder.visitingPatient!!.text = focusVisit.patientFullName
                holder.visitDate!!.text = formatTools.pivotObjectDateFormat(focusVisit.visitDate)
                holder.visitItem!!.setOnClickListener {
                    // go to detail view with navigation + focusVisitObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, focusVisit, null, null,
                        null, null, null)
                    navControls.navigate(input)

                }

            }
            5 -> {
                val focusPractitioner = practitionersList!![position]
                infoIconController(focusPractitioner.contactInfo, holder.practitionersPhoneIcon!!)
                infoIconController(focusPractitioner.emailInfo, holder.practitionersEmailIcon!!)
                holder.practitionersName!!.text = focusPractitioner.delimitedFullName
                holder.practitionersID!!.text = focusPractitioner.practitionerID
                if (focusPractitioner.practitionerID.isNullOrEmpty()){
                    val idHolder = "UNKNOWN ID"
                    holder.practitionersID.text = idHolder
                }
                holder.practitionerItem!!.setOnClickListener {
                    // go to detail view with navigation + focusPractitionerObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, null, focusPractitioner, null,
                        null, null, null)
                    navControls.navigate(input)

                }
            }
            6 -> {
                val focusDoctor = doctorsList!![position]
                infoIconController(focusDoctor.contactInfo, holder.doctorPhoneIcon!!)
                infoIconController(focusDoctor.emailInfo, holder.doctorEmailIcon!!)
                holder.doctorsName!!.text = focusDoctor.delimitedFullName
                holder.doctorPractitionersID!!.text = focusDoctor.practitionerID
                if (focusDoctor.practitionerID.isNullOrEmpty()){
                    val idHolder = "UNKNOWN ID"
                    holder.doctorPractitionersID.text = idHolder
                }
                if (focusDoctor.specialities != null && focusDoctor.specialities.isNotEmpty()){
                    val speciality = focusDoctor.specialities.firstOrNull()
                    holder.doctorSpeciality!!.text = speciality!!.specialityDescription
                }
                else if (focusDoctor.specialities != null && focusDoctor.specialities.isEmpty()){
                    val holderSpeciality = "MEDICINE"
                    holder.doctorSpeciality!!.text = holderSpeciality
                }
                holder.doctorItem!!.setOnClickListener {
                    // go to detail view with navigation + focusDoctorObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, null, null, focusDoctor,
                        null, null, null)
                    navControls.navigate(input)

                }

            }
            7 -> {
                val focusRegisteredNurse = registeredNursesList!![position]
                infoIconController(focusRegisteredNurse.contactInfo, holder.registeredNursePhoneIcon!!)
                infoIconController(focusRegisteredNurse.emailInfo, holder.registeredNurseEmailIcon!!)
                holder.registeredNurseName!!.text = focusRegisteredNurse.delimitedFullName
                holder.registeredNursePractitionersID!!.text = focusRegisteredNurse.practitionerID
                if (focusRegisteredNurse.practitionerID.isNullOrEmpty()){
                    val idHolder = "UNKNOWN ID"
                    holder.registeredNursePractitionersID.text = idHolder
                }
                holder.registeredNurseItem!!.setOnClickListener {
                    // go to detail view with navigation + focusRegisteredNurseObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, null, null,
                        null, focusRegisteredNurse, null, null)
                    navControls.navigate(input)
                }

            }
            8 -> {
                val focusNursePractitioner = nursePractitionersList!![position]
                infoIconController(focusNursePractitioner.contactInfo, holder.nursePractitionerPhoneIcon!!)
                infoIconController(focusNursePractitioner.emailInfo, holder.nursePractitionerEmailIcon!!)
                holder.nursePractitionerName!!.text = focusNursePractitioner.delimitedFullName
                holder.nursePractitionerPractitionersID!!.text = focusNursePractitioner.practitionerID
                if (focusNursePractitioner.practitionerID.isNullOrEmpty()){
                    val idHolder = "UNKNOWN ID"
                    holder.nursePractitionerPractitionersID.text = idHolder
                }
                holder.nursePractitionerItem!!.setOnClickListener {
                    // go to detail view with navigation + focusNursePractitionerObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, null, null,
                        null, null, focusNursePractitioner, null)
                    navControls.navigate(input)

                }
            }
            9 -> {
                val focusPharmaceuticals = pharmaceuticalsList!![position]
                iconController(holder.pharmaceuticalInventoryIcon!!, holder.pharmaceuticalInventoryCount!!, focusPharmaceuticals.inStock)
                iconControllerS(holder.pharmaceuticalLotIcon!!, holder.pharmaceuticalLotNumber!!, focusPharmaceuticals.batchNumber)
                holder.pharmaceuticalInventoryCount.text = focusPharmaceuticals.inStock.toString()
                holder.pharmaceuticalLotNumber.text = focusPharmaceuticals.batchNumber
                if (focusPharmaceuticals.batchNumber.isNullOrEmpty()){
                    val unsub = "No Lot #"
                    holder.pharmaceuticalLotNumber.text = unsub
                }
                holder.pharmaceuticalsBrand!!.text = focusPharmaceuticals.brandName
                if (focusPharmaceuticals.brandName.isNullOrBlank()){
                    holder.pharmaceuticalsBrand.text = focusPharmaceuticals.genericName
                }
                holder.pharmaceuticalMaker!!.text = focusPharmaceuticals.manufacturerName
                holder.pharmaceuticalMakeDate!!.text = formatTools.pivotObjectDateFormat(focusPharmaceuticals.manufactureDate)
                expiryController(focusPharmaceuticals.expiryDate, holder.pharmaceuticalExpiryDate)
                holder.pharmaceuticalItem!!.setOnClickListener {
                    // go to detail view with navigation + focusPharmaceuticalsObject
                    val input = AllRecordsFragmentDirections.viewRecordDetails(recordID, null,
                        null, null, null, null,
                        null, null, null, focusPharmaceuticals)
                    navControls.navigate(input)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        var recordsXListSize = 0
        when(recordID){
            1 ->{
                recordsXListSize = patientsList!!.size
            }
            2 ->{
                recordsXListSize = diagnosesList!!.size
            }
            3 ->{
                recordsXListSize = prescriptionsList!!.size
            }
            4 ->{
                recordsXListSize = visitsList!!.size
            }
            5 ->{
                recordsXListSize = practitionersList!!.size
            }
            6 ->{
                recordsXListSize = doctorsList!!.size
            }
            7 ->{
                recordsXListSize = registeredNursesList!!.size
            }
            8 ->{
                recordsXListSize = nursePractitionersList!!.size
            }
            9 ->{
                recordsXListSize = pharmaceuticalsList!!.size
            }
        }
        return recordsXListSize
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val navControl = recordsView.findNavController()
        //Patient List View
        val patientItem: MaterialCardView? = itemView.findViewById(R.id.patients_list_view)
        val patientShortName: TextView? = itemView.findViewById(R.id.patients_list_name_text)
        val patientInsurer: TextView? = itemView.findViewById(R.id.patients_list_insurer_text)
        val patientInsurerID: TextView? = itemView.findViewById(R.id.patients_list_insurer_subtext)
        val patientBloodGroup: TextView? = itemView.findViewById(R.id.patients_list_blood_group)
        val patientDiagnosesCountIcon: ImageView? = itemView.findViewById(R.id.patients_list_diagnoses_icon)
        val patientDiagnosesCount: TextView? = itemView.findViewById(R.id.patients_list_diagnoses_count)

        //Diagnosis List View
        val diagnosisItem: MaterialCardView? = itemView.findViewById(R.id.diagnosis_list_view)
        val diagnosisSynopsis: TextView? = itemView.findViewById(R.id.diagnosis_list_synopsis_text)
        val diagnosisPatientName: TextView? = itemView.findViewById(R.id.diagnosis_list_patient_text)
        val diagnosisDate: TextView? = itemView.findViewById(R.id.diagnosis_list_diagnosis_date)
        val diagnosisLevel: TextView? = itemView.findViewById(R.id.diagnosis_list_diagnosis_level)
        val diagnosisPrescriptionsIcon: ImageView? = itemView.findViewById(R.id.diagnosis_list_prescription_icon)
        val diagnosisPrescriptionsCount: TextView? = itemView.findViewById(R.id.diagnosis_list_prescription_count)
        val diagnosisVisitsIcon: ImageView? = itemView.findViewById(R.id.diagnosis_list_visit_icon)
        val diagnosisVisitsCount: TextView? = itemView.findViewById(R.id.diagnosis_list_visit_count)

        //Prescription List View
        val prescriptionItem: MaterialCardView? = itemView.findViewById(R.id.prescription_list_view)
        val prescriptionRxName: TextView? = itemView.findViewById(R.id.prescription_list_rx_name_text)
        val prescriptionPrescriberName: TextView? = itemView.findViewById(R.id.prescription_list_prescriber_text)
        val prescriptionPrescriberID: TextView? = itemView.findViewById(R.id.prescribtion_list_prescriber_id)
        val prescriptionPatientName: TextView? = itemView.findViewById(R.id.prescription_list_prescriber_patient)
        val prescriptionTypeIcon: ImageView? = itemView.findViewById(R.id.prescription_list_prescription_icon)

        //Visit List View
        val visitItem: MaterialCardView? = itemView.findViewById(R.id.visit_list_view)
        val visitHost: TextView? = itemView.findViewById(R.id.visit_list_host_practitioner_text)
        val visitHostID: TextView? = itemView.findViewById(R.id.visit_list_host_practitioner_id)
        val visitingPatient: TextView? = itemView.findViewById(R.id.visit_list_visit_patient)
        val visitDate: TextView? = itemView.findViewById(R.id.visit_list_visit_date)

        //Practitioner List View
        val practitionerItem: MaterialCardView? = itemView.findViewById(R.id.practitioner_list_view)
        val practitionersName: TextView? = itemView.findViewById(R.id.practitioner_list_practitioner_name_text)
        val practitionersID: TextView? = itemView.findViewById(R.id.practitioner_list_host_practitioner_id)
        val practitionersPhoneIcon: ImageView? = itemView.findViewById(R.id.practitioner_list_phone_icon)
        val practitionersEmailIcon: ImageView? = itemView.findViewById(R.id.practitioners_list_email_icon)

        //Doctor List View
        val doctorItem: MaterialCardView? = itemView.findViewById(R.id.doctor_list_view)
        val doctorsName: TextView? = itemView.findViewById(R.id.doctor_list_practitioner_name_text)
        val doctorPractitionersID: TextView? = itemView.findViewById(R.id.doctor_list_host_practitioner_id)
        val doctorPhoneIcon: ImageView? = itemView.findViewById(R.id.doctor_list_phone_icon)
        val doctorEmailIcon: ImageView? = itemView.findViewById(R.id.doctor_list_email_icon)
        val doctorSpeciality: TextView? = itemView.findViewById(R.id.doctor_list_practitioner_speciality)


        //Registered Nurse List View
        val registeredNurseItem: MaterialCardView? = itemView.findViewById(R.id.registered_nurses_list_view)
        val registeredNurseName: TextView? = itemView.findViewById(R.id.registered_nurse_list_practitioner_name_text)
        val registeredNursePractitionersID: TextView? = itemView.findViewById(R.id.registered_nurse_list_host_practitioner_id)
        val registeredNursePhoneIcon: ImageView? = itemView.findViewById(R.id.registered_nurse_list_phone_icon)
        val registeredNurseEmailIcon: ImageView? = itemView.findViewById(R.id.registered_nurse_list_email_icon)


        //Nurse Practitioner List View
        val nursePractitionerItem: MaterialCardView? = itemView.findViewById(R.id.nurse_practitioners_list_view)
        val nursePractitionerName: TextView? = itemView.findViewById(R.id.nurse_practitioner_list_practitioner_name_text)
        val nursePractitionerPractitionersID: TextView? = itemView.findViewById(R.id.nurse_practitioner_list_host_practitioner_id)
        val nursePractitionerPhoneIcon: ImageView? = itemView.findViewById(R.id.nurse_practitioner_list_phone_icon)
        val nursePractitionerEmailIcon: ImageView? = itemView.findViewById(R.id.nurse_practitioner_list_email_icon)


        //Pharmaceutical List View
        val pharmaceuticalItem: MaterialCardView? = itemView.findViewById(R.id.pharmaceutical_list_view)
        val pharmaceuticalsBrand: TextView? = itemView.findViewById(R.id.pharmaceutical_list_brand_text)
        val pharmaceuticalMaker: TextView? = itemView.findViewById(R.id.pharmaceutical_list_make_text)
        val pharmaceuticalMakeDate: TextView? = itemView.findViewById(R.id.pharmaceutical_list_make_date)
        val pharmaceuticalExpiryDate: TextView? = itemView.findViewById(R.id.pharmaceutical_list_expiry_text)
        val pharmaceuticalInventoryIcon: ImageView? = itemView.findViewById(R.id.pharmaceutical_list_inventory_icon)
        val pharmaceuticalInventoryCount: TextView? = itemView.findViewById(R.id.pharmaceutical_list_inventory_count)
        val pharmaceuticalLotIcon: ImageView? = itemView.findViewById(R.id.pharmaceutical_list_lot_icon)
        val pharmaceuticalLotNumber: TextView? = itemView.findViewById(R.id.pharmaceutical_list_lot_number)





    }


    private fun iconController(iconView: ImageView, countText: TextView, listSize: Int?){

        if (listSize!! < 1){
            iconView.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_deep_grey), android.graphics.PorterDuff.Mode.SRC_IN)
            countText.setTextColor(Color.parseColor("#989797"))

        }
        else if (listSize > 0){
            iconView.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_blue), android.graphics.PorterDuff.Mode.SRC_IN)
            countText.setTextColor(Color.parseColor("#0c204f"))

        }

    }


    private fun iconControllerS(iconView: ImageView, countText: TextView, string: String?){

        if (string.isNullOrBlank() || string.trim() == ""){
            iconView.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_deep_grey), android.graphics.PorterDuff.Mode.SRC_IN)
            countText.setTextColor(Color.parseColor("#989797"))

        }
        else if (!string.isNullOrBlank() || string.trim() != ""){
            iconView.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_blue), android.graphics.PorterDuff.Mode.SRC_IN)
            countText.setTextColor(Color.parseColor("#0c204f"))

        }

    }


    private fun infoIconController(info: String?, infoIcon: ImageView){
        if (info != null) {
            if (info.isBlank() || info.trim() == "") {
                infoIcon.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_grey), android.graphics.PorterDuff.Mode.SRC_IN)

            } else if (info.isNotBlank() || info.trim() != "") {
                infoIcon.setColorFilter(ContextCompat.getColor(recordsContext, R.color.chiron_blue), android.graphics.PorterDuff.Mode.SRC_IN)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun expiryController(expiryDateString: String?, expiryDateText: TextView?): String{
        var expireResult = ""
        val dateObject = LocalDate.parse(expiryDateString)
        val currentDate = LocalDate.now()


        if(dateObject.monthValue - currentDate.monthValue < 4){
            expireResult = "EXPIRING"
            expiryDateText!!.text = expireResult
            expiryDateText.setTextColor(Color.parseColor("#964B00"))
            expiryDateText.setTypeface(expiryDateText.typeface, Typeface.BOLD)
        }
        if (dateObject == currentDate || currentDate > dateObject ){
            expireResult = "EXPIRED"
            expiryDateText!!.text = expireResult
            expiryDateText.setTextColor(Color.parseColor("#800020"))
            expiryDateText.setTypeface(expiryDateText.typeface, Typeface.BOLD)

        }
        if (dateObject.monthValue - currentDate.monthValue > 3){
            val formatter = DateTimeFormatter.ofPattern("dd, MMMM yyyy")
            expireResult = dateObject.format(formatter)
            expiryDateText!!.setTextColor(Color.parseColor("#0c204f"))
            expiryDateText.setTypeface(expiryDateText.typeface, Typeface.NORMAL)

        }
        return expireResult
    }


    fun editChironRecord(position: Int, recordID: Int){
        val viewControls = recordsView.findNavController()
        when(recordID){
            1 ->{
                val focusPatient = patientsList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, focusPatient, null, null, null, null, null)
                viewControls.navigate(input)

            }
            2 ->{

            }
            3 ->{

            }
            4 ->{

            }
            5 ->{
                val focusPractitioner = practitionersList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null, focusPractitioner, null, null, null, null)
                viewControls.navigate(input)

            }
            6 ->{
                val focusDoctor = doctorsList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null, null, null, null, focusDoctor, null)
                viewControls.navigate(input)
            }
            7 ->{
                val focusRegisteredNurse = registeredNursesList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null, null, focusRegisteredNurse, null, null, null)
                viewControls.navigate(input)

            }
            8 ->{
                val focusNursePractitioner = nursePractitionersList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null, null, null, focusNursePractitioner, null, null)
                viewControls.navigate(input)
            }
            9 ->{
                val focusPharmaceutical = pharmaceuticalsList!![position]
                val input = AllRecordsFragmentDirections.editRecordDetails(recordID, null, null, null, null, null, focusPharmaceutical)
                viewControls.navigate(input)
            }
        }

    }


    fun deleteChironRecord(position: Int, recordID: Int){
        when(recordID){
            1 ->{
                val stagedPatient = patientsList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Patient Record from Chiron DB:\n $stagedPatient")
                }
                patientsList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Patient | ${stagedPatient.delimitedFullName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, patient = stagedPatient)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
            2 ->{
                val stagedDiagnosis = diagnosesList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Diagnosis Record from Chiron DB:\n $stagedDiagnosis")
                }
                diagnosesList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Diagnosis | ${stagedDiagnosis.diagnosisSynopsis!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, diagnosis = stagedDiagnosis)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()
            }
            3 ->{
                val stagedPrescription = prescriptionsList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Prescription Record from Chiron DB:\n $stagedPrescription")
                }
                prescriptionsList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Prescription | ${stagedPrescription.prescriptionName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, prescription = stagedPrescription)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
            4 ->{
                val stagedVisit = visitsList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Visit Record from Chiron DB:\n $stagedVisit")
                }
                visitsList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Visit Log | ${stagedVisit.hostPractitionerID!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, visit = stagedVisit)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
            5 ->{
                val stagedPractitioner = practitionersList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Practitioner Record from Chiron DB:\n $stagedPractitioner")
                }
                practitionersList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Practitioner Record | ${stagedPractitioner.delimitedFullName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, practitioner = stagedPractitioner)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()
            }
            6 ->{
                val stagedDoctor = doctorsList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Doctor Record from Chiron DB:\n $stagedDoctor")
                }
                doctorsList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Doctor Record | ${stagedDoctor.delimitedFullName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, doctor = stagedDoctor)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()
            }
            7 ->{
                val stagedRegisteredNurse = registeredNursesList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Registered Nurse Record from Chiron DB:\n $stagedRegisteredNurse")
                }
                registeredNursesList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Registered Nurse Record | ${stagedRegisteredNurse.delimitedFullName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, registeredNurse = stagedRegisteredNurse)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
            8 ->{
                val stagedNursePractitioner = nursePractitionersList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Nurse Practitioner Record from Chiron DB:\n $stagedNursePractitioner")
                }
                nursePractitionersList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Nurse Practitioner Record | ${stagedNursePractitioner.delimitedFullName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, nursePractitioner = stagedNursePractitioner)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
            9 ->{
                val stagedPharmaceuticals = pharmaceuticalsList!![position]
                subscribeOnBackground {
                    //pivotViewModel.deleteDataPivot(focusPivot)
                    println("Deleted Pharmaceutical Record from Chiron DB:\n $stagedPharmaceuticals")
                }
                pharmaceuticalsList.removeAt(position)
                notifyItemRemoved(position)
                val deletedPrompt = Snackbar.make(recordsView,"Deleted Pharmaceuticals Record | ${stagedPharmaceuticals.brandName!!.capitalize(
                    Locale.ROOT
                )
                }", Snackbar.LENGTH_LONG)
                deletedPrompt.setAction("UNDO", View.OnClickListener {

                    restoreRecord(recordID = recordID, position, pharmaceuticals = stagedPharmaceuticals)

                })
                deletedPrompt.anchorView = recordsView.rootView.findViewById(R.id.hub_foot_nav)
                deletedPrompt.show()

            }
        }

    }


    private fun restoreRecord(recordID: Int, position: Int, patient: Patient? = null, diagnosis: Diagnosis? = null,
                              prescription: Prescription? = null, visit: Visit? = null,
                              practitioner: Practitioner? = null, doctor: Doctor? = null,
                              registeredNurse: RegisteredNurse? = null, nursePractitioner: NursePractitioner? = null,
                              pharmaceuticals: Pharmaceuticals? = null){
        when(recordID){
            1 ->{
                patientsList!!.add(position, patient!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Patient Record to Chiron DB:\n $patient")
                }
                notifyItemInserted(position)
            }
            2 ->{
                diagnosesList!!.add(position, diagnosis!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Diagnosis Record to Chiron DB:\n $diagnosis")
                }
                notifyItemInserted(position)
            }
            3 ->{
                prescriptionsList!!.add(position, prescription!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Prescription Record to Chiron DB:\n $prescription")
                }
                notifyItemInserted(position)
            }
            4 ->{
                visitsList!!.add(position, visit!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Visit Record to Chiron DB:\n $visit")
                }
                notifyItemInserted(position)
            }
            5 ->{
                practitionersList!!.add(position, practitioner!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Practitioner Record to Chiron DB:\n $practitioner")
                }
                notifyItemInserted(position)
            }
            6 ->{
                doctorsList!!.add(position, doctor!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Doctor Record to Chiron DB:\n $doctor")
                }
                notifyItemInserted(position)
            }
            7 ->{
                registeredNursesList!!.add(position, registeredNurse!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Registered Nurse Record to Chiron DB:\n $registeredNurse")
                }
                notifyItemInserted(position)
            }
            8 ->{
                nursePractitionersList!!.add(position, nursePractitioner!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Nurse Practitioner Record to Chiron DB:\n $nursePractitioner")
                }
                notifyItemInserted(position)

            }
            9 ->{
                pharmaceuticalsList!!.add(position, pharmaceuticals!!)
                subscribeOnBackground {
                    //pivotViewModel.insertDataPivot(dataPivot)
                    println("Restoring Pharmaceutical Record to Chiron DB:\n $pharmaceuticals")
                }
                notifyItemInserted(position)
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