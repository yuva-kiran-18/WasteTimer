package com.example.wastetimer.tile

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.example.wastetimer.service.TimerForegroundService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WasteTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()

        val isRunning = TimerForegroundService.isRunning
        val intent = Intent(this, TimerForegroundService::class.java).apply {
            action = if (isRunning) TimerForegroundService.ACTION_STOP else TimerForegroundService.ACTION_START
        }

        startForegroundService(intent)
        updateTileState(!isRunning)
    }

    private fun updateTileState(isRunning: Boolean = TimerForegroundService.isRunning) {
        val tile = qsTile ?: return
        if (isRunning) {
            tile.state = Tile.STATE_ACTIVE
            tile.label = "Tracking Waste"
        } else {
            tile.state = Tile.STATE_INACTIVE
            tile.label = "Waste Timer"
        }
        tile.updateTile()
    }
}
