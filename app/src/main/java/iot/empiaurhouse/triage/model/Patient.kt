package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Patient(
    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("firstName")
    @SerializedName("firstName")
    val firstName: String?,
    @JsonProperty("lastName")
    @SerializedName("lastName")
    val lastName: String?,
    @JsonProperty("bloodGroup")
    @SerializedName("bloodGroup")
    val bloodGroup: String?,
    @JsonProperty("address")
    @SerializedName("address")
    val address: String?,
    @JsonProperty("city")
    @SerializedName("city")
    val city: String?,
    @JsonProperty("phoneNumber")
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @JsonProperty("insuranceVendor")
    @SerializedName("insuranceVendor")
    val insuranceVendor: String?,
    @JsonProperty("insuranceVendorID")
    @SerializedName("insuranceVendorID")
    val insuranceVendorID: String?,
    @JsonProperty("profileImagePath")
    @SerializedName("profileImagePath")
    val profileImagePath: String?,
    @JsonProperty("birthDate")
    @SerializedName("birthDate")
    val birthDate: String?,
    @JsonProperty("diagnoses")
    @SerializedName("diagnoses")
    val diagnoses: ArrayList<Diagnosis>?,
    @JsonProperty("image")
    @SerializedName("image")
    val image: ArrayList<Int>?,
    @JsonProperty("fullName")
    @SerializedName("fullName")
    val fullName: String?,
    @JsonProperty("shortName")
    @SerializedName("shortName")
    val shortName: String?,
    @JsonProperty("delimitedFullName")
    @SerializedName("delimitedFullName")
    val delimitedFullName: String?,
    @JsonProperty("systemImagePath")
    @SerializedName("systemImagePath")
    val systemImagePath: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?,


)