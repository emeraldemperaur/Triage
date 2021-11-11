package iot.empiaurhouse.triage.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import iot.empiaurhouse.triage.persistence.TypeConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "insight_db")
@androidx.room.TypeConverters(TypeConverters::class)
data class InsightModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "alias")
    val alias: String,
    @ColumnInfo(name = "vistaCode")
    val vistaCode: Int?,
    @ColumnInfo(name = "entityCode")
    val entityCode: Int?,
    @ColumnInfo(name = "pointOfInterest")
    val pointOfInterest: String,
    @ColumnInfo(name = "rangeStartDate")
    val rangeStartDate: String,
    @ColumnInfo(name = "rangeEndDate")
    val rangeEndDate: String,
    @ColumnInfo(name = "piThresholdValue")
    val piThresholdValue: String,
    @ColumnInfo(name = "omegaThresholdValue")
    val omegaThresholdValue: String? = null,
    @ColumnInfo(name = "serverOfOrigin")
    val serverOfOrigin: String?,
    @ColumnInfo(name = "createdOnTimeStamp")
    val createdOnTimeStamp: String?
): Parcelable