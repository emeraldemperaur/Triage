package iot.empiaurhouse.triage.utils

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceManager(context: Context) {

    private val dataStore = context.createDataStore(name = "chiron_prefs")

    companion object {
        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
        val SERVER_URL_KEY = preferencesKey<String>("USER_SERVER")
        val USER_PHONE_KEY = preferencesKey<String>("USER_PHONE")
        val CHIRON_ID_KEY = preferencesKey<String>("CHIRON_ID")
    }

    suspend fun storeUser(email: String, serverUrl: String, phone: String, chironID: String) {
        dataStore.edit {
            it[USER_EMAIL_KEY] = email
            it[SERVER_URL_KEY] = serverUrl
            it[USER_PHONE_KEY] = phone
            it[CHIRON_ID_KEY] = chironID
        }
    }

    val userEmailFlow: Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: ""
    }

    val serverUrlFlow: Flow<String> = dataStore.data.map {
        it[SERVER_URL_KEY] ?: ""
    }

    val userPhoneFlow: Flow<String> = dataStore.data.map {
        it[USER_PHONE_KEY] ?: ""
    }

    val chironIDFlow: Flow<String> = dataStore.data.map {
        it[CHIRON_ID_KEY] ?: ""
    }




}