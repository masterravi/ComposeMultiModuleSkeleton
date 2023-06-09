package com.training.datastore

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterList {


    @TypeConverter
    fun stringToMap(value: String): Map<String, Any> {
        return Gson().fromJson(value, object : TypeToken<Map<String, Any>>() {}.type)
    }

    @TypeConverter
    fun mapToString(value: Map<String, Any>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }

}