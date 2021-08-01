package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Pharmaceuticals(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("brandName")
    val brandName: String?,
    @SerializedName("genericName")
    val genericName: String?,
    @SerializedName("chemicalName")
    val chemicalName: String?,
    @SerializedName("manufactureName")
    val manufactureName: String?,
    @SerializedName("batchNumber")
    val batchNumber: String?,
    @SerializedName("approvalNumber")
    val approvalNumber: String?,
    @SerializedName("manufactureDate")
    val manufactureDate: String?,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("image")
    val image: ArrayList<Int>?,
    @SerializedName("inStock")
    val inStock: Int?,
    @SerializedName("new")
    val new: Boolean
)