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

package com.htecgroup.coresample.app.infrastructure

import android.app.Activity
import com.google.firebase.analytics.FirebaseAnalytics
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.domain.service.analytics.Event

class FirebaseAnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

    override fun logEvent(event: Event) {
        firebaseAnalytics.logEvent(event.name, event.params)
    }

    override fun setUserProperty(key: String, value: String?) {
        firebaseAnalytics.setUserProperty(key, value)
    }

    override fun setCurrentScreen(
        activity: Activity,
        screenName: String,
        screenClassOverride: String
    ) {
        firebaseAnalytics.setCurrentScreen(activity, screenName, screenClassOverride)
    }
}
