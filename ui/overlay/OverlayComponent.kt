package com.example.wastetimer.ui.overlay

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun OverlayComponent(
    currentSessionTime: String = "00:00" // In a real setup, pass StateFlow here
) {
    var isExpanded by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // Animations based on requirements
    val size by animateDpAsState(targetValue = if (isExpanded) 120.dp else 34.dp, label = "size")
    val alpha by animateFloatAsState(targetValue = if (isExpanded) 1.0f else 0.2f, label = "alpha")

    // Auto-collapse after 5 seconds
    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            delay(5000)
            isExpanded = false
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(size)
            .alpha(alpha)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(percent = 50)
            )
            .clickable { isExpanded = !isExpanded }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    // Edge snapping logic would go here
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isExpanded) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⏱ Elapsed", fontSize = 10.sp)
                Text(currentSessionTime, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        } else {
            Text("⏱", fontSize = 14.sp)
        }
    }
}
