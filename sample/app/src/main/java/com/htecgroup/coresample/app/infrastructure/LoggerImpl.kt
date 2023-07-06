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

import android.util.Log
import com.htecgroup.coresample.app.BuildConfig
import com.htecgroup.coresample.domain.service.Logger
import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {

    override fun d(tag: String, message: String?, error: Throwable?) {
        (message ?: error?.message ?: error?.localizedMessage ?: "")
            .takeIf { BuildConfig.DEBUG }
            ?.let {
                Log.d(tag, it, error)
            }
    }

    override fun e(tag: String, error: Throwable?, message: String?) {
        (message ?: error?.message ?: error?.localizedMessage ?: "")
            .takeIf { BuildConfig.DEBUG }
            ?.let {
                Log.e(tag, it, error)
            }
    }

    override fun i(tag: String, message: String?, error: Throwable?) {
        (message ?: error?.message ?: error?.localizedMessage ?: "")
            .takeIf { BuildConfig.DEBUG }
            ?.let {
                Log.i(tag, it, error)
            }
    }

    override fun v(tag: String, message: String?, error: Throwable?) {
        (message ?: error?.message ?: error?.localizedMessage ?: "")
            .takeIf { BuildConfig.DEBUG }
            ?.let {
                Log.v(tag, it, error)
            }
    }

    override fun w(tag: String, message: String?, error: Throwable?) {
        (message ?: error?.message ?: error?.localizedMessage ?: "")
            .takeIf { BuildConfig.DEBUG }
            ?.let {
                Log.w(tag, it, error)
            }
    }
}
