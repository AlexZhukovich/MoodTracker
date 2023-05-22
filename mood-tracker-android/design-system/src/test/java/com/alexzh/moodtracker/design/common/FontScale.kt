package com.alexzh.moodtracker.design.common

enum class FontScale(val value: Float) {
    SMALL(0.85f),
    NORMAL(1f),
    LARGE(1.15f),
    HUGE(1.3f);

    override fun toString(): String {
        return when (this) {
            SMALL -> "SMALL_FONT_SCALE"
            NORMAL -> "NORMAL_FONT_SCALE"
            LARGE -> "LARGE_FONT_SCALE"
            HUGE -> "HUGE_FONT_SCALE"
        }
    }
}
