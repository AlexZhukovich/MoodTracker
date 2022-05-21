package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.R

class DefaultIconMapper : IconMapper {
    override fun getIconResId(happinessLevel: Int, @DrawableRes fallbackIcon: Int): Int {
        return when(happinessLevel) {
            5 -> R.drawable.ic_emotion_excited
            4 -> R.drawable.ic_emotion_happy
            3 -> R.drawable.ic_emotion_neutral
            2 -> R.drawable.ic_emotion_confused
            1 -> R.drawable.ic_emotion_angry
            else -> fallbackIcon
        }
    }
}