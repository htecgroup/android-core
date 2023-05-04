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

package com.htecgroup.core.presentation

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex

/**
 * Base [Application] setup.
 */
abstract class CoreApplication : Application() {

    companion object {
        var appContext: CoreApplication? = null
            private set
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (enableMultiDex()) {
            MultiDex.install(this)
        }
        initViewModelId()
    }

    /**
     * Provides databinding id for injecting viewmodel into layouts.
     */
    protected abstract fun initViewModelId()

    /**
     * Override this method in a subclass and return true to enable MultiDex.
     * Also, add 'multiDexEnabled true' into defaultConfig in build.gradle.kts file.
     */
    protected open fun enableMultiDex(): Boolean = false
}
