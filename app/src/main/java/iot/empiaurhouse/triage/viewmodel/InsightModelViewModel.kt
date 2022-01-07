package iot.empiaurhouse.triage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.DataPivot
import iot.empiaurhouse.triage.model.InsightModel
import iot.empiaurhouse.triage.network.ChironAPIService
import iot.empiaurhouse.triage.persistence.TriageRepository
import iot.empiaurhouse.triage.utils.UserPreferenceManager

class InsightModelViewModel(app: Application) : AndroidViewModel(app) {
    private val chironAPIService = ChironAPIService(app.applicationContext)
    private val triageRepository = TriageRepository()
    private lateinit var userManager: UserPreferenceManager
    val connecting = MutableLiveData<Boolean>()
    private val insightModelDisposable = CompositeDisposable()
    val insightModel = MutableLiveData<List<InsightModel>>()
    val resultError = MutableLiveData<Boolean>()
    private val insightModelCleanDisposable = CompositeDisposable()
    val insightModelClean = MutableLiveData<List<InsightModel>>()




    fun processPivot(application: Application){
        triageRepository.TriageRepository(application)
        userManager = UserPreferenceManager(application.applicationContext)
        fetchInsightsList()
        //fetchPivotsListClean()
    }

    private fun fetchInsightsList(){
        connecting.value = true
        insightModelDisposable.add(
            triageRepository.fetchInsightModelsByID().subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<InsightModel>>(){
                    override fun onSuccess(reply: List<InsightModel>) {
                        insightModel.value = reply
                        resultError.value = false
                        connecting.value = false
                        println("\nFound Insight Models DB: \n $reply")
                    }
                    override fun onError(e: Throwable) {
                        resultError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )
    }

    fun fetchInsightsListClean(){
        val extantServer = userManager.getServerUrl().toString()
        connecting.value = true
        insightModelCleanDisposable.add(
            triageRepository.fetchInsightModelsByIDClean(extantServer).subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<InsightModel>>(){
                    override fun onSuccess(reply: List<InsightModel>) {
                        insightModelClean.value = reply
                        resultError.value = false
                        connecting.value = false
                        println("\nFound Extant Server Insight Models DB: \n $reply")

                    }

                    override fun onError(e: Throwable) {
                        resultError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )
    }


    fun updateInsightsDB(dataPivot: DataPivot){
        triageRepository.editDataPivot(dataPivot)
    }

    fun insertInsightModel(insightModel: InsightModel){
        triageRepository.insertInsightModel(insightModel)
    }

    fun deleteInsightModel(insightModel: InsightModel){
        triageRepository.deleteInsightModel(insightModel)
    }

    fun killInsightModelsDB(){
        triageRepository.deleteInsightModels()
    }


}