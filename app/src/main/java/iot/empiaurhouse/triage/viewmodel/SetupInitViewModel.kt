package iot.empiaurhouse.triage.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import iot.empiaurhouse.triage.model.APIStatus
import iot.empiaurhouse.triage.network.ChironAPIService
import iot.empiaurhouse.triage.utils.UserPreferenceManager


class SetupInitViewModel(app: Application) : AndroidViewModel(app) {

    private val chironAPIService = ChironAPIService(app.applicationContext)
    private val disposable = CompositeDisposable()
    private lateinit var info: Map<String, String?>
    val serverStatus = MutableLiveData<List<APIStatus>>()
    val serverError = MutableLiveData<Boolean>()
    val connecting = MutableLiveData<Boolean>()
    private lateinit var userManager: UserPreferenceManager


    fun pingServer(context: Context){
        userManager = UserPreferenceManager(context)
        fetchServerResponse()
        // fetchServerInfo()
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun fetchServerResponse(){
        connecting.value = true
        disposable.add(
            chironAPIService.getChironStatus()
                .subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<APIStatus>>(){
                    override fun onSuccess(reply: List<APIStatus>) {
                        serverStatus.value = reply
                        serverError.value = false
                        connecting.value = false
                        val replyItem = reply.first()
                            replyItem.serverStatus?.let { replyItem.localhost?.let { it1 ->
                                replyItem.signature?.let { it2 ->
                                    userManager.storeServerInfo(it,
                                        it1, it2
                                    )
                                }
                            } }

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


     fun fetchServerInfo(): Map<String, String?>{
        var infoList = serverStatus.value
        var infoObject = infoList?.first()

        println("Chiron Server Info Object found: $infoObject")
        if (infoObject != null) {
            println("Status: ${infoObject.serverStatus}")
            println("Signature: ${infoObject.signature}")
            println("LocalHost: ${infoObject.localhost}")
            info = mapOf("Status" to infoObject.serverStatus, "Signature" to infoObject.signature, "LocalHost" to infoObject.localhost)
        }
        return info
    }


}