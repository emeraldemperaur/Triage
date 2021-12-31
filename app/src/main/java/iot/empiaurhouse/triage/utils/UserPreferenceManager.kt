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

    fun storeChironRecordsCount(patientRecordCount: Int, diagnosesRecordCount: Int,
                                prescriptionsRecordCount: Int, visitsRecordCount: Int,
                                practitionerRecordCount: Int, doctorRecordCount: Int,
                                nPRecordCount: Int, rNCount: Int, pharmaceuticalRecordCount: Int){
        preferencesEditor.putInt("CHIRON_PATIENTS_COUNT", patientRecordCount)
        preferencesEditor.putInt("CHIRON_DIAGNOSES_COUNT", diagnosesRecordCount)
        preferencesEditor.putInt("CHIRON_PRESCRIPTIONS_COUNT", prescriptionsRecordCount)
        preferencesEditor.putInt("CHIRON_VISITS_COUNT", visitsRecordCount)
        preferencesEditor.putInt("CHIRON_PRACTITIONERS_COUNT", practitionerRecordCount)
        preferencesEditor.putInt("CHIRON_DOCTORS_COUNT", doctorRecordCount)
        preferencesEditor.putInt("CHIRON_NURSE_PRACTITIONERS_COUNT", nPRecordCount)
        preferencesEditor.putInt("CHIRON_REGISTERED_NURSES_COUNT", rNCount)
        preferencesEditor.putInt("CHIRON_PHARMACEUTICALS_COUNT", pharmaceuticalRecordCount)
        preferencesEditor.apply()
        preferencesEditor.commit()
    }

    fun clearUserData(){
        preferencesEditor.clear()
        preferencesEditor.apply()
        preferencesEditor.commit()
    }


    fun getPatientsCount(): Int {
        return userPreferences.getInt("CHIRON_PATIENTS_COUNT", 0)
    }

    fun getDiagnosesCount(): Int {
        return userPreferences.getInt("CHIRON_DIAGNOSES_COUNT", 0)
    }

    fun getPrescriptionsCount(): Int {
        return userPreferences.getInt("CHIRON_PRESCRIPTIONS_COUNT", 0)
    }

    fun getVisitsCount(): Int {
        return userPreferences.getInt("CHIRON_VISITS_COUNT", 0)
    }

    fun getPractitionersCount(): Int {
        return userPreferences.getInt("CHIRON_PRACTITIONERS_COUNT", 0)
    }

    fun getDoctorsCount(): Int {
        return userPreferences.getInt("CHIRON_DOCTORS_COUNT", 0)
    }

    fun getNursePractitionersCount(): Int {
        return userPreferences.getInt("CHIRON_NURSE_PRACTITIONERS_COUNT", 0)
    }

    fun getRegisteredNursesCount(): Int {
        return userPreferences.getInt("CHIRON_REGISTERED_NURSES_COUNT", 0)
    }

    fun getPharmaceuticalsCount(): Int {
        return userPreferences.getInt("CHIRON_PHARMACEUTICALS_COUNT", 0)
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