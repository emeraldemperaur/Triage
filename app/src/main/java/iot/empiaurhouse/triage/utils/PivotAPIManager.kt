package iot.empiaurhouse.triage.utils

import android.content.Context
import android.os.Build
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
class PivotAPIManager(private val dataPivot: DataPivot, private val context: Context) {

    private var entityCode: Int? = null
    private var endPointCode: Int? = null
    private var timeStreamCode: Int? = null
    private var valueParameterCode: Int? = null
    private var practitionerCode: Int? = null
    private lateinit var valueParams: ArrayList<String>


    private val chironAPIService = ChironAPIService()
    private val patientDisposable = CompositeDisposable()
    private val patientDisposableII = CompositeDisposable()
    private val patientDisposableIII = CompositeDisposable()
    private val diagnosisDisposable = CompositeDisposable()
    private val diagnosisDisposableII = CompositeDisposable()
    private val diagnosisDisposableIII = CompositeDisposable()
    private val prescriptionDisposable = CompositeDisposable()
    private val prescriptionDisposableII = CompositeDisposable()
    private val prescriptionDisposableIII = CompositeDisposable()
    private val visitDisposable = CompositeDisposable()
    private val visitDisposableII = CompositeDisposable()
    private val visitDisposableIII = CompositeDisposable()
    private val practitionerDisposable = CompositeDisposable()
    private val doctorDisposable = CompositeDisposable()
    private val nursePractitionerDisposable = CompositeDisposable()
    private val registeredNurseDisposable = CompositeDisposable()
    private val pharmaceuticalDisposable = CompositeDisposable()

    val patientRecords = MutableLiveData<List<Patient>>()
    val patientRecordsII = MutableLiveData<List<Patient>>()
    val patientRecordsIII = MutableLiveData<List<Patient>>()
    val diagnosisRecords = MutableLiveData<List<Diagnosis>>()
    val diagnosisRecordsII = MutableLiveData<List<Diagnosis>>()
    val diagnosisRecordsIII = MutableLiveData<List<Diagnosis>>()
    val prescriptionRecords = MutableLiveData<List<Prescription>>()
    val prescriptionRecordsII = MutableLiveData<List<Prescription>>()
    val prescriptionRecordsIII = MutableLiveData<List<Prescription>>()
    val visitRecords = MutableLiveData<List<Visit>>()
    val visitRecordsII = MutableLiveData<List<Visit>>()
    val visitRecordsIII = MutableLiveData<List<Visit>>()
    val practitionerRecords = MutableLiveData<List<Practitioner>>()
    val doctorRecords = MutableLiveData<List<Doctor>>()
    val nursePractitionerRecords = MutableLiveData<List<NursePractitioner>>()
    val registeredNurseRecords = MutableLiveData<List<RegisteredNurse>>()
    val pharmaceuticalRecords = MutableLiveData<List<Pharmaceuticals>>()

    val patientError = MutableLiveData<Boolean>()
    val patientErrorII = MutableLiveData<Boolean>()
    val patientErrorIII = MutableLiveData<Boolean>()
    val diagnosisError = MutableLiveData<Boolean>()
    val diagnosisErrorII = MutableLiveData<Boolean>()
    val diagnosisErrorIII = MutableLiveData<Boolean>()
    val prescriptionError = MutableLiveData<Boolean>()
    val visitError = MutableLiveData<Boolean>()
    val practitionerError = MutableLiveData<Boolean>()
    val doctorError = MutableLiveData<Boolean>()
    val nursePractitionerError = MutableLiveData<Boolean>()
    val registeredNurseError = MutableLiveData<Boolean>()
    val pharmaceuticalError = MutableLiveData<Boolean>()

    val connecting = MutableLiveData<Boolean>()


    init {
        valueParams = arrayListOf()
        when(dataPivot.entityCode){
            1 ->{
                when(dataPivot.endPointCode){
                    1 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPatientRecordsByFN(valueParams)
                    }
                    2 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPatientRecordsByLN(valueParams)
                    }
                    3 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchPatientRecordsByDoB(valueParams)
                    }
                    4 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPatientRecordsByBloodGroup(valueParams)
                    }
                    5 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPatientRecordsByInsurer(valueParams)
                    }
                    6 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPatientRecordsByInsurerID(valueParams)
                    }
                }
            }
            2 ->{
                when(dataPivot.endPointCode){
                    10 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchDiagnosesRecordsBySynopsis(valueParams)
                    }
                    11 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchDiagnosisRecordsByVisitDate(valueParams)
                    }
                    12 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchDiagnosesRecordsByInsurerID(valueParams)
                    }
                    13 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchDiagnosesRecordsByLevel(valueParams)
                    }
                }
            }
            3 ->{

            }
            4 ->{

            }
            5 ->{

            }
            6 ->{
                when(dataPivot.practitionerCode){
                    10 ->{

                    }
                    20 ->{

                    }
                    30 ->{

                    }
                    40 ->{

                    }
                }

            }


        }

    }



    private fun fetchPatientRecordsByFN(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByFN(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

                patientDisposable.add(
                    chironAPIService.getChironPatientsByFN(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                patientDisposableII.add(
                    chironAPIService.getChironPatientsByFN(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsII.value = reply
                                patientErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                patientDisposableIII.add(
                    chironAPIService.getChironPatientsByFN(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsIII.value = reply
                                patientErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPatientRecordsByLN(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByLN(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            patientDisposable.add(
                chironAPIService.getChironPatientsByLN(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                patientDisposableII.add(
                    chironAPIService.getChironPatientsByLN(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsII.value = reply
                                patientErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                patientDisposableIII.add(
                    chironAPIService.getChironPatientsByLN(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsIII.value = reply
                                patientErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPatientRecordsByDoB(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBirthDateOn(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBirthDateBefore(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBirthDateAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBirthDateBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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

    private fun fetchPatientRecordsByBloodGroup(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByBloodGroup(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            patientDisposable.add(
                chironAPIService.getChironPatientsByBloodGroup(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                patientDisposableII.add(
                    chironAPIService.getChironPatientsByBloodGroup(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsII.value = reply
                                patientErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                patientDisposableIII.add(
                    chironAPIService.getChironPatientsByBloodGroup(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsIII.value = reply
                                patientErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPatientRecordsByInsurer(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByInsurer(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            patientDisposable.add(
                chironAPIService.getChironPatientsByInsurer(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                patientDisposableII.add(
                    chironAPIService.getChironPatientsByInsurer(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsII.value = reply
                                patientErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                patientDisposableIII.add(
                    chironAPIService.getChironPatientsByInsurer(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsIII.value = reply
                                patientErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPatientRecordsByInsurerID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            patientDisposable.add(
                chironAPIService.getChironPatientsByInsurerID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            patientDisposable.add(
                chironAPIService.getChironPatientsByInsurerID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                patientDisposableII.add(
                    chironAPIService.getChironPatientsByInsurerID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsII.value = reply
                                patientErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                patientDisposableIII.add(
                    chironAPIService.getChironPatientsByInsurerID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                            override fun onSuccess(reply: List<Patient>) {
                                patientRecordsIII.value = reply
                                patientErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                patientErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDiagnosesRecordsBySynopsis(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesBySynopsis(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesBySynopsis(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                diagnosisDisposableII.add(
                    chironAPIService.getChironDiagnosesBySynopsis(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsII.value = reply
                                diagnosisErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                diagnosisDisposableIII.add(
                    chironAPIService.getChironDiagnosesBySynopsis(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsIII.value = reply
                                diagnosisErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDiagnosesRecordsByLevel(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByLevel(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByLevel(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                diagnosisDisposableII.add(
                    chironAPIService.getChironDiagnosesByLevel(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsII.value = reply
                                diagnosisErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                diagnosisDisposableIII.add(
                    chironAPIService.getChironDiagnosesByLevel(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsIII.value = reply
                                diagnosisErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDiagnosesRecordsByInsurerID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByInsurerID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByInsurerID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                diagnosisDisposableII.add(
                    chironAPIService.getChironDiagnosesByInsurerID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsII.value = reply
                                diagnosisErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                diagnosisDisposableIII.add(
                    chironAPIService.getChironDiagnosesByInsurerID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Diagnosis>>() {
                            override fun onSuccess(reply: List<Diagnosis>) {
                                diagnosisRecordsIII.value = reply
                                diagnosisErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                diagnosisErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchDiagnosisRecordsByVisitDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByVisitOn(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByVisitBefore(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByVisitAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            diagnosisDisposable.add(
                chironAPIService.getChironDiagnosesByVisitBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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
    fun reformatDateString(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        return date.format(outputFormatter)
    }





}