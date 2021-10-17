package iot.empiaurhouse.triage.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiagnosisLevel(
    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("diagnosisLevelName")
    @SerializedName("diagnosisLevelName")
    val diagnosisLevelName: String?,
    @JsonProperty("diagnosisLevelHexCode")
    @SerializedName("diagnosisLevelHexCode")
    val diagnosisLevelHexCode: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?
): Parcelable