package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Prescription(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("prescriptionName")
    val prescriptionName: String?,
    @SerializedName("prescriptionDosageRegimen")
    val prescriptionDosageRegimen: String?,
    @SerializedName("prescribedDosageAmount")
    val prescribedDosageAmount: String?,
    @SerializedName("prescribedDosageType")
    val prescribedDosageType: String?,
    @SerializedName("prescribedDuration")
    val prescribedDuration: String?,
    @SerializedName("prescribedBy")
    val prescribedBy: String?,
    @SerializedName("prescriptionPractitionerID")
    val prescriptionPractitionerID: String?,
    @SerializedName("prescriptionDate")
    val prescriptionDate: String?,
    @SerializedName("patientFullName")
    val patientFullName: String?,
    @SerializedName("new")
    val new: Boolean?

)