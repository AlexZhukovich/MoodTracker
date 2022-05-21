package com.alexzh.moodtracker.presentation.feature.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.Chip
import com.alexzh.moodtracker.presentation.component.Section
import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem

@ExperimentalMaterial3Api
@Composable
fun TodayScreen(
    viewModel: TodayViewModel,
    onAdd: () -> Unit
) {
    val uiState by viewModel.uiState

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        floatingActionButton = {
             ExtendedFloatingActionButton(
                 onClick = { onAdd() },
                 text = { Text("Add") },
                 icon = {
                     Icon(
                         Icons.Filled.Add,
                         contentDescription = "Add"
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
                    Section("Mood") {
                        when (uiState) {
                            TodayScreenState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(80.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            is TodayScreenState.Success -> {
                                val data = (uiState as TodayScreenState.Success).data
                                data.forEachIndexed { index, item ->
                                    MoodItem(item)

                                    if (index < data.lastIndex) {
                                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.loadMood()
    }
}

@Composable
fun MoodItem(
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
                .padding(8.dp)
        ) {
            item.note?.let {
                Text(text = it, fontSize = 14.sp)
            }

            LazyRow {
                items(item.activities) {
                    Chip(text = it)
                }
            }

            Text(
                text = item.formattedDate, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}