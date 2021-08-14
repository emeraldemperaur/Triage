package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIStatus(
    @SerializedName("signature")
    @JsonProperty(value="signature")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val signature: String?,
    @SerializedName( "localhost")
    @JsonProperty(value="localhost")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val localhost: String?,
    @SerializedName("chironStatus")
    @JsonProperty(value="chironStatus")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val serverStatus: String?
)