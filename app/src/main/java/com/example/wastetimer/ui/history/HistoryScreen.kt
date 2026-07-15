package com.example.wastetimer.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wastetimer.ui.history.components.DeletePeriodDialog
import com.example.wastetimer.ui.history.components.HistoryCard
import com.example.wastetimer.viewmodel.HistoryViewModel
import com.example.wastetimer.data.model.TrackingPeriodUiModel
import com.example.wastetimer.data.model.SessionUiModel
import com.example.wastetimer.data.model.HistoryUiState

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    var deleteItem by remember {
        mutableStateOf<TrackingPeriodUiModel?>(null)
}

    if (state.isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()

        }

        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(
            items = state.periods,
            key = { it.periodId }
        ) { item ->

            HistoryCard(

                item = item,

                onExpand = {
                    viewModel.toggleExpanded(item.periodId)
                },

                onDelete = {
                    deleteItem = item
                }

            )

        }

    }

    deleteItem?.let {

        DeletePeriodDialog(

            onConfirm = {

                viewModel.deletePeriod(it.periodId)

                deleteItem = null

            },

            onDismiss = {

                deleteItem = null

            }

        )

    }

}
