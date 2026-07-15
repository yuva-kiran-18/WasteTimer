package com.example.wastetimer.ui.dashboard.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
            Text("Reset Timer")
        },

        text = {
            Text(
                "This will permanently close the current tracking period and start a new one."
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
                Text("Cancel")
            }

        }

    )

}
