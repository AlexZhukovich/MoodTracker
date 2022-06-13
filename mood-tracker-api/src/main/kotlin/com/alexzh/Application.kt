package com.alexzh

import com.alexzh.data.database.DatabaseConnector
import com.alexzh.plugins.*
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.get

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureStatusPages()
    configureRouting()
    configureContentNegotiation()
    configureSerialization()
    configureHTTP()
    configureMonitoring()
    configureAuthentication()

    val databaseConnector: DatabaseConnector = get(DatabaseConnector::class.java)
    databaseConnector.connect()
}
