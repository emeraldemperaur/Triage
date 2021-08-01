package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class ChironRecords(
    @SerializedName("recordName")
    val recordName: String?,
    @SerializedName("recordType")
    val recordType: String?,
    @SerializedName("recordCount")
    val recordCount: Int?,
    @SerializedName("recordID")
    val recordID: Int?

)