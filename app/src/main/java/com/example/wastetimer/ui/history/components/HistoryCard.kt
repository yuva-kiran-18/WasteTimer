package com.example.wastetimer.ui.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wastetimer.data.model.HistoryItem

@Composable
fun HistoryCard(
    item: HistoryItem,
    onExpand: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onExpand()
            }
    ) {

        Column(
            Modifier.padding(16.dp)
        ) {

            Text(
                "Tracking Period #${item.periodId}",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text("Sessions : ${item.sessionCount}")

            Text("Total : ${format(item.totalDuration)}")

            if (item.expanded) {

                Spacer(Modifier.height(12.dp))

                item.sessions.forEach {

                    SessionItem(

                        title = "Session ${it.sessionId}",

                        duration = format(it.duration)

                    )

                }

            }

        }

    }

}

private fun format(duration: Long): String {

    val s = duration / 1000

    return "%02d:%02d:%02d".format(
        s / 3600,
        (s % 3600) / 60,
        s % 60
    )

}
