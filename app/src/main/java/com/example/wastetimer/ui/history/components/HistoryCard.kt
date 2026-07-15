package com.example.wastetimer.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wastetimer.data.model.HistoryItem

@Composable
fun HistoryCard(
    item: HistoryItem
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Period #${item.periodId}",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${item.sessionCount} Sessions",
                    style = MaterialTheme.typography.labelMedium
                )

            }

            Text(
                text = "Total: ${formatDuration(item.totalDuration)}",
                modifier = Modifier.padding(top = 8.dp)
            )

        }

    }

}

private fun formatDuration(duration: Long): String {

    val seconds = duration / 1000

    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60

    return "%02d:%02d:%02d".format(h, m, s)
}
