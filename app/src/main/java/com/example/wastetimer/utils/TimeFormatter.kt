package com.example.wastetimer.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {

    private val dateTimeFormatter = SimpleDateFormat(
        "dd MMM yyyy, HH:mm",
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

    fun formatDateTime(epochMillis: Long): String =
        dateTimeFormatter.format(Date(epochMillis))
}
