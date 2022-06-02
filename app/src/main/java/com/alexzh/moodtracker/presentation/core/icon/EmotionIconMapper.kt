package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.presentation.core.EmotionItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem
import com.alexzh.moodtrackerdb.EmotionEntity

interface EmotionIconMapper {

    fun mapToEmotionItem(
        emotionEntity: EmotionEntity,
        @DrawableRes fallbackIcon: Int
    ): EmotionItem

    fun mapToSelectableEmotionItem(
        emotionEntity: EmotionEntity,
        @DrawableRes fallbackIcon: Int,
        isSelected: Boolean
    ): SelectableEmotionItem
}