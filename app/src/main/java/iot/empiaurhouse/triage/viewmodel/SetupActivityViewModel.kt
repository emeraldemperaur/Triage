package iot.empiaurhouse.triage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.ChironRecords
import iot.empiaurhouse.triage.network.ChironAPIService

class SetupActivityViewModel(app: Application) : AndroidViewModel(app) {
    private val chironAPIService = ChironAPIService(app.applicationContext)
    private val disposable = CompositeDisposable()
    private var cachedRecords: ArrayList<ChironRecords>? = null

    val serverStatus = MutableLiveData<List<ChironRecords>>()
    val serverError = MutableLiveData<Boolean>()
    val connecting = MutableLiveData<Boolean>()

    fun pingServer(){
        fetchServerResponse()
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun stashRecordsList(chironRecords :List<ChironRecords>) {
        cachedRecords = arrayListOf()
        if (cachedRecords!!.isEmpty()) {
            cachedRecords!!.addAll(chironRecords)
            println("Chiron Records cached successfully in ViewModel: $cachedRecords")
        }
    }


    fun fetchRecords(): ArrayList<ChironRecords>? {
        return cachedRecords
    }

    private fun fetchServerResponse(){
        connecting.value = true
        disposable.add(
            chironAPIService.getChironRecords()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<ChironRecords>>(){
                    override fun onSuccess(reply: List<ChironRecords>) {
                        serverStatus.value = reply
                        serverError.value = false
                        connecting.value = false
                        // stashRecordsList(reply)
                        println(reply.toString())

                    }

                    override fun onError(e: Throwable) {
                        serverError.value = true
                        connecting.value = false
                        e.printStackTrace()
                    }

                } )
        )

    }

}