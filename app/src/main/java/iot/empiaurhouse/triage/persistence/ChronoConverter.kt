package iot.empiaurhouse.triage.persistence

import androidx.room.TypeConverter
import java.util.*

class ChronoConverter {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun fromAnyToString(value: Any?): String? {
        return if (value == null) null else value.toString()
    }

}