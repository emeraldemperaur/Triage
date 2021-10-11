package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Practitioner(
    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("firstName")
    @SerializedName("firstName")
    val firstName: String?,
    @JsonProperty("lastName")
    @SerializedName("lastName")
    val lastName: String?,
    @JsonProperty("practitionerID")
    @SerializedName("practitionerID")
    val practitionerID: String?,
    @JsonProperty("contactInfo")
    @SerializedName("contactInfo")
    val contactInfo: String?,
    @JsonProperty("emailInfo")
    @SerializedName("emailInfo")
    val emailInfo: String?,
    @JsonProperty("image")
    @SerializedName("image")
    val image: ArrayList<Int>?,
    @JsonProperty("fullName")
    @SerializedName("fullName")
    val fullName: String?,
    @JsonProperty("delimitedFullName")
    @SerializedName("delimitedFullName")
    val delimitedFullName: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?
)