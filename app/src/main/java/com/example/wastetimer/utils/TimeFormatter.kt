package com.example.wastetimer.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {

    private val formatter = SimpleDateFormat(
        "dd MMM yyyy, HH:mm",
        Locale.getDefault()
    )

    fun formatDateTime(time: Long): String =
        formatter.format(Date(time))

    fun formatDuration(duration: Long): String {

        val totalSeconds = duration / 1000

        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return "%02d:%02d:%02d".format(
            hours,
            minutes,
            seconds
        )
    }
}
