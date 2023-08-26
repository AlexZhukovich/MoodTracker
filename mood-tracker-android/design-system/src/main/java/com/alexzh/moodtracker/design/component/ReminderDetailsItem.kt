package com.alexzh.moodtracker.design.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ReminderDetailsItem(
    time: String,
    isEnabled: Boolean,
    repeatDays: List<DayOfWeek>,
    availableDays: Array<DayOfWeek>,
    isExpanded: Boolean,
    @StringRes collapseButtonContentDescription: Int,
    @StringRes expandButtonContentDescription: Int,
    @StringRes deleteButtonText: Int,
    @StringRes deleteReminderContentDescription: Int,
    onEnabled: () -> Unit,
    onRepeatDayChange: (DayOfWeek) -> Unit,
    onExpand: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onExpand() },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = time,
                    fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Normal,
                    style = MaterialTheme.typography.displayMedium,
                )

                Column {
                    IconButton(onClick = { onExpand() }) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (!isExpanded) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                                contentDescription = stringResource(
                                    if (isExpanded) collapseButtonContentDescription else expandButtonContentDescription,
                                    time
                                )
                            )
                        }
                    }
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = { onEnabled() }
                    )
                }
            }

            AnimatedVisibility(visible = !isExpanded) {
                Text(
                    text = repeatDays.joinToString(", ") {
                        it.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        )
                    },
                    fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Normal
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        availableDays.forEach {  dayOfWeek ->
                            RemindedDayIndicator(
                                dayOfWeek = dayOfWeek,
                                isSelected = repeatDays.contains(dayOfWeek),
                                onSelected = { onRepeatDayChange(dayOfWeek) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    TextButton(
                        onClick = { onDelete() },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Delete,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                contentDescription = stringResource(deleteReminderContentDescription, time)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(deleteButtonText),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RemindedDayIndicator(
    dayOfWeek: DayOfWeek,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                CircleShape
            )
            .clip(CircleShape)
            .border(
                if (isSelected) 0.dp else 1.dp,
                MaterialTheme.colorScheme.outline,
                CircleShape
            )
            .clickable { onSelected(!isSelected) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}