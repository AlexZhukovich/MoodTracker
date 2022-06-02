package com.alexzh.moodtracker.presentation.feature.today

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.Section
import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem
import com.google.accompanist.flowlayout.FlowRow
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import java.util.*

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun TodayScreen(
    viewModel: TodayViewModel,
    onAdd: () -> Unit
) {
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Kalendar(
                kalendarStyle = KalendarStyle(
                    kalendarBackgroundColor = MaterialTheme.colorScheme.surface,
                    kalendarSelector = KalendarSelector.Rounded(
                        selectedColor = MaterialTheme.colorScheme.inversePrimary,
                        todayColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ),
                kalendarType = KalendarType.Oceanic(KalendarShape.DefaultRectangle, startDate = uiState.date.minusDays(3)),
                selectedDay = uiState.date,
                onCurrentDayClick = { day, event ->
                    viewModel.onEvent(TodayEvent.OnDateChange(day))
                }
            )
        },
        floatingActionButton = {
             ExtendedFloatingActionButton(
                 onClick = { onAdd() },
                 text = { Text(stringResource(R.string.todayScreen_add_label)) },
                 icon = {
                     Icon(
                         Icons.Filled.Add,
                         contentDescription = stringResource(R.string.todayScreen_add_label)
                     )
                 }
             )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding() + 4.dp,
                        bottom = it.calculateBottomPadding() + 8.dp
                    )
            ) {
                item {
                    Section(stringResource(R.string.todayScreen_emotions_label)) {


                        when {
                            uiState.isLoading -> LoadingScreen()
                            uiState.items.isEmpty() -> EmptyScreen()
                            else -> SuccessScreen(uiState.items)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxWidth().height(72.dp * 2),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxWidth().height(72.dp * 2),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(72.dp),
                painter = painterResource(R.drawable.ic_no_data),
                contentDescription = null
            )
            Text(stringResource(R.string.todayScreen_noData_label), fontSize = 18.sp)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun SuccessScreen(items: List<MoodDataItem>) {
    val lastIndex = items.lastIndex
    Column(modifier = Modifier.fillMaxWidth()) {
        items.forEachIndexed { index, item ->
            MoodItem(item)
            if (index < lastIndex) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun MoodItem(
    item: MoodDataItem
) {
    Row(modifier = Modifier.padding(4.dp)) {
        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(item.iconId),
            contentDescription = null
        )

        Column(
            modifier = Modifier.weight(1.0f)
                .padding(horizontal = 8.dp)
        ) {
            item.note?.let {
                Text(text = it, fontSize = 14.sp)
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 0.dp,
            ) {
                item.activities.forEach { activity ->
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = activity.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(activity.icon),
                                contentDescription = null
                            )
                        },
                        enabled = false,
                        border = AssistChipDefaults.assistChipBorder(disabledBorderColor = MaterialTheme.colorScheme.outline),
                        colors = AssistChipDefaults.assistChipColors(
                            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            Text(
                text = item.formattedDate, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}