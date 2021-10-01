package iot.empiaurhouse.triage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import iot.empiaurhouse.triage.persistence.TypeConverters
import org.jetbrains.annotations.Nullable

@Entity(tableName = "pivot_db")
@androidx.room.TypeConverters(TypeConverters::class)
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
    @Nullable
    val timeStreamCode: Int? = null,
    @ColumnInfo(name = "valueParamCode")
    @Nullable
    val valueParamCode: Int? = null,
    @ColumnInfo(name = "valueParameterA")
    @Nullable
    val valueParameterA: String? = null,
    @ColumnInfo(name = "valueParameterB")
    @Nullable
    val valueParameterB: String? = null,
    @ColumnInfo(name = "valueParameterC")
    @Nullable
    val valueParameterC: String? = null,
    @ColumnInfo(name = "dateParameterA")
    @Nullable
    val dateParameterA: String? = null,
    @ColumnInfo(name = "dateParameterB")
    @Nullable
    val dateParameterB: String? = null,
    @ColumnInfo(name = "serverOfOrigin")
    val serverOfOrigin: String?,
    @ColumnInfo(name = "createdOnTimeStamp")
    val createdOnTimeStamp: String?
        )
