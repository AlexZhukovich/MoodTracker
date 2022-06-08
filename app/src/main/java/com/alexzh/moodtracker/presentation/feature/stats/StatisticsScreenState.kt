package com.alexzh.moodtracker.presentation.feature.stats

import java.time.LocalDate

// TODO: UPDATE TYPE FOR THE "data"
// TODO: PROBABLY I CAN USE FLOAT INSTEAD OF DOUBLE BECAUSE A LOT OF TRANSFORMATIONS IN UI COMPONENTS
data class StatisticsScreenState(
    val loading: Boolean = false,
    val data: List<Pair<LocalDate, Double>> = emptyList()
)
