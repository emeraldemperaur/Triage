package iot.empiaurhouse.triage.utils

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.network.ChironAPIService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class InsightAPIManager(private val insightModel: InsightModel, private val context: Context) {
    private lateinit var valueParams: ArrayList<String>
    private val chironAPIService = ChironAPIService()
    private val patientDisposable = CompositeDisposable()
    private val diagnosisDisposable = CompositeDisposable()
    private val prescriptionDisposable = CompositeDisposable()
    private val visitDisposable = CompositeDisposable()
    private val pharmaceuticalDisposable = CompositeDisposable()

    val patientRecords = MutableLiveData<List<Patient>>()
    val diagnosisRecords = MutableLiveData<List<Diagnosis>>()
    val prescriptionRecords = MutableLiveData<List<Prescription>>()
    val visitRecords = MutableLiveData<List<Visit>>()
    val pharmaceuticalRecords = MutableLiveData<List<Pharmaceuticals>>()
    val pharmaceuticalRecordsII = MutableLiveData<List<Pharmaceuticals>>()

    val patientError = MutableLiveData<Boolean>()
    val diagnosisError = MutableLiveData<Boolean>()
    val prescriptionError = MutableLiveData<Boolean>()
    val visitError = MutableLiveData<Boolean>()
    val pharmaceuticalError = MutableLiveData<Boolean>()
    val pharmaceuticalErrorII = MutableLiveData<Boolean>()

    val connecting = MutableLiveData<Boolean>()

    init {
        valueParams = arrayListOf()
        when(insightModel.entityCode){
            1 ->{
                valueParams.add(insightModel.rangeStartDate)
                valueParams.add(insightModel.rangeEndDate)
                fetchPatientRecordsByDoB(valueParams)
            }
            2 ->{
                valueParams.add(insightModel.rangeStartDate)
                valueParams.add(insightModel.rangeEndDate)
                fetchDiagnosisRecordsByVisitDate(valueParams)
            }
            3 ->{
                valueParams.add(insightModel.rangeStartDate)
                valueParams.add(insightModel.rangeEndDate)
                fetchPrescriptionRecordsByRxDate(valueParams)

            }
            4 ->{
                valueParams.add(insightModel.rangeStartDate)
                valueParams.add(insightModel.rangeEndDate)
                fetchVisitRecordsByVisitDate(valueParams)

            }
            5 ->{
                valueParams.add(insightModel.rangeStartDate)
                valueParams.add(insightModel.rangeEndDate)
                fetchPharmaceuticalRecordsByExpiryDate(valueParams)
                Handler(Looper.getMainLooper()).postDelayed({
                    fetchPharmaceuticalRecordsByManufactureDate(valueParams)
                }, 1111)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPatientRecordsByDoB(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBirthDateBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                        override fun onSuccess(reply: List<Patient>) {
                            patientRecords.value = reply
                            patientError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            patientError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchDiagnosisRecordsByVisitDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 2) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByVisitBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                        override fun onSuccess(reply: List<Diagnosis>) {
                            diagnosisRecords.value = reply
                            diagnosisError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            diagnosisError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPrescriptionRecordsByRxDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 3) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxDateBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                        override fun onSuccess(reply: List<Prescription>) {
                            prescriptionRecords.value = reply
                            prescriptionError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            prescriptionError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchVisitRecordsByVisitDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 4) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDateBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                        override fun onSuccess(reply: List<Visit>) {
                            visitRecords.value = reply
                            visitError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            visitError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPharmaceuticalRecordsByManufactureDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 5) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureDateBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                        override fun onSuccess(reply: List<Pharmaceuticals>) {
                            pharmaceuticalRecordsII.value = reply
                            pharmaceuticalErrorII.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            pharmaceuticalErrorII.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPharmaceuticalRecordsByExpiryDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (insightModel.entityCode == 5) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByExpiryDateBetween(reformatDateString(insightModel.rangeStartDate), reformatDateString(insightModel.rangeEndDate))
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                        override fun onSuccess(reply: List<Pharmaceuticals>) {
                            pharmaceuticalRecords.value = reply
                            pharmaceuticalError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            pharmaceuticalError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun reformatDateString(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        return date.format(outputFormatter)
    }



}