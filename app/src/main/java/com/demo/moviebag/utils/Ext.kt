package com.demo.moviebag.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import kotlin.math.pow
import kotlin.math.roundToInt

@SuppressLint("SimpleDateFormat")
fun String.getFormattedDate(): String {

    return try {
        this.substringBefore("T")
    } catch (e: Exception) {
        ""
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
