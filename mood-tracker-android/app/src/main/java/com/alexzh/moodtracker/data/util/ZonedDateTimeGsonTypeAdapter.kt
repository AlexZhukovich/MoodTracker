package com.alexzh.moodtracker.data.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeGsonTypeAdapter : JsonDeserializer<ZonedDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ZonedDateTime {
        val jsonObj = json?.asJsonObject
        val dateTime = jsonObj!!.getAsJsonObject("dateTime")
        val date = dateTime.getAsJsonObject("date")
        val year = date["year"].asInt
        val month = date["month"].asInt
        val day = date["day"].asInt

        val time = dateTime.getAsJsonObject("time")
        val hour = time["hour"].asInt
        val minute = time["minute"].asInt
        val second = time["second"].asInt
        val nano = time["nano"].asInt

        val zone = jsonObj.getAsJsonObject("zone")
        val id = zone["id"].asString

        return ZonedDateTime.of(
            year,
            month,
            day,
            hour,
            minute,
            second,
            nano,
            ZoneId.of(id)
        )
    }
}