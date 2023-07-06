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

package com.htecgroup.coresample.app.di

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.htecgroup.coresample.data.notification.NotificationTokenManager
import com.htecgroup.coresample.data.repositories.contracts.INotificationTokenStore
import com.htecgroup.coresample.domain.service.NotificationController
import com.htecgroup.coresample.presentation.utils.notification.NotificationControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Provides
    fun provideFirebaseMessaging() = FirebaseMessaging.getInstance()

    @Singleton
    @Provides
    fun provideNotificationTokenStore(firebaseMessaging: FirebaseMessaging): INotificationTokenStore =
        NotificationTokenManager(firebaseMessaging)

    @Singleton
    @Provides
    fun provideNotificationController(@ApplicationContext context: Context):
        NotificationController = NotificationControllerImpl(context)
}
