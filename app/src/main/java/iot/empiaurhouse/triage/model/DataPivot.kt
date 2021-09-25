package iot.empiaurhouse.triage.model

import java.time.LocalDate

data class DataPivot (
    val alias: String?,
    val entityCode: Int? = null,
    val subEntityCode: Int? = null,
    val endPointCode: Int? = null,
    val timeStreamCode: Int? = null,
    val valueParameterA: String? = null,
    val valueParameterB: String? = null,
    val valueParameterC: String? = null,
    val dateParameterA: LocalDate? = null,
    val dateParameterB: LocalDate? = null,
    val serverOfOrigin: String?,
    val createdOnTimeStamp: String?
        )
