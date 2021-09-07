package iot.empiaurhouse.triage.model

data class DataPivot (
    val alias: String,
    val entityCode: Int,
    val subEntityCode: Int?,
    val endPointCode: Int,
    val valueParameterA: String,
    val valueParameterB: String?,
    val valueParameterC: String?,
    val dateParameterA: String,
    val dateParameterB: String?,
    val serverOfOrigin: String?,
    val createdOnTimeStamp: String
        )
