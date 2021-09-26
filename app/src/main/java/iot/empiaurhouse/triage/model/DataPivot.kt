package iot.empiaurhouse.triage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "pivot_db")
data class DataPivot (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "alias")
    val alias: String,
    @ColumnInfo(name = "entityCode")
    val entityCode: Int? = null,
    @ColumnInfo(name = "optionCode")
    val optionCode: Int? = null,
    @ColumnInfo(name = "practitionerCode")
    val practitionerCode: Int? = null,
    @ColumnInfo(name = "endPointCode")
    val endPointCode: Int? = null,
    @ColumnInfo(name = "timeStreamCode")
    val timeStreamCode: Int? = null,
    @ColumnInfo(name = "valueParamCode")
    val valueParamCode: Int? = null,
    @ColumnInfo(name = "valueParameterA")
    val valueParameterA: String? = null,
    @ColumnInfo(name = "valueParameterB")
    val valueParameterB: String? = null,
    @ColumnInfo(name = "valueParameterC")
    val valueParameterC: String? = null,
    @ColumnInfo(name = "dateParameterA")
    val dateParameterA: LocalDate? = null,
    @ColumnInfo(name = "dateParameterB")
    val dateParameterB: LocalDate? = null,
    @ColumnInfo(name = "serverOfOrigin")
    val serverOfOrigin: String?,
    @ColumnInfo(name = "createdOnTimeStamp")
    val createdOnTimeStamp: LocalDate?
        )
