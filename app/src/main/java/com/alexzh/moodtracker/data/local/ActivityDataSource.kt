package com.alexzh.moodtracker.data.local

import com.alexzh.moodtrackerdb.ActivityEntity

interface ActivityDataSource {

    suspend fun getActionById(id: Long): ActivityEntity?

    suspend fun getActivities(): List<ActivityEntity>

    suspend fun insertAction(name: String, icon: String, id: Long? = null)

    suspend fun deleteAction(id: Long)
}