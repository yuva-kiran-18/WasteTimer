package com.example.wastetimer.ui.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wastetimer.data.model.HistoryItem
import com.example.wastetimer.utils.TimeFormatter

@Composable
fun HistoryCard(
    item: HistoryItem,
    onExpand: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpand() }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Tracking Period #${item.periodId}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                title = "Started",
                value = TimeFormatter.formatDateTime(item.createdAt)
            )

            item.endedAt?.let {
                InfoRow(
                    title = "Ended",
                    value = TimeFormatter.formatDateTime(it)
                )
            }

            InfoRow(
                title = "Sessions",
                value = item.sessionCount.toString()
            )

            InfoRow(
                title = "Total",
                value = TimeFormatter.formatDuration(item.totalDuration)
            )

            if (item.expanded && item.sessions.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                item.sessions.forEach { session ->

                    SessionItem(
                        title = "Session #${session.sessionId}",
                        duration = TimeFormatter.formatDuration(session.duration)
                    )

                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
