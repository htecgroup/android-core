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
import com.google.firebase.analytics.FirebaseAnalytics
import com.htecgroup.coresample.app.infrastructure.AnalyticsParamImpl
import com.htecgroup.coresample.app.infrastructure.FirebaseAnalyticsImpl
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.domain.service.analytics.AnalyticsParam
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Singleton
    @Provides
    fun provideAnalytics(firebaseAnalytics: FirebaseAnalytics): Analytics {
        return FirebaseAnalyticsImpl(firebaseAnalytics)
    }

    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideAnalyticsParam(): AnalyticsParam {
        return AnalyticsParamImpl()
    }
}
