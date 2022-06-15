package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import com.alexzh.moodtracker.data.util.Result

interface UserRepository {

    suspend fun getUserInfo(
        userId: Long
    ): Result<UserInfoModel>
}