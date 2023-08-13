package com.alexzh.moodtracker.data.model.backup

import com.alexzh.moodtracker.data.model.EmotionHistory
import com.google.gson.annotations.SerializedName

data class BackupData(
    @SerializedName("version")
    val version: Int,
    @SerializedName("emotions")
    val list: List<EmotionHistory>
)
