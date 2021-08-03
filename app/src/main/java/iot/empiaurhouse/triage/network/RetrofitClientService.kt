package iot.empiaurhouse.triage.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import iot.empiaurhouse.triage.utils.JSONHeaderInterceptor
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientFactory {
    private lateinit var serverUrl: String
    private lateinit var userManager: UserPreferenceManager
    private val client = OkHttpClient.Builder().build()
    private var online: Boolean = false

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()



    private val chironAPI = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideHttpClient())
        .build()
        .create(ChironAPIEndpoints::class.java)



    private fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(JSONHeaderInterceptor())
        return okHttpClientBuilder.build()
    }



    fun initServerUrl(context: Context){
        userManager = UserPreferenceManager(context)
        serverUrl = userManager.getServerUrl().toString()
        if (!serverUrl.endsWith("/")){
            serverUrl = serverUrl.plus("/")
            println(serverUrl)
        }
        if(!serverUrl.startsWith("http://") || !serverUrl.startsWith("https://")){
            serverUrl = "https://$serverUrl"
            println(serverUrl)
        }


    }



}