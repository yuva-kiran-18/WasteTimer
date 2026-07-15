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
import androidx.compose.material3.CardDefaults
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
    item: TrackingPeriodUiModel,
    onExpand: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpand() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        "Tracking Period #${item.periodId}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        TimeFormatter.formatDateTime(item.createdAt),
                        style = MaterialTheme.typography.bodySmall
                    )

                }

                Row {

                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            null
                        )
                    }

                    IconButton(
                        onClick = onExpand
                    ) {
                        Text(
    text = if (item.expanded) "▲" else "▼"
)
                    }

                }

            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Sessions : ${item.sessionCount}"
            )

            Text(
                "Total : ${
                    TimeFormatter.formatDuration(item.totalDuration)
                }"
            )

            item.endedAt?.let {

                Text(
                    "Ended : ${
                        TimeFormatter.formatDateTime(it)
                    }"
                )

            }

            if (item.expanded) {

                Spacer(Modifier.height(12.dp))

                Divider()

                Spacer(Modifier.height(8.dp))

                item.sessions.forEach { session ->

                    SessionItem(
                        title = "Session #${session.sessionId}",
                        duration = TimeFormatter.formatDuration(
                            session.duration
                        )
                    )

                }

            }

        }

    }

}
