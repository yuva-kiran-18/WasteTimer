package com.example.wastetimer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wastetimer.viewmodel.MainViewModel

@Composable
fun StatisticsScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    // In a full production app, these would be collected from the ViewModel
    // representing Room Database aggregates.
    val mockWeeklyData = listOf(120f, 45f, 200f, 80f, 150f, 30f, 90f) // Minutes wasted
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onNavigateBack) { Text("← Back") }
                Text("Analytics", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(start = 16.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text("Last 7 Days (Minutes)", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            BarChart(data = mockWeeklyData)
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text("Lifetime Insights", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(title = "Most Productive Day", value = "Tuesday", color = Color(0xFF4CAF50), modifier = Modifier.weight(1f))
                StatCard(title = "Highest Wasted Day", value = "Sunday", color = Color(0xFFF44336), modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(title = "Average Session", value = "14m 30s", color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                StatCard(title = "Total Sessions", value = "142", color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun BarChart(data: List<Float>) {
    val maxData = data.maxOrNull() ?: 1f
    val barColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        val barWidth = size.width / (data.size * 2f)
        val space = barWidth
        
        data.forEachIndexed { index, value ->
            val barHeight = (value / maxData) * size.height
            val x = index * (barWidth + space) + (space / 2f)
            val y = size.height - barHeight
            
            drawRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight)
            )
        }
    }
}
