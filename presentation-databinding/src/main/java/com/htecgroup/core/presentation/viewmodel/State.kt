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

package com.htecgroup.core.presentation.viewmodel

import androidx.annotation.IntDef

/**
 * [State] defines most common UI related states.
 */
@kotlin.annotation.Retention(value = AnnotationRetention.RUNTIME)
@IntDef(State.IDLE, State.LOADING, State.NO_DATA, State.DATA, State.NO_INTERNET, State.ERROR)
annotation class State {
    companion object {
        const val IDLE = 0
        const val LOADING = 1
        const val NO_DATA = 2
        const val DATA = 3
        const val NO_INTERNET = 4
        const val ERROR = 5
    }
}
