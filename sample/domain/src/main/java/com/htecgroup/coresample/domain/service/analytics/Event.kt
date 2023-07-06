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

package com.htecgroup.coresample.domain.service.analytics

import android.os.Bundle
import androidx.annotation.Size

class Event private constructor(val name: String, val params: Bundle) {

    class Builder(@param:Size(min = 1L, max = 40L) private val eventName: String) {

        private val params: Bundle = Bundle()

        fun setStringParam(key: String, value: String) = apply { params.putString(key, value) }

        fun setIntegerParam(key: String, value: Int) = apply { params.putInt(key, value) }

        fun setBooleanParam(key: String, value: Boolean) = apply { params.putBoolean(key, value) }

        fun setDoubleParam(key: String, value: Double) = apply { params.putDouble(key, value) }

        fun setFloatParam(key: String, value: Float) = apply { params.putFloat(key, value) }

        fun setLongParam(key: String, value: Long) = apply { params.putLong(key, value) }

        fun build() = Event(eventName, params)
    }
}
