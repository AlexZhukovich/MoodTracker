package com.alexzh.moodtracker.di

import com.alexzh.moodtracker.presentation.core.date.DefaultTimeFormatter
import com.alexzh.moodtracker.presentation.core.date.TimeFormatter
import com.alexzh.moodtracker.presentation.core.icon.DefaultIconMapper
import com.alexzh.moodtracker.presentation.core.icon.IconMapper
import com.alexzh.moodtracker.presentation.feature.today.TodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<IconMapper> { DefaultIconMapper() }
    factory<TimeFormatter> { DefaultTimeFormatter() }

    viewModel {
        TodayViewModel(
            get(),
            get()
        )
    }
}