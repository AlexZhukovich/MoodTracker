package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.presentation.core.ActivityItem
import com.alexzh.moodtracker.presentation.core.SelectableActivityItem
import com.alexzh.moodtrackerdb.ActivityEntity

interface ActivityIconMapper {

    fun mapToActivityItem(
        activityEntity: ActivityEntity,
        @DrawableRes fallbackIcon: Int
    ): ActivityItem

    fun mapToSelectableActivityItem(
        activityEntity: ActivityEntity,
        @DrawableRes fallbackIcon: Int,
        isSelected: Boolean
    ): SelectableActivityItem
}