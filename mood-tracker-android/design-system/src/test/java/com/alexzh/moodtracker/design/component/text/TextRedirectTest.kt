package com.alexzh.moodtracker.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.moodtracker.design.common.FontScale
import com.alexzh.moodtracker.design.common.highlightLastItem
import com.alexzh.moodtracker.design.theme.AppTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class TextRedirectTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false)
    )

    @Test
    fun textRedirect_defaultState(
        @TestParameter isDarkTheme: Boolean
    ) {
        paparazzi.snapshot {
            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (fontScale in FontScale.values()) {
                        CompositionLocalProvider(
                            LocalDensity provides Density(
                                density = LocalDensity.current.density,
                                fontScale = fontScale.value
                            )
                        ) {
                            TextRedirect(
                                modifier = Modifier.fillMaxWidth(),
                                text = arrayOf(
                                    "This is first element",
                                    "This is a second element",
                                    "This is the third element"
                                ).highlightLastItem(),
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}