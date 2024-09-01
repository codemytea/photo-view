package com.example.photoview.utils

import android.os.Build
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun String.toStringDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.ofEpochSecond(this.toLong(), 0, ZoneOffset.UTC).format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
    } else {
        "Error: Device doesn't support date"
    }
}