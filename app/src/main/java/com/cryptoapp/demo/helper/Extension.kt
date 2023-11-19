package com.cryptoapp.demo.helper

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


public fun logthis(msg: String, tag: String = "TAGTAG") {
    Log.d(tag, msg)
}

fun Double.formatToTwoDecimalPlaces(): String {
    return String.format("%.2f", this)
}

fun String.toUnixTime(): Long {
    val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoFormatter.timeZone = TimeZone.getTimeZone("UTC")

    val date = isoFormatter.parse(this)

    return date?.time ?: 0L
}