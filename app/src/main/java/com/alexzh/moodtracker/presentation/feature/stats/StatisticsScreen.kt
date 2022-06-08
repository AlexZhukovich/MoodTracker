package com.alexzh.moodtracker.presentation.feature.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.DateToHappinessChart
import com.alexzh.moodtracker.presentation.component.Section

@ExperimentalMaterial3Api
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.statisticsScreen_title)) }
            )
        }
    ) { paddingValues ->
        val uiState = viewModel.state.value

        when {
            uiState.loading -> LoadingScreen(paddingValues)
            else -> LoadedSuccessfullyScreen(paddingValues, uiState)
        }

        LaunchedEffect(Unit) {
            viewModel.onEvent(StatisticsEvent.Load)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun LoadedSuccessfullyScreen(
    paddingValues: PaddingValues,
    state: StatisticsScreenState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(
                start = 0.dp,
                top = paddingValues.calculateTopPadding(),
                end = 0.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        item {
            Section(stringResource(R.string.statisticsScreen_moodChart_label)) {
                if (state.data.isEmpty()) {
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
                } else {
                    DateToHappinessChart(
                        state.data,
                        modifier = Modifier.fillMaxWidth()
                            .height(250.dp)
                            .padding(10.dp),
                        axisColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        showYAxis = false
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun LoadingScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}