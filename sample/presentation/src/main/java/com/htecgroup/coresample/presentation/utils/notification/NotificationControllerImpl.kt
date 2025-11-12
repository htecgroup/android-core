/*
 * Copyright 2023 HTEC Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htecgroup.coresample.presentation.utils.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.htecgroup.coresample.domain.service.NotificationController
import com.htecgroup.coresample.presentation.MainActivity
import com.htecgroup.coresample.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val NOTIFICATION_ID = 12345

class NotificationControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationController {

    private val defaultChannelId = context.getString(R.string.default_notification_channel_id)

    override fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.default_notification_channel_name)
            context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(
                NotificationChannel(
                    defaultChannelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun showNotification(title: String, description: String, channelId: String?) {
        val cnlId = channelId ?: defaultChannelId
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, createNotification(title, description, cnlId))
    }

    private fun createNotification(
        title: String,
        description: String,
        channelId: String
    ): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(
                PendingIntent.getActivity(context, 0, intent, flag)
            )
            .setAutoCancel(true)
            .build()
    }
}
