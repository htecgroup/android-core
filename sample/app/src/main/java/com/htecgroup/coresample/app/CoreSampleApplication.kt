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

package com.htecgroup.coresample.app

import com.google.android.gms.ads.MobileAds
import com.htecgroup.core.presentation.CoreApplication
import com.htecgroup.coresample.domain.service.NotificationController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CoreSampleApplication : CoreApplication() {

    @Inject
    lateinit var notificationController: NotificationController

    init {
        app = this
    }

    override fun onCreate() {
        super.onCreate()
        /*if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }*/

        // Stetho.initializeWithDefaults(this);

        notificationController.createNotificationChannels()
        MobileAds.initialize(this)
    }

    override fun enableMultiDex() = true

    /**
     * #DataBindingSample
     * Uncomment the function [CoreSampleApplication.initViewModelId] to enable Data Binding sample
     */
    /*
    public override fun initViewModelId() {
        ViewModelIdProvider.viewModelId = BR.vm
    }
    */

    companion object {
        lateinit var app: CoreSampleApplication

        fun resources() = app.resources!!
    }
}
