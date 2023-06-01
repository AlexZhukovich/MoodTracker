package com.alexzh.moodtracker.design.component.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
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

@ExperimentalMaterial3Api
@RunWith(TestParameterInjector::class)
class SectionTest(
    @TestParameter val isDarkTheme: Boolean,
    @TestParameter val layoutDirection: LayoutDirection
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false),
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @Test
    fun section_defaultState() {
        paparazzi.snapshot {
            AppTheme(isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(
                            layoutDirection = layoutDirection,
                            fontScale = fontScale.value
                        ) {
                            Section("Section title") {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = "Test test test test test"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
