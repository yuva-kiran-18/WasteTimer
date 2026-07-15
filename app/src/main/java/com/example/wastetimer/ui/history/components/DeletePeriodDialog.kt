package com.example.wastetimer.ui.history.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeletePeriodDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text("Delete Tracking Period")

        },

        text = {

            Text(
                "This will permanently delete the tracking period and all of its sessions."
            )

        },

        confirmButton = {

            Button(
                onClick = onConfirm
            ) {

                Text("Delete")

            }

        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    "Cancel",
                    color = MaterialTheme.colorScheme.primary
                )

            }

        }

    )

}
