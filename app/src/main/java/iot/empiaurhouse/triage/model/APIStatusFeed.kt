package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIStatusFeed (
    @Json(name = "List")
    @JsonProperty(value="List")
    val List: List<APIStatus>?

    )