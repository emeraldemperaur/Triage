package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Diagnosis(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("patientFullName")
    val patientFullNme: String?,
    @SerializedName("diagnosisLevel")
    val diagnosisLevel: ArrayList<DiagnosisLevel>?,
    @SerializedName("diagnosisDetails")
    val diagnosisDetails: String?,
    @SerializedName("diagnosisSynopsis")
    val diagnosisSynopsis: String?,
    @SerializedName("visitDate")
    val visitDate: String?,
    @SerializedName("prescriptions")
    val prescriptions: ArrayList<Prescription>?,
    @SerializedName("visits")
    val visits: ArrayList<Visit>?,
    @SerializedName("new")
    val new: Boolean?

)