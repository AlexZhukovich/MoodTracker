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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@Composable
fun OutlineDatePicker(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    value: LocalDate,
    formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"),
    icon: ImageVector = Icons.Filled.DateRange,
    iconContentDescription: String = "Select a date",
    onValueChange: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val localDateTime = LocalDateTime.of(value.year, value.monthValue, value.dayOfMonth, 0, 0)

    val datePickerDialog = remember {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(localDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli())
            .setCalendarConstraints(CalendarConstraints.Builder().setStart(0L).build())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            onValueChange(
                date.toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()
            )
            focusManager.clearFocus()
        }
        datePicker.addOnNegativeButtonClickListener {
            focusManager.clearFocus()
        }
        datePicker.addOnDismissListener {
            focusManager.clearFocus()
        }
        datePicker
    }

    OutlinedTextField(
        modifier = modifier.onFocusChanged {
            if (it.isFocused) {
                datePickerDialog.show(
                    (context as FragmentActivity).supportFragmentManager,
                    "Date picker"
                )
            }
        },
        label = label,
        value = value.format(formatter),
        onValueChange = { onValueChange( LocalDate.parse(it, formatter)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.clickable {
                    datePickerDialog.show(
                        (context as FragmentActivity).supportFragmentManager,
                        "Date picker"
                    )
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview_OutlineDatePicker() {
    val date1 = remember { mutableStateOf(LocalDate.now()) }
    val date2 = remember { mutableStateOf(LocalDate.now()) }
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            OutlineDatePicker(
                label = { Text("Date") },
                value = date1.value,
                onValueChange = { date1.value = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlineDatePicker(
                label = { Text("Date") },
                value = date2.value,
                formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG),
                onValueChange = { date2.value = it }
            )
        }
    }
}