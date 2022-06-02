package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.core.EmotionItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem
import com.alexzh.moodtrackerdb.EmotionEntity

class DefaultEmotionIconMapper : EmotionIconMapper {

    override fun mapToEmotionItem(
        emotionEntity: EmotionEntity,
        @DrawableRes fallbackIcon: Int
    ) = EmotionItem(
        emotionId = emotionEntity.id,
        iconRes = mapIcon(emotionEntity.icon, fallbackIcon)
    )

    override fun mapToSelectableEmotionItem(
        emotionEntity: EmotionEntity,
        fallbackIcon: Int,
        isSelected: Boolean
    ) = SelectableEmotionItem(
        emotionId = emotionEntity.id,
        iconRes = mapIcon(emotionEntity.icon, fallbackIcon),
        isSelected = isSelected
    )

    @DrawableRes
    private fun mapIcon(
        iconName: String,
        @DrawableRes fallbackIcon: Int
    ) = when (iconName) {
        "emotion-excited" -> R.drawable.ic_emotion_excited
        "emotion-happy" -> R.drawable.ic_emotion_happy
        "emotion-neutral" -> R.drawable.ic_emotion_neutral
        "emotion-confused" -> R.drawable.ic_emotion_confused
        "emotion-angry" -> R.drawable.ic_emotion_angry
        else -> fallbackIcon
    }
}