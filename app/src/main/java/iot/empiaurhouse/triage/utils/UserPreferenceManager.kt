package iot.empiaurhouse.triage.utils

import android.content.Context
import android.content.SharedPreferences


class UserPreferenceManager(context: Context) {

    private var userPreferences: SharedPreferences = context.getSharedPreferences("chiron_user_preferences", 0)
    var preferencesEditor: SharedPreferences.Editor = userPreferences.edit()

    fun storeUserData(email: String, serverUrl: String, phone: String, chironID: String){
        preferencesEditor.putString("USER_EMAIL_KEY", email)
        preferencesEditor.putString("SERVER_URL_KEY", serverUrl)
        preferencesEditor.putString("USER_PHONE_KEY", phone)
        preferencesEditor.putString("CHIRON_ID_KEY", chironID)
        preferencesEditor.apply()
        preferencesEditor.commit()

    }

    fun storeServerInfo(status: String, host: String, sign: String){
        preferencesEditor.putString("CHIRON_SERVER_STATUS", status)
        preferencesEditor.putString("CHIRON_SERVER_HOST", host)
        preferencesEditor.putString("CHIRON_SERVER_SIGN", sign)
        preferencesEditor.apply()
        preferencesEditor.commit()
    }


    fun clearUserData(){
        preferencesEditor.clear()
        preferencesEditor.apply()
        preferencesEditor.commit()
    }

    fun getUserID(): String? {
        return userPreferences.getString("USER_EMAIL_KEY", null)
    }

    fun getServerUrl(): String? {
        return userPreferences.getString("SERVER_URL_KEY", null)
    }

    fun getUserPhone(): String? {
        return userPreferences.getString("USER_PHONE_KEY", null)
    }

    fun getChironID(): String? {
        return userPreferences.getString("CHIRON_ID_KEY", null)
    }


    fun getServerStatus(): String? {
        return userPreferences.getString("CHIRON_SERVER_STATUS", null)
    }

    fun getServerHost(): String? {
        return userPreferences.getString("CHIRON_SERVER_HOST", null)
    }


    fun getServerSign(): String? {
        return userPreferences.getString("CHIRON_SERVER_SIGN", null)
    }


}