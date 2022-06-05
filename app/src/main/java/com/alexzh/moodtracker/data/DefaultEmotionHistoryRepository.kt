package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtrackerdb.ActivityEntity
import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.ZonedDateTime

class DefaultEmotionHistoryRepository(
    private val db: MoodTrackerDatabase
) : EmotionHistoryRepository {
    private val emotionsQueries = db.emotionEntityQueries
    private val activitiesQueries = db.activityEntityQueries
    private val emotionHistoryQueries = db.emotionHistoryEntityQueries
    private val emotionHistoryToActivityQueries = db.emotionHistoryToActivityEntityQueries
    private val emotionHistoryWithActivitiesQueries = db.emotionHistoryWithActivitiesQueries

    override fun getEmotionsHistoryByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): Flow<Result<List<EmotionHistory>>> {
        return flow {
            emit(Result.Loading())

            val localEmotionHistoryWithActivity = emotionHistoryWithActivitiesQueries
                .getEmotionHistoryWithActivitiesByDate(startDate, endDate)
                .executeAsList()
            val activities = activitiesQueries
                .getActivities()
                .executeAsList()

            emit(
                Result.Success(
                    localEmotionHistoryWithActivity.map { emotionHistoryWithActivity ->
                        EmotionHistory(
                            id = emotionHistoryWithActivity.id,
                            date = emotionHistoryWithActivity.date,
                            emotion = Emotion(
                                id = emotionHistoryWithActivity.emotionId,
                                name = emotionHistoryWithActivity.emotionName,
                                happinessLevel = emotionHistoryWithActivity.emotionHappinessLevel,
                                icon = emotionHistoryWithActivity.emotionIcon
                            ),
                            activities = emotionHistoryWithActivity.activityIds.split(",").map { activityId ->
                                val activity = activities.first { it.id == activityId.toLong() }
                                Activity(
                                    id = activity.id,
                                    name = activity.name,
                                    icon = activity.icon
                                )
                            },
                            note = emotionHistoryWithActivity.note
                        )
                    }
                )
            )
        }
    }

    override fun getEmotionHistoryById(
        id: Long
    ): Flow<Result<EmotionHistory?>> {
        return flow {
            emit(Result.Loading())

            val localEmotionHistoryWithActivity = emotionHistoryWithActivitiesQueries
                .getEmotionHistoryWithActivitiesById(id)
                .executeAsOneOrNull()
            val activities = activitiesQueries
                .getActivities()
                .executeAsList()

            if (localEmotionHistoryWithActivity == null) {
                emit(Result.Error("No items found"))
            } else {
                emit(
                    Result.Success(
                        mapToEmotionHistory(
                            localEmotionHistoryWithActivity,
                            activities
                        )
                    )
                )
            }
        }
    }

    override suspend fun getEmotions(): List<Emotion> {
        return emotionsQueries.getEmotions().executeAsList().map { emotion ->
            Emotion(
                id = emotion.id,
                name = emotion.name,
                happinessLevel = emotion.happinessLevel,
                icon = emotion.icon
            )
        }
    }

    override suspend fun getActivities(): List<Activity> {
        return activitiesQueries.getActivities().executeAsList().map { activity ->
            Activity(
                id = activity.id,
                name = activity.name,
                icon = activity.icon
            )
        }
    }

    override suspend fun insertOrUpdateEmotionHistory(
        id: Long?,
        date: ZonedDateTime,
        emotionId: Long,
        selectedActivityIds: List<Long>,
        note: String?
    ) {
        val activityIds = if (id != null) {
            emotionHistoryToActivityQueries
                .getActivitiesByEmotionHistory(id)
                .executeAsList()
                .map { it.activityId }
        } else emptyList()

        db.transaction {
            emotionHistoryQueries.insert(
                date = date,
                emotionId = emotionId,
                note = note?.ifEmpty { null },
                id = id
            )
            val newId = emotionHistoryQueries.getLastInsertedRowId().executeAsOneOrNull()
            if (newId != null) {
                val activitiesToDelete = activityIds.filter { !selectedActivityIds.contains(it) }
                val activitiesToInsert = selectedActivityIds.filter { !activityIds.contains(it) }

                activitiesToDelete.forEach { activityId ->
                    emotionHistoryToActivityQueries.deleteByEmotionHistoryIdAndActivityId(
                        newId,
                        activityId
                    )
                }

                activitiesToInsert.forEach { activityId ->
                    emotionHistoryToActivityQueries.insert(
                        emotionHistoryId = newId,
                        activityId = activityId,
                        id = null
                    )
                }
            } else {
                rollback()
            }
        }
    }

    override suspend fun deleteEmotionHistory(emotionHistory: EmotionHistory) {
        db.transaction {
            val activitiesToDelete = emotionHistory.activities.map { it.id }
            activitiesToDelete.forEach {
                emotionHistoryToActivityQueries.delete(it)
            }

            emotionHistoryQueries.delete(emotionHistory.id)
        }
    }

    private fun mapToEmotionHistory(
        emotionHistoryWithActivity: EmotionHistoryWithActivities,
        activities: List<ActivityEntity>
    ) = EmotionHistory(
        id = emotionHistoryWithActivity.id,
        date = emotionHistoryWithActivity.date,
        emotion = Emotion(
            id = emotionHistoryWithActivity.emotionId,
            name = emotionHistoryWithActivity.emotionName,
            happinessLevel = emotionHistoryWithActivity.emotionHappinessLevel,
            icon = emotionHistoryWithActivity.emotionIcon
        ),
        activities = emotionHistoryWithActivity.activityIds.split(",").map { activityId ->
            val activity = activities.first { it.id == activityId.toLong() }
            Activity(
                id = activity.id,
                name = activity.name,
                icon = activity.icon
            )
        },
        note = emotionHistoryWithActivity.note
    )
}