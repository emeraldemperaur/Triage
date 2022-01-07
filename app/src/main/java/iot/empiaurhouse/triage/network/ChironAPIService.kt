package iot.empiaurhouse.triage.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Single
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.utils.JSONHeaderInterceptor
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class ChironAPIService(val context: Context) {

    private lateinit var serverUrl: String
    private lateinit var userManager: UserPreferenceManager
    private val client = OkHttpClient.Builder().build()
    private var online: Boolean = false

    init {
        serverUrl = "https://chiron-cloudapp.herokuapp.com/"
        urlPrep()
    }

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()


    private val chironAPI = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideHttpClient())
        .build()
        .create(ChironAPIEndpoints::class.java)


    private fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(JSONHeaderInterceptor())
        return okHttpClientBuilder.build()
    }

    fun getChironStatus(): Single<List<APIStatus>>{
        return chironAPI.getChironAPIStatus()
    }

    fun getChironRecords(): Single<List<ChironRecords>>{
        return chironAPI.getChironRecords()
    }

    fun getChironPatients(): Single<List<Patient>>{
        return chironAPI.getChironPatients()
    }

    fun getChironPatientsByLN(lastName: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByLastName(lastName)
    }

    fun getChironPatientsByFN(firstName: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByFirstName(firstName)
    }

    fun getChironPatientsByBloodGroup(bloodGroup: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByBloodGroup(bloodGroup)
    }

    fun getChironPatientsByInsurer(insurer: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByInsuranceVendor(insurer)
    }

    fun getChironPatientsByInsurerID(insurerID: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByInsuranceVendorID(insurerID)
    }



    fun getChironPatientsByBirthDateOn(birthDate: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByBirthDate(birthDate)
    }

    fun getChironPatientsByBirthDateBefore(birthDate: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByBirthDateBefore(birthDate)
    }

    fun getChironPatientsByBirthDateAfter(birthDate: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByBirthDateAfter(birthDate)
    }

    fun getChironPatientsByBirthDateBetween(birthDateA: String, birthDateB: String): Single<List<Patient>>{
        return chironAPI.getChironPatientsByBirthDateBetween(birthDateA, birthDateB)
    }

    fun getChironDiagnoses(): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnoses()
    }

    fun getChironDiagnosesByDetails(diagnosisDetails: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByDiagnosisDetail(diagnosisDetails)
    }

    fun getChironDiagnosesBySynopsis(diagnosisSynopsis: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByDiagnosisSynopsis(diagnosisSynopsis)
    }

    fun getChironDiagnosesByLevel(diagnosisLevel: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByDiagnosisLevelName(diagnosisLevel)
    }

    fun getChironDiagnosesByInsurerID(insurerID: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByInsuranceVendorID(insurerID)
    }

    fun getChironDiagnosesByVisitOn(visitDate: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByVisitDate(visitDate)
    }

    fun getChironDiagnosesByVisitBefore(visitDate: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByVisitDateBefore(visitDate)
    }

    fun getChironDiagnosesByVisitAfter(visitDate: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByVisitDateAfter(visitDate)
    }

    fun getChironDiagnosesByVisitBetween(visitDateA: String, visitDateB: String): Single<List<Diagnosis>>{
        return chironAPI.getChironDiagnosesByVisitDateBetween(visitDateA, visitDateB)
    }

    fun getChironPrescriptions(): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptions()
    }

    fun getChironPrescriptionsByRxName(prescriptionName: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriptionName(prescriptionName)
    }

    fun getChironPrescriptionsByPrescriber(prescriberName: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriber(prescriberName)
    }

    fun getChironPrescriptionsByPrescriberID(prescriberID: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriberID(prescriberID)
    }

    fun getChironPrescriptionsByInsurerID(insurerID: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByInsuranceVendorID(insurerID)
    }

    fun getChironPrescriptionsByRxDateOn(visitDate: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriptionDate(visitDate)
    }

    fun getChironPrescriptionsByRxDateBefore(visitDate: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriptionDateBefore(visitDate)
    }

    fun getChironPrescriptionsByRxDateAfter(visitDate: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriptionDateAfter(visitDate)
    }

    fun getChironPrescriptionsByRxDateBetween(visitDateA: String, visitDateB: String): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptionsByPrescriptionDateBetween(visitDateA, visitDateB)
    }

    fun getChironVisits(): Single<List<Visit>>{
        return chironAPI.getChironVisits()
    }

    fun getChironVisitsByHost(hostPractitioner: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByHostPractitioner(hostPractitioner)
    }

    fun getChironVisitsByHostID(hostPractitionerID: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByHostPractitionerID(hostPractitionerID)
    }

    fun getChironVisitsByInsurerID(insurerID: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByInsuranceVendorID(insurerID)
    }

    fun getChironVisitsByVisitTime(visitTime: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitTime(visitTime)
    }

    fun getChironVisitsByVisitDescription(visitDescription: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitDescription(visitDescription)
    }

    fun getChironVisitsByVisitDateOn(visitDate: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitDate(visitDate)
    }

    fun getChironVisitsByVisitDateBefore(visitDate: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitDateBefore(visitDate)
    }

    fun getChironVisitsByVisitDateAfter(visitDate: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitDateAfter(visitDate)
    }

    fun getChironVisitsByVisitDateBetween(visitDateA: String, visitDateB: String): Single<List<Visit>>{
        return chironAPI.getChironVisitsByVisitDateBetween(visitDateA, visitDateB)
    }

    fun getChironPractitioners(): Single<List<Practitioner>>{
        return chironAPI.getChironPractitioners()
    }

    fun getChironPractitionersByFirstName(firstName: String): Single<List<Practitioner>>{
        return chironAPI.getChironPractitionersByFirstName(firstName)
    }

    fun getChironPractitionersByLastName(lastName: String): Single<List<Practitioner>>{
        return chironAPI.getChironPractitionersByLastName(lastName)
    }

    fun getChironPractitionersByPID(practitionerID: String): Single<List<Practitioner>>{
        return chironAPI.getChironPractitionersByPractitionerID(practitionerID)
    }

    fun getChironDoctors(): Single<List<Doctor>>{
        return chironAPI.getChironDoctors()
    }

    fun getChironDoctorsByFirstName(firstName: String): Single<List<Doctor>>{
        return chironAPI.getChironDoctorsByFirstName(firstName)
    }

    fun getChironDoctorsByLastName(lastName: String): Single<List<Doctor>>{
        return chironAPI.getChironDoctorsByLastName(lastName)
    }

    fun getChironDoctorsByPID(practitionerID: String): Single<List<Doctor>>{
        return chironAPI.getChironDoctorsByPractitionerID(practitionerID)
    }

    fun getChironNursePractitioners(): Single<List<NursePractitioner>>{
        return chironAPI.getChironNursePractitioners()
    }

    fun getChironNursePractitionersByFirstName(firstName: String): Single<List<NursePractitioner>>{
        return chironAPI.getChironNursePractitionersByFirstName(firstName)
    }

    fun getChironNursePractitionersByLastName(lastName: String): Single<List<NursePractitioner>>{
        return chironAPI.getChironNursePractitionersByLastName(lastName)
    }

    fun getChironNursePractitionersByPID(practitionerID: String): Single<List<NursePractitioner>>{
        return chironAPI.getChironNursePractitionersByPractitionerID(practitionerID)
    }

    fun getChironRegisteredNurses(): Single<List<RegisteredNurse>>{
        return chironAPI.getChironRegisteredNurses()
    }

    fun getChironRegisteredNursesByFirstName(firstName: String): Single<List<RegisteredNurse>>{
        return chironAPI.getChironRegisteredNursesByFirstName(firstName)
    }

    fun getChironRegisteredNursesByLastName(lastName: String): Single<List<RegisteredNurse>>{
        return chironAPI.getChironRegisteredNursesByLastName(lastName)
    }

    fun getChironRegisteredNursesByPID(practitionerID: String): Single<List<RegisteredNurse>>{
        return chironAPI.getChironRegisteredNursesByPractitionerID(practitionerID)
    }

    fun getChironPharmaceuticals(): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticals()
    }

    fun getChironPharmaceuticalsByBrandName(brandName: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByBrandName(brandName)
    }

    fun getChironPharmaceuticalsByGenericName(genericName: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByGenericName(genericName)
    }

    fun getChironPharmaceuticalsByChemicalName(chemicalName: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByChemicalName(chemicalName)
    }

    fun getChironPharmaceuticalsByManufactureName(manufactureName: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByManufactureName(manufactureName)
    }

    fun getChironPharmaceuticalsByManufactureDate(manufactureDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByManufactureDate(manufactureDate)
    }

    fun getChironPharmaceuticalsByManufactureDateBefore(manufactureDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByManufactureDateBefore(manufactureDate)
    }

    fun getChironPharmaceuticalsByManufactureDateAfter(manufactureDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByManufactureDateAfter(manufactureDate)
    }

    fun getChironPharmaceuticalsByManufactureDateBetween(manufactureDateA: String, manufactureDateB: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByManufactureDateBetween(manufactureDateA, manufactureDateB)
    }

    fun getChironPharmaceuticalsByExpiryDateOn(expiryDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByExpiryDate(expiryDate)
    }

    fun getChironPharmaceuticalsByExpiryDateBefore(expiryDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByExpiryDateBefore(expiryDate)
    }

    fun getChironPharmaceuticalsByExpiryDateAfter(expiryDate: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByExpiryDateAfter(expiryDate)
    }

    fun getChironPharmaceuticalsByExpiryDateBetween(expiryDateA: String, expiryDateB: String): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticalsByExpiryDateBetween(expiryDateA, expiryDateB)
    }

    fun postPatient(focusPatient: Patient): Call<Patient>{
        return chironAPI.postPatient(focusPatient)
    }

    fun deletePatient(focusPatient: Patient): Call<Patient>{
        return chironAPI.deletePatient(focusPatient)
    }

    fun deleteDiagnosis(focusDiagnosis: Diagnosis): Call<Diagnosis>{
        return chironAPI.deleteDiagnosis(focusDiagnosis)
    }

    fun postPractitioner(focusPractitioner: Practitioner): Call<Practitioner>{
        return chironAPI.postPractitioner(focusPractitioner)
    }

    fun deletePractitioner(focusPractitioner: Practitioner): Call<Practitioner>{
        return chironAPI.deletePractitioner(focusPractitioner)
    }

    fun postDoctor(focusDoctor: Doctor): Call<Doctor>{
        return chironAPI.postDoctor(focusDoctor)
    }

    fun deleteDoctor(focusDoctor: Doctor): Call<Doctor>{
        return chironAPI.deleteDoctor(focusDoctor)
    }

    fun postRegisteredNurse(focusRegisteredNurse: RegisteredNurse): Call<RegisteredNurse>{
        return chironAPI.postRegisteredNurse(focusRegisteredNurse)
    }

    fun deleteRegisteredNurse(focusRegisteredNurse: RegisteredNurse): Call<RegisteredNurse>{
        return chironAPI.deleteRegisteredNurse(focusRegisteredNurse)
    }

    fun postNursePractitioner(focusNursePractitioner: NursePractitioner): Call<NursePractitioner>{
        return chironAPI.postNursePractitioner(focusNursePractitioner)
    }

    fun deleteNursePractitioner(focusNursePractitioner: NursePractitioner): Call<NursePractitioner>{
        return chironAPI.deleteNursePractitioner(focusNursePractitioner)
    }

    fun postPharmaceuticals(focusPharmaceuticals: Pharmaceuticals): Call<Pharmaceuticals>{
        return chironAPI.postPharmaceutical(focusPharmaceuticals)
    }

    fun deletePharmaceuticals(focusPharmaceuticals: Pharmaceuticals): Call<Pharmaceuticals>{
        return chironAPI.deletePharmaceutical(focusPharmaceuticals)
    }


    private fun urlPrep(){
        userManager = UserPreferenceManager(context)
        var xServerUrl = userManager.getServerUrl().toString()
        println("\t\nThis is the init server Url found: " + xServerUrl)
        if (xServerUrl != null) {
            if (!xServerUrl.startsWith("http://") && !xServerUrl.startsWith("https://")) {
                xServerUrl = "https://$xServerUrl"
            }
            if (!xServerUrl.endsWith("/")) {
                xServerUrl = xServerUrl.plus("/")
            }
            serverUrl = xServerUrl
            println("\t\nThis is the prepped server Url found: " + serverUrl)
            if (serverUrl == null){
                serverUrl = "https://chiron-cloudapp.herokuapp.com/"
            }
        }
    }


}