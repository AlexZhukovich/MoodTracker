package com.alexzh.moodtracker.data.local.adapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.DayOfWeek

val listOfDayOfWeekAdapter = object : ColumnAdapter<List<DayOfWeek>, String> {
    override fun decode(databaseValue: String): List<DayOfWeek> {
        return if (databaseValue.isEmpty()) {
            emptyList()
        } else {
            databaseValue.split(",").map { DayOfWeek.of(it.toInt()) }
        }
    }

    override fun encode(value: List<DayOfWeek>): String {
        return value.joinToString(",") { it.value.toString() }
    }
}