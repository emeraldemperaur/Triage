package iot.empiaurhouse.triage.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.model.Patient
import iot.empiaurhouse.triage.network.ChironAPIService
import iot.empiaurhouse.triage.persistence.TriageRepository
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import kotlinx.coroutines.InternalCoroutinesApi

class DataPivotViewModel: ViewModel() {
    private val chironAPIService = ChironAPIService()
    private val triageRepository = TriageRepository()
    private val dataPivotDisposable = CompositeDisposable()
    private val dataPivotCleanDisposable = CompositeDisposable()
    private val patientDisposable = CompositeDisposable()
    private val patientSubDisposable = CompositeDisposable()
    private val patientUnSubDisposable = CompositeDisposable()
    val patientRecords = MutableLiveData<List<Patient>>()
    val patientSubRecords = MutableLiveData<List<Patient>>()
    val patientUnSubRecords = MutableLiveData<List<Patient>>()
    val serviceError = MutableLiveData<Boolean>()
    val dataPivots = MutableLiveData<List<DataPivot>>()
    val dataPivotsClean = MutableLiveData<List<DataPivot>>()
    val resultError = MutableLiveData<Boolean>()
    val connecting = MutableLiveData<Boolean>()
    private lateinit var alphaParam: String
    private lateinit var betaParam: String
    private lateinit var epsilonParam: String
    private lateinit var chiParam: String
    private lateinit var psiParam: String
    private lateinit var userManager: UserPreferenceManager



    @InternalCoroutinesApi
    fun processPivot(application: Application){
        triageRepository.TriageRepository(application)
        userManager = UserPreferenceManager(application.applicationContext)
        fetchPivotsList()
        //fetchPivotsListClean()
    }



     private fun fetchPivotsList(){
        connecting.value = true
        dataPivotDisposable.add(
            triageRepository.fetchDataPivotsByID().subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DataPivot>>(){
                    override fun onSuccess(reply: List<DataPivot>) {
                        dataPivots.value = reply
                        resultError.value = false
                        connecting.value = false
                        println("\nFound Data Pivots DB: \n $reply")

                    }

                    override fun onError(e: Throwable) {
                        resultError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )
    }

     fun fetchPivotsListClean(){
        val extantServer = userManager.getServerUrl().toString()
        connecting.value = true
        dataPivotCleanDisposable.add(
            triageRepository.fetchDataPivotsByIDClean(extantServer).subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DataPivot>>(){
                    override fun onSuccess(reply: List<DataPivot>) {
                        dataPivotsClean.value = reply
                        resultError.value = false
                        connecting.value = false
                        println("\nFound Extant Server Data Pivots DB: \n $reply")

                    }

                    override fun onError(e: Throwable) {
                        resultError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )
    }

    fun collatePivotData(dataPivot: DataPivot){
        alphaParam = dataPivot.valueParameterA.toString()
        betaParam = dataPivot.valueParameterB.toString()
        epsilonParam = dataPivot.valueParameterC.toString()
        chiParam = dataPivot.dateParameterA.toString()
        psiParam = dataPivot.dateParameterB.toString()
        val dateEndPointCodes = arrayListOf(3, 11, 18, 23, 29, 30)
        val timeStreamCode = dataPivot.timeStreamCode
        val valueParamCode = dataPivot.valueParamCode
        val valueEndPointCodes = arrayListOf(1, 2)
        val pivotEPC = dataPivot.endPointCode



        //val patientFNEndPoint = chironAPIService.getChironPatientsByFN(alphaParam!!)


    }

    fun getPatientService(dataPivot: DataPivot){
        var service = chironAPIService.getChironPatientsByBirthDateOn("1990-04-27")
        if (isDatePivot(dataPivot)){
            if(dataPivot.endPointCode == 3){
                when(dataPivot.timeStreamCode){
                    1 ->{
                        patientDisposable.add(
                            chironAPIService.getChironPatientsByBirthDateOn(chiParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                } )
                        )
                    }
                    2 ->{
                        patientDisposable.add(
                            chironAPIService.getChironPatientsByBirthDateBefore(chiParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                } )
                        )
                    }
                    3 ->{
                        patientDisposable.add(
                            chironAPIService.getChironPatientsByBirthDateAfter(chiParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())
                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                } )
                        )
                    }
                    4 ->{
                        patientDisposable.add(
                            chironAPIService.getChironPatientsByBirthDateBetween(chiParam, psiParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                } )
                        )
                    }
                }

            }

        }
        else if (!isDatePivot(dataPivot)){
            val vPC = dataPivot.valueParamCode
            when(dataPivot.endPointCode){
                1->{
                    patientDisposable.add(
                        chironAPIService.getChironPatientsByFN(alphaParam)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                override fun onSuccess(reply: List<Patient>) {
                                    patientRecords.value = reply
                                    serviceError.value = false
                                    connecting.value = false
                                    // stashRecordsList(reply)
                                    println(reply.toString())

                                }

                                override fun onError(e: Throwable) {
                                    serviceError.value = true
                                    connecting.value = false
                                    e.printStackTrace()
                                }

                            } )
                    )

                }
                2->{
                    patientDisposable.add(
                        chironAPIService.getChironPatientsByLN(alphaParam)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                override fun onSuccess(reply: List<Patient>) {
                                    patientRecords.value = reply
                                    serviceError.value = false
                                    connecting.value = false
                                    // stashRecordsList(reply)
                                    println(reply.toString())

                                }

                                override fun onError(e: Throwable) {
                                    serviceError.value = true
                                    connecting.value = false
                                    e.printStackTrace()
                                }

                            } )
                    )

                }
                4->{
                    patientDisposable.add(
                        chironAPIService.getChironPatientsByBloodGroup(alphaParam)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                override fun onSuccess(reply: List<Patient>) {
                                    patientRecords.value = reply
                                    serviceError.value = false
                                    connecting.value = false
                                    // stashRecordsList(reply)
                                    println(reply.toString())

                                }

                                override fun onError(e: Throwable) {
                                    serviceError.value = true
                                    connecting.value = false
                                    e.printStackTrace()
                                }

                            } )
                    )

                }
                5->{
                    patientDisposable.add(
                        chironAPIService.getChironPatientsByInsurer(alphaParam)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                override fun onSuccess(reply: List<Patient>) {
                                    patientRecords.value = reply
                                    serviceError.value = false
                                    connecting.value = false
                                    // stashRecordsList(reply)
                                    println(reply.toString())

                                }

                                override fun onError(e: Throwable) {
                                    serviceError.value = true
                                    connecting.value = false
                                    e.printStackTrace()
                                }

                            } )
                    )

                }
                6->{
                    patientDisposable.add(
                        chironAPIService.getChironPatientsByInsurerID(alphaParam)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribeWith(object: DisposableSingleObserver<List<Patient>>(){
                                override fun onSuccess(reply: List<Patient>) {
                                    patientRecords.value = reply
                                    serviceError.value = false
                                    connecting.value = false
                                    // stashRecordsList(reply)
                                    println(reply.toString())

                                }

                                override fun onError(e: Throwable) {
                                    serviceError.value = true
                                    connecting.value = false
                                    e.printStackTrace()
                                }

                            } )
                    )

                }
            }
            if(vPC!! > 1) {
                when (dataPivot.endPointCode) {

                    1 -> {
                        patientSubDisposable.add(
                            chironAPIService.getChironPatientsByFN(betaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )
                        patientUnSubDisposable.add(
                            chironAPIService.getChironPatientsByFN(epsilonParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientUnSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )

                    }
                    2 -> {
                        patientSubDisposable.add(
                            chironAPIService.getChironPatientsByLN(betaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )
                        patientUnSubDisposable.add(
                            chironAPIService.getChironPatientsByLN(epsilonParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientUnSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )

                    }
                    4 -> {
                        patientSubDisposable.add(
                            chironAPIService.getChironPatientsByBloodGroup(betaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )
                        patientUnSubDisposable.add(
                            chironAPIService.getChironPatientsByBloodGroup(epsilonParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientUnSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )

                    }
                    5 -> {
                        patientSubDisposable.add(
                            chironAPIService.getChironPatientsByInsurer(betaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )
                        patientUnSubDisposable.add(
                            chironAPIService.getChironPatientsByInsurer(epsilonParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientUnSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )

                    }
                    6 -> {
                        patientSubDisposable.add(
                            chironAPIService.getChironPatientsByInsurerID(alphaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )
                        patientUnSubDisposable.add(
                            chironAPIService.getChironPatientsByInsurerID(alphaParam)
                                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<List<Patient>>() {
                                    override fun onSuccess(reply: List<Patient>) {
                                        patientUnSubRecords.value = reply
                                        serviceError.value = false
                                        connecting.value = false
                                        // stashRecordsList(reply)
                                        println(reply.toString())

                                    }

                                    override fun onError(e: Throwable) {
                                        serviceError.value = true
                                        connecting.value = false
                                        e.printStackTrace()
                                    }

                                })
                        )

                    }
                }
            }

        }

    }

    fun updatePivotDB(dataPivot: DataPivot){
        triageRepository.editDataPivot(dataPivot)
    }

    fun insertDataPivot(dataPivot: DataPivot){
        triageRepository.insertDataPivot(dataPivot)
    }

    fun deleteDataPivot(dataPivot: DataPivot){
        triageRepository.deleteDataPivot(dataPivot)
    }

    fun killDataPivotDB(){
        triageRepository.deleteDataPivots()
    }


    private fun isDatePivot(dataPivot: DataPivot): Boolean{
        val tSC = dataPivot.timeStreamCode
        val vPC = dataPivot.valueParamCode
        var isDate = false
        if (tSC != null){
            isDate = true
        }
        else if (vPC != null || dataPivot.valueParamCode!! > 0){
            isDate = false
        }
        return isDate
    }





}