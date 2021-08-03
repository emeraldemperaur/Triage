package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIStatus(
    @SerializedName("signature")
    @JsonProperty(value="signature")
    val serverSignature: String?,
    @SerializedName( "localhost")
    @JsonProperty(value="localhost")
    val serverLocalHost: String?,
    @SerializedName("status")
    @JsonProperty(value="status")
    val serverStatus: String?
)