package com.alexzh

import io.ktor.server.application.*
import com.alexzh.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureRouting()
    configureSerialization()
    configureHTTP()
    configureMonitoring()
    configureSecurity()
}
