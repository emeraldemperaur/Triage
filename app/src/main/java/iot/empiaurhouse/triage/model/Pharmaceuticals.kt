package iot.empiaurhouse.triage.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Pharmaceuticals(
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
    @JsonProperty("manufactureName")
    @SerializedName("manufactureName")
    val manufactureName: String?,
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
    val image: ArrayList<Int>?,
    @JsonProperty("inStock")
    @SerializedName("inStock")
    val inStock: Int?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: Boolean
)