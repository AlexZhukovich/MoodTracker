package com.alexzh.moodtracker.design.component.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.moodtracker.design.R
import com.alexzh.moodtracker.design.common.FontScale
import com.alexzh.moodtracker.design.theme.AppTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(TestParameterInjector::class)
class DateToHappinessChartTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false))

    private val testHappinessIndicator = HappinessIndicators(
        angryIconRes = R.drawable.ic_email,
        confusedIconRes = R.drawable.ic_password,
        neutralIconRes = R.drawable.ic_visibility_on,
        happyIconRes = R.drawable.ic_visibility_off,
        excitedIconRes = R.drawable.ic_person
    )
    private val testData = listOf(
        LocalDate.of(2022, 1, 1) to 4.0f,
        LocalDate.of(2022, 1, 2) to 5.0f,
        LocalDate.of(2022, 1, 3) to 3.0f,
        LocalDate.of(2022, 1, 4) to 1.0f,
        LocalDate.of(2022, 1, 5) to 2.0f,
    )

    @Test
    fun dateToHappinessChart_defaultState(
        @TestParameter isDarkTheme: Boolean
    ) {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        CompositionLocalProvider(
                            LocalDensity provides Density(
                                density = LocalDensity.current.density,
                                fontScale = fontScale.value
                            )
                        ) {
                            DateToHappinessChart(
                                data = testData,
                                happinessIndicators = testHappinessIndicator,
                                modifier = Modifier.fillMaxWidth()
                                    .height(200.dp)
                                    .padding(8.dp),
                                axisColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                showYAxis = false
                            )
                        }
                    }
                }
            }
        }
    }
}