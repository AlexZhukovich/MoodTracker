package com.alexzh.moodtracker.design.component.button.loading

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.moodtracker.design.common.FontScale
import com.alexzh.moodtracker.design.theme.AppTheme
import com.android.ide.common.rendering.api.SessionParams
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class LoadingButtonTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false, screenHeight = 1),
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
    )

    @Test
    fun loadingButton_defaultState(
        @TestParameter isDarkTheme: Boolean,
        @TestParameter fontScale: FontScale
    ) {
        paparazzi.snapshot {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = fontScale.value
                )
            ) {
                AppTheme(darkTheme = isDarkTheme) {
                    LoadingButton(
                        onClick = { },
                        text = "Button"
                    )
                }
            }
        }
    }



    @Test
    fun loadingButton_loadingState(
        @TestParameter isDarkTheme: Boolean,
        @TestParameter fontScale: FontScale
    ) {
        paparazzi.snapshot {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = fontScale.value
                )
            ) {
                AppTheme(darkTheme = isDarkTheme) {
                    LoadingButton(
                        onClick = { },
                        text = "Button",
                        isLoading = true
                    )
                    Thread.sleep(500)
                }
            }
        }
    }
}


