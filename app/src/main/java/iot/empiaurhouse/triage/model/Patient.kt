package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Patient(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("bloodGroup")
    val bloodGroup: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("insuranceVendor")
    val insuranceVendor: String?,
    @SerializedName("insuranceVendorID")
    val insuranceVendorID: String?,
    @SerializedName("profileImagePath")
    val profileImagePath: String?,
    @SerializedName("birthDate")
    val birthDate: String?,
    @SerializedName("diagnoses")
    val diagnoses: ArrayList<Diagnosis>?,
    @SerializedName("image")
    val image: ArrayList<Int>?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("delimitedFullName")
    val delimitedFullName: String?,
    @SerializedName("systemImageDate")
    val systemImageDate: String?,
    @SerializedName("new")
    val new: Boolean?,


)