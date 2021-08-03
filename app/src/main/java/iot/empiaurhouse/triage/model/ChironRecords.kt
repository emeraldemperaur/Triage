package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ChironRecords(
    @SerializedName(  "recordName")
    @JsonProperty(value="recordName")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val recordName: String?,
    @SerializedName("recordType")
    @JsonProperty(value="recordType")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val recordType: String?,
    @SerializedName("recordCount")
    @JsonProperty(value="recordCount")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val recordCount: Int?,
    @SerializedName( "recordID")
    @JsonProperty(value="recordID")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val recordID: Int?

)