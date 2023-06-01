package com.alexzh.moodtracker.design.component.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
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
import java.time.LocalTime

@RunWith(TestParameterInjector::class)
class OutlineTimeAndDatePickersTest(
    @TestParameter val isDarkTheme: Boolean
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false),
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @Test
    fun outlineTimePickerAndOutlineDatePicker_defaultState() {
        paparazzi.snapshot {
            val date = remember { mutableStateOf(LocalDate.of(2022, 1, 1)) }
            val time = remember { mutableStateOf(LocalTime.of(7, 42)) }
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (fontScale in FontScale.values()) {
                        TestHarness(fontScale = fontScale.value) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlineDatePicker(
                                    modifier = Modifier.weight(1.0f),
                                    label = { Text("Date: ") },
                                    onValueChange = { },
                                    value = date.value
                                )

                                OutlineTimePicker(
                                    modifier = Modifier.weight(1.0f),
                                    label = { Text("Time: ") },
                                    onValueChange = { },
                                    value = time.value
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}