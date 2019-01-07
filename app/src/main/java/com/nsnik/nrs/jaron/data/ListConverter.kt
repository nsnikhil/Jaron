package com.nsnik.nrs.jaron.data

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson


class ListConverter {

    @TypeConverter
    fun fromString(value: String): List<String> =
        Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromList(list: List<String>): String = Gson().toJson(list)

}