package com.sogya.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.math.BigDecimal


class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromArrayLongList(list: ArrayList<Long>?):String?{
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromLong(value: String?): ArrayList<Long?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: ""
    }
    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.valueOf(0.0)
        return input.toBigDecimalOrNull() ?: BigDecimal.valueOf(0.0)
    }
    @TypeConverter
    fun fromArrayDecimalList(list: ArrayList<BigDecimal>?):String?{
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromBigDecimal(value: String?): ArrayList<BigDecimal?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}