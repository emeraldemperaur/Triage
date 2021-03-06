package iot.empiaurhouse.triage.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Single
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.utils.JSONHeaderInterceptor
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class ChironAPIService {

    private var serverUrl: String = "https://www.test.com"
    private lateinit var userManager: UserPreferenceManager
    private val client = OkHttpClient.Builder().build()
    private var online: Boolean = false

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

    fun getChironPrescriptions(): Single<List<Prescription>>{
        return chironAPI.getChironPrescriptions()
    }

    fun getChironVisits(): Single<List<Visit>>{
        return chironAPI.getChironVisits()
    }

    fun getChironPractitioners(): Single<List<Practitioner>>{
        return chironAPI.getChironPractitioners()
    }

    fun getChironDoctors(): Single<List<Doctor>>{
        return chironAPI.getChironDoctors()
    }

    fun getChironNursePractitioners(): Single<List<NursePractitioner>>{
        return chironAPI.getChironNursePractitioners()
    }

    fun getChironRegisteredNurses(): Single<List<RegisteredNurse>>{
        return chironAPI.getChironRegisteredNurses()
    }

    fun getChironPharmaceuticals(): Single<List<Pharmaceuticals>>{
        return chironAPI.getChironPharmaceuticals()
    }






    fun initServerUrl(context: Context){
        userManager = UserPreferenceManager(context)
        serverUrl = userManager.getServerUrl().toString()
        if (!serverUrl.endsWith("/")){
            serverUrl = serverUrl.plus("/")
            println("This is the init server Url found: " + serverUrl)
        }
        if(!serverUrl.startsWith("http://") || !serverUrl.startsWith("https://")){
            serverUrl = "https://$serverUrl"
            println("This is the init server Url found: " + serverUrl)
        }


    }


}