package com.alexzh.moodtracker.presentation.feature.stats

sealed class StatisticsEvent {
    object Load : StatisticsEvent()
}
