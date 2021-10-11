package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Visit(
    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("visitDate")
    @SerializedName("visitDate")
    val visitDate: String?,
    @JsonProperty("visitTime")
    @SerializedName("visitTime")
    val visitTime: String?,
    @JsonProperty("visitDescription")
    @SerializedName("visitDescription")
    val visitDescription: String?,
    @JsonProperty("patientFullName")
    @SerializedName("patientFullName")
    val patientFullName: String?,
    @JsonProperty("hostPractitioner")
    @SerializedName("hostPractitioner")
    val hostPractitioner: String?,
    @JsonProperty("hostPractitionerID")
    @SerializedName("hostPractitionerID")
    val hostPractitionerID: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?
)