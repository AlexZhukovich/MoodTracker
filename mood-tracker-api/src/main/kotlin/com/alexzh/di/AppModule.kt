package com.alexzh.di

import com.alexzh.auth.Encryptor
import com.alexzh.auth.EncryptorImpl
import com.alexzh.auth.JwtService
import com.alexzh.auth.JwtServiceImpl
import com.alexzh.controller.AuthController
import com.alexzh.controller.UserInfoController
import com.alexzh.data.UserRepository
import com.alexzh.data.UserRepositoryImpl
import com.alexzh.data.database.DatabaseConnector
import com.alexzh.data.database.H2DatabaseConnector
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("secret")) { System.getenv()["secret"] }

    single<Encryptor> {
        EncryptorImpl(secretKey = get(named("secret")))
    }

    single<JwtService> {
        JwtServiceImpl(secretKey = get(named("secret")))
    }

    single<DatabaseConnector> { H2DatabaseConnector() }
    factory<UserRepository> { UserRepositoryImpl() }

    factory {
        AuthController(
            encryptor = get(),
            jwtService = get(),
            userRepository = get()
        )
    }
    factory { UserInfoController() }
}