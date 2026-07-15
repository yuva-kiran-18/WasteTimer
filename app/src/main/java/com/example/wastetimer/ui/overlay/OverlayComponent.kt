package com.example.wastetimer.ui.overlay

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun OverlayComponent() {

    var expanded by remember {
        mutableStateOf(false)
    }

    var offsetX by remember {
        mutableStateOf(0f)
    }

    var offsetY by remember {
        mutableStateOf(0f)
    }

    val size by animateDpAsState(
        if (expanded) 120.dp else 34.dp,
        label = ""
    )

    val alpha by animateFloatAsState(
        if (expanded) 1f else 0.2f,
        label = ""
    )

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt(),
                    offsetY.roundToInt()
                )
            }
            .size(size)
            .alpha(alpha)
            .background(
                MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clickable {
                expanded = !expanded
            }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        contentAlignment = Alignment.Center
    ) {

        Text("⏱")

    }
}
