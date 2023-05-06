package com.blaubalu.detoxrank.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import com.blaubalu.detoxrank.MainActivity
import com.blaubalu.detoxrank.ui.utils.Constants.CANCEL_REQUEST_CODE
import com.blaubalu.detoxrank.ui.utils.Constants.CLICK_REQUEST_CODE
import com.blaubalu.detoxrank.ui.utils.Constants.STOPWATCH_STATE

@ExperimentalAnimationApi
object ServiceHelper {

    private val flag =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE
        else
            0

    fun clickPendingIntent(context: Context): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(STOPWATCH_STATE, TimerState.Started.name)
        }
        return PendingIntent.getActivity(
            context, CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, TimerService::class.java).apply {
            putExtra(STOPWATCH_STATE, TimerState.Canceled.name)
        }
        return PendingIntent.getService(
            context, CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }

    fun triggerForegroundService(context: Context, action: String) {
        Intent(context, TimerService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}