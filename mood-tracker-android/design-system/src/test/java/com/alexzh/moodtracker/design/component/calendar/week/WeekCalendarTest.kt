package com.alexzh.moodtracker.design.component.calendar.week

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.moodtracker.design.common.FontScale
import com.alexzh.moodtracker.design.theme.AppTheme
import com.android.ide.common.rendering.api.SessionParams
import com.google.accompanist.testharness.TestHarness
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RunWith(TestParameterInjector::class)
class WeekCalendarTest(
    @TestParameter val isDarkTheme: Boolean
) {
    private val testDate = LocalDate.of(2022, 5, 5)

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false),
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @Test
    fun weekCalendar_todayIsSelectedDate() {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate,
                                onSelectedDateChange = {},
                                todayDate = testDate
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun weekCalendar_todayIsNotSelectedDate() {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate,
                                onSelectedDateChange = {},
                                todayDate = testDate.minusDays(1)
                            )
                        }
                    }
                }
            }
        }
    }
}