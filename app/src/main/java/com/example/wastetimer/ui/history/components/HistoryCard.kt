package com.example.wastetimer.ui.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wastetimer.data.model.TrackingPeriodUiModel
import com.example.wastetimer.utils.TimeFormatter

@Composable
fun HistoryCard(
    item: HistoryItem,
    onExpand: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpand() }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Tracking Period #${item.periodId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                "Started",
                TimeFormatter.formatDateTime(item.createdAt)
            )

            item.endedAt?.let {

                InfoRow(
                    "Ended",
                    TimeFormatter.formatDateTime(it)
                )

            }

            InfoRow(
                "Sessions",
                item.sessionCount.toString()
            )

            InfoRow(
                "Total",
                TimeFormatter.formatDuration(item.totalDuration)
            )

            if (item.expanded && item.sessions.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                item.sessions.forEach {

                    SessionItem(
                        title = "Session #${it.sessionId}",
                        duration = TimeFormatter.formatDuration(it.duration)
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

        Text(title)

        Text(
            value,
            fontWeight = FontWeight.SemiBold
        )

    }

}
