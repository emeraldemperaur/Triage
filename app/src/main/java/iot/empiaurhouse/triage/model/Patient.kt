package iot.empiaurhouse.triage.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val image: ByteArray?,
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


): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Patient) return false

        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return image?.contentHashCode() ?: 0
    }
}