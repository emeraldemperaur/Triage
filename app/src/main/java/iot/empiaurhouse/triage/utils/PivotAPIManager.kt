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


    private var valueParams: ArrayList<String>
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
                when(dataPivot.endPointCode){
                    25 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPharmaceuticalRecordsByBrandName(valueParams)
                    }
                    26 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPharmaceuticalRecordsByGenericName(valueParams)
                    }
                    27 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPharmaceuticalRecordsByChemicalName(valueParams)
                    }
                    28 ->{
                        valueParams.add(dataPivot.valueParameterA!!)
                        valueParams.add(dataPivot.valueParameterB!!)
                        valueParams.add(dataPivot.valueParameterC!!)
                        fetchPharmaceuticalRecordsByManufacturerName(valueParams)
                    }
                    29 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchPharmaceuticalRecordsByManufactureDate(valueParams)
                    }
                    30 ->{
                        valueParams.add(dataPivot.dateParameterA!!)
                        valueParams.add(dataPivot.dateParameterB!!)
                        fetchPharmaceuticalRecordsByExpiryDate(valueParams)
                    }
                }
            }
            6 ->{
                when(dataPivot.practitionerCode){
                    10 ->{
                        when(dataPivot.endPointCode){
                            7 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchPractitionerRecordsByFirstName(valueParams)
                            }
                            8 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchPractitionerRecordsByLastName(valueParams)
                            }
                            9 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchPractitionerRecordsByPID(valueParams)
                            }
                        }
                    }
                    20 ->{
                        when(dataPivot.endPointCode){
                            7 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchDoctorRecordsByFirstName(valueParams)
                            }
                            8 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchDoctorRecordsByLastName(valueParams)
                            }
                            9 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchDoctorRecordsByPID(valueParams)
                            }
                        }
                    }
                    30 ->{
                        when(dataPivot.endPointCode){
                            7 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchNursePractitionerRecordsByFirstName(valueParams)
                            }
                            8 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchNursePractitionerRecordsByLastName(valueParams)
                            }
                            9 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchNursePractitionerRecordsByPID(valueParams)
                            }
                        }
                    }
                    40 ->{
                        when(dataPivot.endPointCode){
                            7 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchRegisteredNurseRecordsByFirstName(valueParams)
                            }
                            8 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchRegisteredNurseRecordsByLastName(valueParams)
                            }
                            9 ->{
                                valueParams.add(dataPivot.valueParameterA!!)
                                valueParams.add(dataPivot.valueParameterB!!)
                                valueParams.add(dataPivot.valueParameterC!!)
                                fetchRegisteredNurseRecordsByPID(valueParams)
                            }
                        }
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

    private fun fetchPractitionerRecordsByFirstName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                practitionerDisposableII.add(
                    chironAPIService.getChironPractitionersByFirstName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsII.value = reply
                                practitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                practitionerDisposableIII.add(
                    chironAPIService.getChironPractitionersByFirstName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsIII.value = reply
                                practitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPractitionerRecordsByLastName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                practitionerDisposableII.add(
                    chironAPIService.getChironPractitionersByLastName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsII.value = reply
                                practitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                practitionerDisposableIII.add(
                    chironAPIService.getChironPractitionersByLastName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsIII.value = reply
                                practitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPractitionerRecordsByPID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            practitionerDisposable.add(
                chironAPIService.getChironPractitionersByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                        override fun onSuccess(reply: List<Practitioner>) {
                            practitionerRecords.value = reply
                            practitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            practitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                practitionerDisposableII.add(
                    chironAPIService.getChironPractitionersByPID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsII.value = reply
                                practitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                practitionerDisposableIII.add(
                    chironAPIService.getChironPractitionersByPID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Practitioner>>() {
                            override fun onSuccess(reply: List<Practitioner>) {
                                practitionerRecordsIII.value = reply
                                practitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                practitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDoctorRecordsByFirstName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            doctorDisposable.add(
                chironAPIService.getChironDoctorsByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            doctorDisposable.add(
                chironAPIService.getChironDoctorsByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                doctorDisposableII.add(
                    chironAPIService.getChironDoctorsByFirstName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsII.value = reply
                                doctorErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                doctorDisposableIII.add(
                    chironAPIService.getChironDoctorsByFirstName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsIII.value = reply
                                doctorErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDoctorRecordsByLastName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            doctorDisposable.add(
                chironAPIService.getChironDoctorsByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            doctorDisposable.add(
                chironAPIService.getChironDoctorsByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                doctorDisposableII.add(
                    chironAPIService.getChironDoctorsByLastName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsII.value = reply
                                doctorErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                doctorDisposableIII.add(
                    chironAPIService.getChironDoctorsByLastName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsIII.value = reply
                                doctorErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchDoctorRecordsByPID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            doctorDisposable.add(
                chironAPIService.getChironDoctorsByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            doctorDisposable.add(
                chironAPIService.getChironDoctorsByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                        override fun onSuccess(reply: List<Doctor>) {
                            doctorRecords.value = reply
                            doctorError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            doctorError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                doctorDisposableII.add(
                    chironAPIService.getChironDoctorsByPID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsII.value = reply
                                doctorErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                doctorDisposableIII.add(
                    chironAPIService.getChironDoctorsByPID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Doctor>>() {
                            override fun onSuccess(reply: List<Doctor>) {
                                doctorRecordsIII.value = reply
                                doctorErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                doctorErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchNursePractitionerRecordsByFirstName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                nursePractitionerDisposableII.add(
                    chironAPIService.getChironNursePractitionersByFirstName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsII.value = reply
                                nursePractitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                nursePractitionerDisposableIII.add(
                    chironAPIService.getChironNursePractitionersByFirstName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsIII.value = reply
                                nursePractitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchNursePractitionerRecordsByLastName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                nursePractitionerDisposableII.add(
                    chironAPIService.getChironNursePractitionersByLastName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsII.value = reply
                                nursePractitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                nursePractitionerDisposableIII.add(
                    chironAPIService.getChironNursePractitionersByLastName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsIII.value = reply
                                nursePractitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchNursePractitionerRecordsByPID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            nursePractitionerDisposable.add(
                chironAPIService.getChironNursePractitionersByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                        override fun onSuccess(reply: List<NursePractitioner>) {
                            nursePractitionerRecords.value = reply
                            nursePractitionerError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            nursePractitionerError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                nursePractitionerDisposableII.add(
                    chironAPIService.getChironNursePractitionersByPID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsII.value = reply
                                nursePractitionerErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                nursePractitionerDisposableIII.add(
                    chironAPIService.getChironNursePractitionersByPID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<NursePractitioner>>() {
                            override fun onSuccess(reply: List<NursePractitioner>) {
                                nursePractitionerRecordsIII.value = reply
                                nursePractitionerErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                nursePractitionerErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchRegisteredNurseRecordsByFirstName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByFirstName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                registeredNurseDisposableII.add(
                    chironAPIService.getChironRegisteredNursesByFirstName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsII.value = reply
                                registeredNurseErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                registeredNurseDisposableIII.add(
                    chironAPIService.getChironRegisteredNursesByFirstName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsIII.value = reply
                                registeredNurseErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchRegisteredNurseRecordsByLastName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByLastName(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                registeredNurseDisposableII.add(
                    chironAPIService.getChironRegisteredNursesByLastName(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsII.value = reply
                                registeredNurseErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                registeredNurseDisposableIII.add(
                    chironAPIService.getChironRegisteredNursesByLastName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsIII.value = reply
                                registeredNurseErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchRegisteredNurseRecordsByPID(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())

                        }

                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }

                    })
            )
        }
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            registeredNurseDisposable.add(
                chironAPIService.getChironRegisteredNursesByPID(valueParams.first())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                        override fun onSuccess(reply: List<RegisteredNurse>) {
                            registeredNurseRecords.value = reply
                            registeredNurseError.value = false
                            connecting.value = false
                            println(reply.toString())
                        }
                        override fun onError(e: Throwable) {
                            registeredNurseError.value = true
                            connecting.value = false
                            e.printStackTrace()
                        }
                    })
            )

            if (valueParameters[1].isNotBlank()){
                registeredNurseDisposableII.add(
                    chironAPIService.getChironRegisteredNursesByPID(valueParams[1])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsII.value = reply
                                registeredNurseErrorII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }

            if (valueParameters[2].isNotBlank()){
                registeredNurseDisposableIII.add(
                    chironAPIService.getChironRegisteredNursesByPID(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<RegisteredNurse>>() {
                            override fun onSuccess(reply: List<RegisteredNurse>) {
                                registeredNurseRecordsIII.value = reply
                                registeredNurseErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                registeredNurseErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPharmaceuticalRecordsByBrandName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByBrandName(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByBrandName(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                pharmaceuticalDisposableII.add(
                    chironAPIService.getChironPharmaceuticalsByBrandName(valueParams[1])
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

            if (valueParameters[2].isNotBlank()){
                pharmaceuticalDisposableIII.add(
                    chironAPIService.getChironPharmaceuticalsByBrandName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                            override fun onSuccess(reply: List<Pharmaceuticals>) {
                                pharmaceuticalRecordsIII.value = reply
                                pharmaceuticalErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                pharmaceuticalErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPharmaceuticalRecordsByGenericName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByGenericName(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByGenericName(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                pharmaceuticalDisposableII.add(
                    chironAPIService.getChironPharmaceuticalsByGenericName(valueParams[1])
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

            if (valueParameters[2].isNotBlank()){
                pharmaceuticalDisposableIII.add(
                    chironAPIService.getChironPharmaceuticalsByGenericName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                            override fun onSuccess(reply: List<Pharmaceuticals>) {
                                pharmaceuticalRecordsIII.value = reply
                                pharmaceuticalErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                pharmaceuticalErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPharmaceuticalRecordsByChemicalName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByChemicalName(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByChemicalName(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                pharmaceuticalDisposableII.add(
                    chironAPIService.getChironPharmaceuticalsByChemicalName(valueParams[1])
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

            if (valueParameters[2].isNotBlank()){
                pharmaceuticalDisposableIII.add(
                    chironAPIService.getChironPharmaceuticalsByChemicalName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                            override fun onSuccess(reply: List<Pharmaceuticals>) {
                                pharmaceuticalRecordsIII.value = reply
                                pharmaceuticalErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                pharmaceuticalErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    private fun fetchPharmaceuticalRecordsByManufacturerName(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.valueParamCode != null && dataPivot.valueParamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureName(valueParams.first())
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
        else if (dataPivot.valueParamCode != null && dataPivot.valueParamCode > 1) {

            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureName(valueParams.first())
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

            if (valueParameters[1].isNotBlank()){
                pharmaceuticalDisposableII.add(
                    chironAPIService.getChironPharmaceuticalsByManufactureName(valueParams[1])
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

            if (valueParameters[2].isNotBlank()){
                pharmaceuticalDisposableIII.add(
                    chironAPIService.getChironPharmaceuticalsByManufactureName(valueParams[2])
                        .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Pharmaceuticals>>() {
                            override fun onSuccess(reply: List<Pharmaceuticals>) {
                                pharmaceuticalRecordsIII.value = reply
                                pharmaceuticalErrorIII.value = false
                                connecting.value = false
                                println(reply.toString())
                            }
                            override fun onError(e: Throwable) {
                                pharmaceuticalErrorIII.value = true
                                connecting.value = false
                                e.printStackTrace()
                            }
                        })
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPharmaceuticalRecordsByManufactureDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureDate(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureDateBefore(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureDateAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByManufactureDateBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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
    private fun fetchPharmaceuticalRecordsByExpiryDate(valueParameters: ArrayList<String>) {
        connecting.value = true
        valueParams = valueParameters
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 1) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByExpiryDateOn(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 2) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByExpiryDateBefore(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 3) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByExpiryDateAfter(reformatDateString(dataPivot.dateParameterA!!))
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
        if (dataPivot.timeStreamCode != null && dataPivot.timeStreamCode == 4) {
            pharmaceuticalDisposable.add(
                chironAPIService.getChironPharmaceuticalsByExpiryDateBetween(reformatDateString(dataPivot.dateParameterA!!), reformatDateString(dataPivot.dateParameterB!!))
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