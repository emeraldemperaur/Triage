package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName


data class APIStatus(
    @SerializedName("signature")
    val serverSignature: String?,
    @SerializedName("localhost")
    val serverLocalHost: String?,
    @SerializedName("status")
    val serverStatus: String?
)