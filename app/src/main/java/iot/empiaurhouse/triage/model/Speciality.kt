package iot.empiaurhouse.triage.model

import com.google.gson.annotations.SerializedName

data class Speciality(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("specialityDescription")
    val specialityDescription: String?,
    @SerializedName("new")
    val new: String?
)