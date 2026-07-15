package com.example.wastetimer.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {

    private val dateFormatter = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

    private val timeFormatter = SimpleDateFormat(
        "HH:mm:ss",
        Locale.getDefault()
    )

    private val dateTimeFormatter = SimpleDateFormat(
        "dd MMM yyyy HH:mm:ss",
        Locale.getDefault()
    )

    fun formatDuration(durationMillis: Long): String {

        val totalSeconds = durationMillis / 1000

        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return "%02d:%02d:%02d".format(
            hours,
            minutes,
            seconds
        )
    }

    fun formatDate(epochMillis: Long): String =
        dateFormatter.format(Date(epochMillis))

    fun formatTime(epochMillis: Long): String =
        timeFormatter.format(Date(epochMillis))

    fun formatDateTime(epochMillis: Long): String =
        dateTimeFormatter.format(Date(epochMillis))
}
