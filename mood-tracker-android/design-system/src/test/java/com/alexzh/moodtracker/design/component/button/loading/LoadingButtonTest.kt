package com.alexzh.moodtracker.design.component.button.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@RunWith(TestParameterInjector::class)
class LoadingButtonTest(
    @TestParameter val isDarkTheme: Boolean,
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false),
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )

    @Test
    fun loadingButton_defaultState() {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            LoadingButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Button",
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun loadingButton_loadingState() {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            LoadingButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Button",
                                isLoading = true,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}


