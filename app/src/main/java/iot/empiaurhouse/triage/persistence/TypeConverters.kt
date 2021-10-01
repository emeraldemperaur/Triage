package iot.empiaurhouse.triage.persistence

import androidx.room.TypeConverter

class TypeConverters {


    @TypeConverter
    fun fromAnyToString(value: Any?): String? {
        return if (value == null) null else value.toString()
    }

}