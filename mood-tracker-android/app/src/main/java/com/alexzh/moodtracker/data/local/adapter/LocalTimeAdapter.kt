package com.alexzh.moodtracker.data.local.adapter

import com.squareup.sqldelight.ColumnAdapter
import java.time.LocalTime

val localTimeAdapter = object: ColumnAdapter<LocalTime, Long> {
    override fun decode(databaseValue: Long): LocalTime {
        return LocalTime.ofSecondOfDay(databaseValue)
    }

    override fun encode(value: LocalTime): Long {
        return value.toSecondOfDay().toLong()
    }
}