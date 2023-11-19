package com.cryptoapp.demo.helper

import android.util.Log


public fun logthis(msg: String, tag: String = "TAGTAG") {
    Log.d(tag, msg)
}
fun Double.formatToTwoDecimalPlaces(): String {
    return String.format("%.2f", this)
}