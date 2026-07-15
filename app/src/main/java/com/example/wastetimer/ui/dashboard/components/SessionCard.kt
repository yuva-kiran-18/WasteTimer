package com.example.wastetimer.ui.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SessionCard(
    sessionCount: Int,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Sessions",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "$sessionCount",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

        }

    }

}
