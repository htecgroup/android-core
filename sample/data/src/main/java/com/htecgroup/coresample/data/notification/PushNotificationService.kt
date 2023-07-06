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

package com.htecgroup.coresample.data.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.htecgroup.androidcore.domain.extension.TAG
import com.htecgroup.coresample.domain.service.Logger
import com.htecgroup.coresample.domain.service.NotificationController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationController: NotificationController

    @Inject
    lateinit var logger: Logger

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            if (it.title.isNullOrEmpty() && it.body.isNullOrEmpty()) return

            logger.d(TAG, "Message Notification Title: ${it.title}")
            logger.d(TAG, "Message Notification Body: ${it.body}")
            notificationController.showNotification(it.title!!, it.body!!)
        }

        message.data.let {
            logger.d(TAG, "Message Data Title: ${it["title"]}")
            logger.d(TAG, "Message Data Body: ${it["body"]}")
        }
    }

    override fun onNewToken(newToken: String) {
        logger.d(TAG, "Refreshed token: $newToken")
        // Persist token to third-party servers.
    }
}
