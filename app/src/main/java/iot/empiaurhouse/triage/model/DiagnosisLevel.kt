package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class DiagnosisLevel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("diagnosisLevel")
    val diagnosisLevel: String?,
    @SerializedName("diagnosisLevelHexCode")
    val diagnosisLevelHexCode: String?,
    @SerializedName("new")
    val new: Boolean?
)