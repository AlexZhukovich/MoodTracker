package com.alexzh.moodtracker.presentation.feature.settings.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.design.component.ReminderDetailsItem
import java.time.DayOfWeek
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRemindersScreen(
    viewModel: SettingsRemindersViewModel,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settingsRemindersScreen_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back_contentDescription)
                        )
                    }
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onEvent(SettingsRemindersEvent.ShowAddReminderDialog) },
                text = { Text(stringResource(R.string.settingsRemindersScreen_add_label)) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.settingsRemindersScreen_add_label)
                    )
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.reminders.isNotEmpty() ->
                SuccessScreen(
                    paddingValues = paddingValues,
                    reminders = uiState.reminders,
                    onExpand = { viewModel.onEvent(SettingsRemindersEvent.OnExpandCollapse(it)) },
                    onEnabled = { viewModel.onEvent(SettingsRemindersEvent.OnEnabledDisabled(it)) },
                    onRepeatDayChange = { reminderInfoUi, dayOfWeek ->
                        viewModel.onEvent(SettingsRemindersEvent.OnRepeatDaysChange(reminderInfoUi, dayOfWeek))
                    },
                    onDelete = { viewModel.onEvent(SettingsRemindersEvent.ShowDeleteReminderDialog(it)) }
                )
        }

        if (uiState.showAddReminderDialog) {
            AddReminderDialog(
                onDismiss = { viewModel.onEvent(SettingsRemindersEvent.HideAddReminderDialog) },
                onConfirm = { hour, minute ->
                    viewModel.onEvent(SettingsRemindersEvent.AddReminder(LocalTime.of(hour, minute)))
                }
            )
        }

        if (uiState.showDeleteReminderDialog) {
            DeleteReminderDialog(
                onDismiss = { viewModel.onEvent(SettingsRemindersEvent.HideDeleteReminderDialog) },
                onConfirm = { viewModel.onEvent(SettingsRemindersEvent.DeleteReminder) }
            )
        }
    }
}

@Composable
private fun SuccessScreen(
    paddingValues: PaddingValues,
    reminders: List<ReminderInfoUi>,
    onExpand: (ReminderInfoUi) -> Unit,
    onEnabled: (ReminderInfoUi) -> Unit,
    onRepeatDayChange: (ReminderInfoUi, DayOfWeek) -> Unit,
    onDelete: (ReminderInfoUi) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 4.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            )
    ) {
        items(reminders) { reminderInfoUi ->
            ReminderDetailsItem(
                time = reminderInfoUi.formattedTime,
                isEnabled = reminderInfoUi.isEnabled,
                repeatDays = reminderInfoUi.repeatDays,
                availableDays = DayOfWeek.values(),
                isExpanded =  reminderInfoUi.isExpanded,
                collapseButtonContentDescription = R.string.settingsRemindersScreen_collapseReminder_contentDescription,
                expandButtonContentDescription = R.string.settingsRemindersScreen_expandReminder_contentDescription,
                deleteButtonText = R.string.settingsRemindersScreen_deleteReminder_label,
                deleteReminderContentDescription = R.string.settingsRemindersScreen_deleteReminder_contentDescription,
                onEnabled = { onEnabled(reminderInfoUi) } ,
                onRepeatDayChange = { onRepeatDayChange(reminderInfoUi, it) },
                onExpand = { onExpand(reminderInfoUi) },
                onDelete = { onDelete(reminderInfoUi) }
            )
        }
    }
}

@Composable
private fun DeleteReminderDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.settingsRemindersScreen_deleteReminderDialog_title)) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddReminderDialog(
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = stringResource(id = R.string.settingsRemindersScreen_addReminderDialog_title))
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(timePickerState.hour, timePickerState.minute) }
            ) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}
