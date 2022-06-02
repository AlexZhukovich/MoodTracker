package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtrackerdb.ActivityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityDataSourceImpl(
    db: MoodTrackerDatabase
): ActivityDataSource {
    private val queries = db.activityEntityQueries

    override suspend fun getActionById(id: Long): ActivityEntity? {
        return withContext(Dispatchers.IO) {
            queries.getActionById(id).executeAsOneOrNull()
        }
    }

    override suspend fun getActivities(): List<ActivityEntity> {
        return queries.getActivities().executeAsList()
    }

    override suspend fun insertAction(name: String, icon: String, id: Long?) {
        return withContext(Dispatchers.IO) {
            queries.insert(id, name, icon)
        }
    }

    override suspend fun deleteAction(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }
}