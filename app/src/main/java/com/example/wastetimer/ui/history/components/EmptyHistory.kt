package com.example.wastetimer.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

@Composable
fun EmptyHistory() {

    Column(

        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Icon(

    imageVector = Icons.Default.Info,

    contentDescription = null

)

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(

            text = "No Tracking History",

            style = MaterialTheme.typography.titleMedium

        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(

            text = "Press Reset after tracking to create your first history record.",

            style = MaterialTheme.typography.bodyMedium

        )

    }

}
