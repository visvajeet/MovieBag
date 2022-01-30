package com.demo.moviebag.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

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

fun Context.copyToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    //showToast("copied!")
}
