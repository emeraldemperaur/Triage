package iot.empiaurhouse.triage.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Prescription(

    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("brandName")
    @SerializedName("brandName")
    val brandName: String?,
    @JsonProperty("genericName")
    @SerializedName("genericName")
    val genericName: String?,
    @JsonProperty("chemicalName")
    @SerializedName("chemicalName")
    val chemicalName: String?,
    @JsonProperty("manufacturerName")
    @SerializedName("manufacturerName")
    val manufacturerName: String?,
    @JsonProperty("batchNumber")
    @SerializedName("batchNumber")
    val batchNumber: String?,
    @JsonProperty("approvalNumber")
    @SerializedName("approvalNumber")
    val approvalNumber: String?,
    @JsonProperty("manufactureDate")
    @SerializedName("manufactureDate")
    val manufactureDate: String?,
    @JsonProperty("expiryDate")
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @JsonProperty("image")
    @SerializedName("image")
    val image: String?,
    @JsonProperty("prescriptionName")
    @SerializedName("prescriptionName")
    val prescriptionName: String?,
    @JsonProperty("prescriptionDosageRegimen")
    @SerializedName("prescriptionDosageRegimen")
    val prescriptionDosageRegimen: String?,
    @JsonProperty("prescribedDosageAmount")
    @SerializedName("prescribedDosageAmount")
    val prescribedDosageAmount: String?,
    @JsonProperty("prescribedDosageType")
    @SerializedName("prescribedDosageType")
    val prescribedDosageType: String?,
    @JsonProperty("prescribedDuration")
    @SerializedName("prescribedDuration")
    val prescribedDuration: String?,
    @JsonProperty("prescribedBy")
    @SerializedName("prescribedBy")
    val prescribedBy: String?,
    @JsonProperty("prescriptionPractitionerID")
    @SerializedName("prescriptionPractitionerID")
    val prescriptionPractitionerID: String?,
    @JsonProperty("prescriptionDate")
    @SerializedName("prescriptionDate")
    val prescriptionDate: String?,
    @JsonProperty("patientFullName")
    @SerializedName("patientFullName")
    val patientFullName: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean?

): Parcelable