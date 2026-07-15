package com.example.wastetimer.ui.history.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
                "This permanently deletes this tracking period and every session inside it."
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
                Text("Cancel")
            }

        }

    )

}
