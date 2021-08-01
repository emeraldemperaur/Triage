package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Visit(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("visitDate")
    val visitDate: String?,
    @SerializedName("visitTime")
    val visitTime: String?,
    @SerializedName("visitDescription")
    val visitDescription: String?,
    @SerializedName("patientFullName")
    val patientFullName: String?,
    @SerializedName("hostPractitioner")
    val hostPractitioner: String?,
    @SerializedName("hostPractitionerID")
    val hostPractitionerID: String?,
    @SerializedName("new")
    val new: Boolean?
)