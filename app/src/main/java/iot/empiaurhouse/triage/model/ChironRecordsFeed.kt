package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ChironRecordsFeed(
    @SerializedName( "List")
    @JsonProperty(value="List")
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
    val List: List<ChironRecords>?
)
