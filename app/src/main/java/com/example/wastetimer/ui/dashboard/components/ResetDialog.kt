package com.example.wastetimer.ui.dashboard.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ResetDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text("Reset Tracking")
        },

        text = {
            Text(
                "This will permanently close the current tracking period and start a new one.\n\nThis action cannot be undone."
            )
        },

        confirmButton = {

            Button(
                onClick = onConfirm
            ) {

                Text("Reset")

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
