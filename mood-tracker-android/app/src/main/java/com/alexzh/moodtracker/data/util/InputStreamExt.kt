package com.alexzh.moodtracker.data.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

fun InputStream.convertToString(): String {
    val reader = BufferedReader(InputStreamReader(this))
    val sb = StringBuffer()
    var line: String?

    try {
        while (reader.readLine().also { line = it } != null) {
            sb.append(line).append('\n')
        }
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return sb.toString()
}