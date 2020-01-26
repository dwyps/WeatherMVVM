package com.frangrgec.weathermvvm.data.database.entity

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class StringConverters {

    /*@ToJson fun listToJson(data : MutableList<String>?) : String{
        return if (data == null || data.isEmpty())
            ""
        else
            data.joinToString { it }
    }

    @FromJson fun jsonToList(data : String?) : MutableList<String>{

        return data?.toMutableList() ?: mutableListOf()

    }*/

    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }


    /*@TypeConverter
    fun toList(value: String?): MutableList<String> {

        return listOf(value!!).toMutableList()
    }

    @TypeConverter
    fun toString(stringList: MutableList<String>?): String {

        var string = ""

        if (stringList == null) {
            return string
        }

        stringList.forEach {
            string += "$it,"
        }
        return string
    }*/


}