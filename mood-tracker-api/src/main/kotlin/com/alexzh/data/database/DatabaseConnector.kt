package com.alexzh.data.database

import org.jetbrains.exposed.sql.Database

interface DatabaseConnector {

    fun connect(): Database
}