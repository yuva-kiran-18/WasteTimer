package com.example.wastetimer.tile

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.example.wastetimer.service.TimerForegroundService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
            action = if (isRunning) "ACTION_STOP" else "ACTION_START"
        }
        startService(intent)
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
