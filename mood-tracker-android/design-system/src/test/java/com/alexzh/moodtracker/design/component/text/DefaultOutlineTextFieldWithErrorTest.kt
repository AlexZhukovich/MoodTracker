package com.alexzh.moodtracker.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
class DefaultOutlineTextFieldWithErrorTest(
    @TestParameter val isDarkTheme: Boolean
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false, screenHeight = 3400),
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    @Test
    fun defaultOutlineTextFields_noError() {
        paparazzi.snapshot {
            val name = "Test User"
            val email = "test-email@email.com"
            val password = "123456"

            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            NameOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = name,
                                onValueChange = { },
                            )
                            EmailOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = email,
                                onValueChange = { },
                            )
                            PasswordOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = password,
                                onValueChange = { },
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun defaultOutlineTextFields_hasError() {
        paparazzi.snapshot {
            val name = "Test User"
            val email = "test-email@email.com"
            val password = "123456"
            val error = "Error"

            AppTheme(darkTheme = isDarkTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FontScale.values().forEach { fontScale ->
                        TestHarness(fontScale = fontScale.value) {
                            NameOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = name,
                                onValueChange = { },
                                isError = true,
                                errorLabel = error
                            )
                            EmailOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = email,
                                onValueChange = { },
                                isError = true,
                                errorLabel = error
                            )
                            PasswordOutlinedTextFieldWithError(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                value = password,
                                onValueChange = { },
                                isError = true,
                                errorLabel = error
                            )
                        }
                    }
                }
            }
        }
    }
}