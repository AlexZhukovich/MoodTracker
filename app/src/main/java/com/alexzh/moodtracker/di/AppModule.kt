package com.alexzh.moodtracker.di

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtracker.data.EmotionHistoryRepository
import com.alexzh.moodtracker.data.EmotionHistoryRepositoryImpl
import com.alexzh.moodtracker.data.local.LocalEmotionHistoryDataSource
import com.alexzh.moodtracker.data.local.LocalEmotionHistoryDataSourceImpl
import com.alexzh.moodtracker.data.local.adapter.zonedDateTimeAdapter
import com.alexzh.moodtracker.presentation.core.date.DefaultTimeFormatter
import com.alexzh.moodtracker.presentation.core.date.TimeFormatter
import com.alexzh.moodtracker.presentation.core.icon.ActivityIconMapper
import com.alexzh.moodtracker.presentation.core.icon.DefaultActivityIconMapper
import com.alexzh.moodtracker.presentation.core.icon.DefaultEmotionIconMapper
import com.alexzh.moodtracker.presentation.core.icon.EmotionIconMapper
import com.alexzh.moodtracker.presentation.feature.addmood.AddMoodViewModel
import com.alexzh.moodtracker.presentation.feature.today.TodayViewModel
import com.alexzh.moodtrackerdb.EmotionHistoryEntity
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = MoodTrackerDatabase.Schema,
            context = androidContext(),
            name = "data.db"
        )
    }

    single {
        MoodTrackerDatabase(
            driver = get(),
            emotionHistoryEntityAdapter = EmotionHistoryEntity.Adapter(
                dateAdapter = zonedDateTimeAdapter
            )
        )
    }
    factory(named("dbDispatcher")) { Dispatchers.IO }

    factory<LocalEmotionHistoryDataSource> {
        LocalEmotionHistoryDataSourceImpl(
            get(),
            get(named("dbDispatcher"))
        )
    }
    factory<EmotionHistoryRepository> { EmotionHistoryRepositoryImpl(get()) }
}

val appModule = module {
    factory<ActivityIconMapper> { DefaultActivityIconMapper() }
    factory<EmotionIconMapper> { DefaultEmotionIconMapper() }
    factory<TimeFormatter> { DefaultTimeFormatter() }

    viewModel {
        TodayViewModel(
            get(),
            get(),
            get()
        )
    }

    viewModel {
        AddMoodViewModel(
            get(),
            get(),
            get()
        )
    }
}