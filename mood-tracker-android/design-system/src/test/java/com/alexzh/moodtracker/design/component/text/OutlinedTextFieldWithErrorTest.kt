package com.alexzh.moodtracker.design.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexzh.moodtracker.design.common.FontScale
import com.alexzh.moodtracker.design.theme.AppTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class OutlinedTextFieldWithErrorTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false),
    )

    @Test
    fun outlinedTextFieldWithError_singleLine_noError(
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
                            val text = remember { mutableStateOf("Short text ") }

                            OutlinedTextFieldWithError(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                value = text.value,
                                onValueChange = { text.value = it },
                                leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                                trailingIcon = { Icon(Icons.Outlined.Build, contentDescription = null) },
                                label = "Label"
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun outlinedTextFieldWithError_singleLine_hasSingleLineError(
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
                            val text = remember { mutableStateOf("Short text ") }
                            val errorLabel = "Error message"

                            OutlinedTextFieldWithError(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                value = text.value,
                                onValueChange = { text.value = it },
                                leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                                trailingIcon = { Icon(Icons.Outlined.Build, contentDescription = null) },
                                label = "Label",
                                isError = true,
                                errorLabel = errorLabel
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun outlinedTextFieldWithError_MultilineLine_hasMultilineLineError(
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
                            val text = remember { mutableStateOf("Long long long long long long long long long long long long long  text ") }
                            val errorLabel = "Long long long long long long long long long long long long long long long long error message"

                            OutlinedTextFieldWithError(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                value = text.value,
                                onValueChange = { text.value = it },
                                leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                                trailingIcon = { Icon(Icons.Outlined.Build, contentDescription = null) },
                                label = "Label",
                                isError = true,
                                errorLabel = errorLabel
                            )
                        }
                    }
                }
            }
        }
    }
}