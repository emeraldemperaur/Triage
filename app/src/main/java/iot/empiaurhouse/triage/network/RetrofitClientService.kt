package iot.empiaurhouse.triage.network

import android.content.Context
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientFactory {
    private lateinit var serverUrl: String
    private lateinit var userManager: UserPreferenceManager
    private val client = OkHttpClient.Builder().build()


    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }


    val chironGetService: ChironAPIEndpoints by lazy {
        retrofit().create(ChironAPIEndpoints::class.java)
    }

    val chironPostService: ChironAPIPOSTEndpoints by lazy {
       retrofit().create(ChironAPIPOSTEndpoints::class.java)
    }





    fun initServerUrl(context: Context){
        userManager = UserPreferenceManager(context)
        serverUrl = userManager.getServerUrl().toString()
        if (!serverUrl.endsWith("/")){
            serverUrl = serverUrl.plus("/")
            println(serverUrl)
        }
        if(!serverUrl.startsWith("http://") || !serverUrl.startsWith("https://")){
            serverUrl = "http://$serverUrl"
            println(serverUrl)
        }


    }

    fun serverPingTest(): Boolean{
        // Test the service with ping request return Boolean
        chironGetService.getChironAPIStatus()
        return false
    }


}