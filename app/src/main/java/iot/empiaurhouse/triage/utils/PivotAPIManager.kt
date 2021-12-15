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
    private val practitionerDisposableII = CompositeDisposable()
    private val practitionerDisposableIII = CompositeDisposable()
    private val doctorDisposable = CompositeDisposable()
    private val doctorDisposableII = CompositeDisposable()
    private val doctorDisposableIII = CompositeDisposable()
    private val nursePractitionerDisposable = CompositeDisposable()
    private val nursePractitionerDisposableII = CompositeDisposable()
    private val nursePractitionerDisposableIII = CompositeDisposable()
    private val registeredNurseDisposable = CompositeDisposable()
    private val registeredNurseDisposableII = CompositeDisposable()
    private val registeredNurseDisposableIII = CompositeDisposable()
    private val pharmaceuticalDisposable = CompositeDisposable()
    private val pharmaceuticalDisposableII = CompositeDisposable()
    private val pharmaceuticalDisposableIII = CompositeDisposable()

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
    val practitionerRecordsII = MutableLiveData<List<Practitioner>>()
    val practitionerRecordsIII = MutableLiveData<List<Practitioner>>()
    val doctorRecords = MutableLiveData<List<Doctor>>()
    val doctorRecordsII = MutableLiveData<List<Doctor>>()
    val doctorRecordsIII = MutableLiveData<List<Doctor>>()
    val nursePractitionerRecords = MutableLiveData<List<NursePractitioner>>()
    val nursePractitionerRecordsII = MutableLiveData<List<NursePractitioner>>()
    val nursePractitionerRecordsIII = MutableLiveData<List<NursePractitioner>>()
    val registeredNurseRecords = MutableLiveData<List<RegisteredNurse>>()
    val registeredNurseRecordsII = MutableLiveData<List<RegisteredNurse>>()
    val registeredNurseRecordsIII = MutableLiveData<List<RegisteredNurse>>()
    val pharmaceuticalRecords = MutableLiveData<List<Pharmaceuticals>>()
    val pharmaceuticalRecordsII = MutableLiveData<List<Pharmaceuticals>>()
    val pharmaceuticalRecordsIII = MutableLiveData<List<Pharmaceuticals>>()

    val patientError = MutableLiveData<Boolean>()
    val patientErrorII = MutableLiveData<Boolean>()
    val patientErrorIII = MutableLiveData<Boolean>()
    val diagnosisError = MutableLiveData<Boolean>()
    val diagnosisErrorII = MutableLiveData<Boolean>()
    val diagnosisErrorIII = MutableLiveData<Boolean>()
    val prescriptionError = MutableLiveData<Boolean>()
    val prescriptionErrorII = MutableLiveData<Boolean>()
    val prescriptionErrorIII = MutableLiveData<Boolean>()
    val visitError = MutableLiveData<Boolean>()
    val visitErrorII = MutableLiveData<Boolean>()
    val visitErrorIII = MutableLiveData<Boolean>()
    val practitionerError = MutableLiveData<Boolean>()
    val practitionerErrorII = MutableLiveData<Boolean>()
    val practitionerErrorIII = MutableLiveData<Boolean>()
    val doctorError = MutableLiveData<Boolean>()
    val doctorErrorII = MutableLiveData<Boolean>()
    val doctorErrorIII = MutableLiveData<Boolean>()
    val nursePractitionerError = MutableLiveData<Boolean>()
    val nursePractitionerErrorII = MutableLiveData<Boolean>()
    val nursePractitionerErrorIII = MutableLiveData<Boolean>()
    val registeredNurseError = MutableLiveData<Boolean>()
    val registeredNurseErrorII = MutableLiveData<Boolean>()
    val registeredNurseErrorIII = MutableLiveData<Boolean>()
    val pharmaceuticalError = MutableLiveData<Boolean>()
    val pharmaceuticalErrorII = MutableLiveData<Boolean>()
    val pharmaceuticalErrorIII = MutableLiveData<Boolean>()

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
                when(dataPivot.endPointCode){
                    14 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPrescriptionRecordsByRxName(valueParams)
                    }
                    15 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPrescriptionRecordsByPrescriber(valueParams)
                    }
                    16 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPrescriptionRecordsByPrescriberID(valueParams)
                    }
                    17 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPrescriptionRecordsByInsurerID(valueParams)
                    }
                    18 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchPrescriptionRecordsByRxDate(valueParams)
                    }
                }
            }
            4 ->{
                when(dataPivot.endPointCode){
                    19 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchVisitRecordsByVisitHost(valueParams)
                    }
                    20 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchVisitRecordsByVisitHostID(valueParams)
                    }
                    21 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchVisitRecordsByVisitTime(valueParams)
                    }
                    22 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchVisitRecordsByVisitDescription(valueParams)
                    }
                    23 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchVisitRecordsByVisitDate(valueParams)
                    }
                    24 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchVisitRecordsByInsurerID(valueParams)
                    }
                }
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


    private fun fetchPrescriptionRecordsByRxName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxName(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxName(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                prescriptionDisposableII.add(
                    chironAPIService.getChironPrescriptionsByRxName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsII.value = reply
                                prescriptionErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                prescriptionDisposableIII.add(
                    chironAPIService.getChironPrescriptionsByRxName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsIII.value = reply
                                prescriptionErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPrescriptionRecordsByPrescriber(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByPrescriber(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByPrescriber(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                prescriptionDisposableII.add(
                    chironAPIService.getChironPrescriptionsByPrescriber(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsII.value = reply
                                prescriptionErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                prescriptionDisposableIII.add(
                    chironAPIService.getChironPrescriptionsByPrescriber(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsIII.value = reply
                                prescriptionErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPrescriptionRecordsByPrescriberID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByPrescriberID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByPrescriberID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                prescriptionDisposableII.add(
                    chironAPIService.getChironPrescriptionsByPrescriberID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsII.value = reply
                                prescriptionErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                prescriptionDisposableIII.add(
                    chironAPIService.getChironPrescriptionsByPrescriberID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsIII.value = reply
                                prescriptionErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPrescriptionRecordsByInsurerID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByInsurerID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByInsurerID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                prescriptionDisposableII.add(
                    chironAPIService.getChironPrescriptionsByInsurerID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsII.value = reply
                                prescriptionErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                prescriptionDisposableIII.add(
                    chironAPIService.getChironPrescriptionsByInsurerID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Prescription>>() {
                            override fun onSuccess(reply: List<Prescription>) {
                                prescriptionRecordsIII.value = reply
                                prescriptionErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                prescriptionErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPrescriptionRecordsByRxDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxDateOn(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxDateBefore(reformatDateString(dataPivot.dateParameterA!!))
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
                            diagnosisError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxDateAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            prescriptionDisposable.add(
                chironAPIService.getChironPrescriptionsByRxDateBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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

    private fun fetchVisitRecordsByVisitHost(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByHost(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            visitDisposable.add(
                chironAPIService.getChironVisitsByHost(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                visitDisposableII.add(
                    chironAPIService.getChironVisitsByHost(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsII.value = reply
                                visitErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                visitDisposableIII.add(
                    chironAPIService.getChironVisitsByHost(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsIII.value = reply
                                visitErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchVisitRecordsByVisitHostID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByHostID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            visitDisposable.add(
                chironAPIService.getChironVisitsByHostID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                visitDisposableII.add(
                    chironAPIService.getChironVisitsByHostID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsII.value = reply
                                visitErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                visitDisposableIII.add(
                    chironAPIService.getChironVisitsByHostID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsIII.value = reply
                                visitErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchVisitRecordsByVisitDescription(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDescription(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDescription(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                visitDisposableII.add(
                    chironAPIService.getChironVisitsByVisitDescription(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsII.value = reply
                                visitErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                visitDisposableIII.add(
                    chironAPIService.getChironVisitsByVisitDescription(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsIII.value = reply
                                visitErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchVisitRecordsByInsurerID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByInsurerID(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            visitDisposable.add(
                chironAPIService.getChironVisitsByInsurerID(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                visitDisposableII.add(
                    chironAPIService.getChironVisitsByInsurerID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsII.value = reply
                                visitErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                visitDisposableIII.add(
                    chironAPIService.getChironVisitsByInsurerID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsIII.value = reply
                                visitErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchVisitRecordsByVisitTime(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitTime(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitTime(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                visitDisposableII.add(
                    chironAPIService.getChironVisitsByVisitTime(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsII.value = reply
                                visitErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                visitDisposableIII.add(
                    chironAPIService.getChironVisitsByVisitTime(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Visit>>() {
                            override fun onSuccess(reply: List<Visit>) {
                                visitRecordsIII.value = reply
                                visitErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                visitErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchVisitRecordsByVisitDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDateOn(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDateBefore(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDateAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            visitDisposable.add(
                chironAPIService.getChironVisitsByVisitDateBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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
    fun reformatDateString(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        return date.format(outputFormatter)
    }





}