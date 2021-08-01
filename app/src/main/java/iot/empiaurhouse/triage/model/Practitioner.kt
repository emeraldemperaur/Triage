package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Practitioner(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("practitionerID")
    val practitionerID: String?,
    @SerializedName("contactInfo")
    val contactInfo: String?,
    @SerializedName("emailInfo")
    val emailInfo: String?,
    @SerializedName("image")
    val image: ArrayList<Int>?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("delimitedFullName")
    val delimitedFullName: String?,
    @SerializedName("new")
    val new: Boolean?
)