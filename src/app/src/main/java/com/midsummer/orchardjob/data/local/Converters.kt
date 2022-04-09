package com.midsummer.orchardjob.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.midsummer.orchardjob.pojo.Row
import com.midsummer.orchardjob.pojo.Staff

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class Converters {

    @TypeConverter
    fun fromStaff(value: Staff): String {
        val gson = Gson()
        val type = object : TypeToken<Staff>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStaff(value: String): Staff {
        val gson = Gson()
        val type = object : TypeToken<Staff>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRow(value: Row): String {
        val gson = Gson()
        val type = object : TypeToken<Row>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRow(value: String): Row {
        val gson = Gson()
        val type = object : TypeToken<Row>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRows(value: List<Row>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Row>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRows(value: String): List<Row> {
        val gson = Gson()
        val type = object : TypeToken<List<Row>>() {}.type
        return gson.fromJson(value, type)
    }
}