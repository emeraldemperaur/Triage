package iot.empiaurhouse.triage.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Speciality(
    @JsonProperty("id")
    @SerializedName("id")
    val id: Int?,
    @JsonProperty("specialityDescription")
    @SerializedName("specialityDescription")
    val specialityDescription: String?,
    @JsonProperty("new")
    @SerializedName("new")
    val new: String?
): Parcelable