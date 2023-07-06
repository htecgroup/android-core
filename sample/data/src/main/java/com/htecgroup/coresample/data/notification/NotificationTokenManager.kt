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

import com.google.firebase.messaging.FirebaseMessaging
import com.htecgroup.coresample.data.repositories.contracts.INotificationTokenStore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationTokenManager @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : INotificationTokenStore {

    override suspend fun getNotificationToken(): Result<String> = suspendCoroutine { coroutine ->
        firebaseMessaging.token.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                coroutine.resume(Result.success(it.result))
            } else {
                coroutine.resume(
                    Result.failure(it.exception ?: Exception("Failed to fetch Firebase token."))
                )
            }
        }
    }

    override suspend fun unregisterNotificationToken(): Result<Unit> =
        suspendCoroutine { coroutine ->
            firebaseMessaging.deleteToken().addOnCompleteListener {
                if (it.isSuccessful) {
                    coroutine.resume(Result.success(Unit))
                } else {
                    coroutine.resume(
                        Result.failure(
                            it.exception ?: Exception("Failed to unregister Firebase token.")
                        )
                    )
                }
            }
        }
}
