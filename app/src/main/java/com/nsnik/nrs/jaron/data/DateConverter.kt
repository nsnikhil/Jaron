package com.nsnik.nrs.jaron.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun longToDate(date: Long?): Date? = if (date == null) null else Date(date)

    @TypeConverter
    fun dateToLong(date: Date?): Long? = date?.time

}