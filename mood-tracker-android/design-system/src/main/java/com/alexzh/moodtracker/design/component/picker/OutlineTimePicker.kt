package com.alexzh.moodtracker.design.component.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun OutlineTimePicker(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    value: LocalTime,
    is24HourView: Boolean = true,
    formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"),
    icon: ImageVector = Icons.Filled.DateRange,
    iconContentDescription: String = "Select a time",
    onValueChange: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val timePickerDialog = remember {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(if (is24HourView) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(value.hour)
            .setMinute(value.minute)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            onValueChange(LocalTime.of(timePicker.hour, timePicker.minute))
            focusManager.clearFocus()
        }
        timePicker.addOnNegativeButtonClickListener {
            focusManager.clearFocus()
        }
        timePicker.addOnCancelListener {
            focusManager.clearFocus()
        }

        timePicker
    }

    OutlinedTextField(
        modifier = modifier.onFocusChanged {
           if (it.isFocused) {
               timePickerDialog.show(
                   (context as FragmentActivity).supportFragmentManager,
                   "Date picker"
               )
           }
        },
        label = label,
        value = value.format(formatter),
        onValueChange = { onValueChange( LocalTime.parse(it, formatter)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.clickable {
                    timePickerDialog.show(
                        (context as FragmentActivity).supportFragmentManager,
                        "Time picker"
                    )
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview_OutlineTimePicker() {
    val date1 = remember { mutableStateOf(LocalTime.now()) }
    val date2 = remember { mutableStateOf(LocalTime.now()) }
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            OutlineTimePicker(
                label = { Text("Time") },
                value = date1.value,
                onValueChange = { date1.value = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlineTimePicker(
                label = { Text("Time") },
                value = date2.value,
                formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG),
                onValueChange = { date2.value = it }
            )
        }
    }
}