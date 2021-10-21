package iot.empiaurhouse.triage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.*
import iot.empiaurhouse.triage.network.ChironAPIService

class ChironRecordsViewModel: ViewModel() {
    private val chironAPIService = ChironAPIService()
    private val patientDisposable = CompositeDisposable()
    private val diagnosisDisposable = CompositeDisposable()
    private val prescriptionDisposable = CompositeDisposable()
    private val visitDisposable = CompositeDisposable()
    private val practitionerDisposable = CompositeDisposable()
    private val doctorDisposable = CompositeDisposable()
    private val nursePractitionerDisposable = CompositeDisposable()
    private val registeredNurseDisposable = CompositeDisposable()
    private val pharmaceuticalDisposable = CompositeDisposable()

    val patientRecords = MutableLiveData<List<Patient>>()
    val diagnosisRecords = MutableLiveData<List<Diagnosis>>()
    val prescriptionRecords = MutableLiveData<List<Prescription>>()
    val visitRecords = MutableLiveData<List<Visit>>()
    val practitionerRecords = MutableLiveData<List<Practitioner>>()
    val doctorRecords = MutableLiveData<List<Doctor>>()
    val nursePractitionerRecords = MutableLiveData<List<NursePractitioner>>()
    val registeredNurseRecords = MutableLiveData<List<RegisteredNurse>>()
    val pharmaceuticalRecords = MutableLiveData<List<Pharmaceuticals>>()

    val patientError = MutableLiveData<Boolean>()
    val diagnosisError = MutableLiveData<Boolean>()
    val prescriptionError = MutableLiveData<Boolean>()
    val visitError = MutableLiveData<Boolean>()
    val practitionerError = MutableLiveData<Boolean>()
    val doctorError = MutableLiveData<Boolean>()
    val nursePractitionerError = MutableLiveData<Boolean>()
    val registeredNurseError = MutableLiveData<Boolean>()
    val pharmaceuticalError = MutableLiveData<Boolean>()

    val connecting = MutableLiveData<Boolean>()

    fun pullChironRecords(recordID: Int){
        when(recordID){
            1 ->{
                fetchPatientRecords()
            }
            2 ->{
                fetchDiagnosesRecords()

            }
            3 ->{
                fetchPrescriptionRecords()
            }
            4 ->{
                fetchVisitRecords()
            }
            5 ->{
                fetchPractitionerRecords()
            }
            6 ->{
                fetchDoctorRecords()
            }
            7 ->{
                fetchRegisteredNurseRecords()
            }
            8 ->{
                fetchNursePractitionerRecords()
            }
            9 ->{
                fetchPharmaceuticalRecords()
            }
        }
    }


    private fun fetchPatientRecords(){
        connecting.value = true
        patientDisposable.add(
            chironAPIService.getChironPatients()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                    override fun onSuccess(reply: List<Patient>) {
                        patientRecords.value = reply
                        patientError.value = false
                        connecting.value = false
                        // stashRecordsList(reply)
                        println(reply.toString())

                    }

                    override fun onError(e: Throwable) {
                        patientError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )

    }


    private fun fetchDiagnosesRecords(){
        connecting.value = true
        diagnosisDisposable.add(
            chironAPIService.getChironDiagnoses()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Diagnosis>>(){
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

                } )
        )

    }

    private fun fetchPrescriptionRecords(){
        connecting.value = true
        prescriptionDisposable.add(
            chironAPIService.getChironPrescriptions()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Prescription>>(){
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

                } )
        )

    }

    private fun fetchVisitRecords(){
        connecting.value = true
        visitDisposable.add(
            chironAPIService.getChironVisits()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Visit>>(){
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

                } )
        )

    }

    private fun fetchPractitionerRecords(){
        connecting.value = true
        practitionerDisposable.add(
            chironAPIService.getChironPractitioners()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Practitioner>>(){
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

                } )
        )

    }

    private fun fetchDoctorRecords(){
        connecting.value = true
        doctorDisposable.add(
            chironAPIService.getChironDoctors()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Doctor>>(){
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

                } )
        )

    }

    private fun fetchNursePractitionerRecords(){
        connecting.value = true
        nursePractitionerDisposable.add(
            chironAPIService.getChironNursePractitioners()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<NursePractitioner>>(){
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

                } )
        )

    }

    private fun fetchRegisteredNurseRecords(){
        connecting.value = true
        registeredNurseDisposable.add(
            chironAPIService.getChironRegisteredNurses()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<RegisteredNurse>>(){
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

                } )
        )

    }

    private fun fetchPharmaceuticalRecords(){
        connecting.value = true
        pharmaceuticalDisposable.add(
            chironAPIService.getChironPharmaceuticals()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Pharmaceuticals>>(){
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

                } )
        )

    }









}