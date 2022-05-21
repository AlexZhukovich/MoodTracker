package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes

interface IconMapper {

    @DrawableRes
    fun getIconResId(happinessLevel: Int, @DrawableRes fallbackIcon: Int): Int
}