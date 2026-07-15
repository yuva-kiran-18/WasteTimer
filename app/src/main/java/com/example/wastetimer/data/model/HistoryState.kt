package com.example.wastetimer.data.model

data class HistoryState(
    val periods: List<HistoryItem> = emptyList(),
    val isLoading: Boolean = true
)
