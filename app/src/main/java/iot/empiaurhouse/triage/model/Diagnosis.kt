package iot.empiaurhouse.triage.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Diagnosis (
    @SerializedName("id")
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("patientFullName")
    @SerializedName("patientFullName")
    val patientFullName: String?,
    @JsonProperty("diagnosisLevel")
    @SerializedName("diagnosisLevel")
    val diagnosisLevel: DiagnosisLevel,
    @JsonProperty("diagnosisDetails")
    @SerializedName("diagnosisDetails")
    val diagnosisDetails: String?,
    @JsonProperty("diagnosisSynopsis")
    @SerializedName("diagnosisSynopsis")
    val diagnosisSynopsis: String?,
    @JsonProperty("visitDate")
    @SerializedName("visitDate")
    val visitDate: String?,
    @JsonProperty("prescriptions")
    @SerializedName("prescriptions")
    val prescriptions: ArrayList<Prescription>?,
    @JsonProperty("visits")
    @SerializedName("visits")
    val visits: ArrayList<Visit>?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?

): Parcelable