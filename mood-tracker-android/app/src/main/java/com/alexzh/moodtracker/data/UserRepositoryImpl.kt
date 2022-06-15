package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.exception.Unauthorized
import com.alexzh.moodtracker.data.exception.UserInfoIsNotAvailableException
import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import com.alexzh.moodtracker.data.remote.service.UserRemoteService
import com.alexzh.moodtracker.data.util.Result
import java.net.ConnectException

class UserRepositoryImpl(
    private val remoteService: UserRemoteService
): UserRepository {

    override suspend fun getUserInfo(
        userId: Long
    ): Result<UserInfoModel> {
        return try {
            val response = remoteService.getUserInfo(userId)
            val userInfo = response.body()
            when {
                response.code() == 200 && userInfo != null ->
                    Result.Success(userInfo)
                response.code() == 401 -> Result.Error(Unauthorized())
                else -> Result.Error(UserInfoIsNotAvailableException())
            }
        } catch (ex: ConnectException) {
            Result.Error(ServiceUnavailableException())
        }
    }
}