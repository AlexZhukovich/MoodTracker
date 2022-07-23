package com.alexzh.moodtracker.design.component.chart

import androidx.annotation.DrawableRes

data class HappinessIndicators(
    @DrawableRes val angryIconRes: Int,
    @DrawableRes val confusedIconRes: Int,
    @DrawableRes val neutralIconRes: Int,
    @DrawableRes val happyIconRes: Int,
    @DrawableRes val excitedIconRes: Int
)