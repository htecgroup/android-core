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

package com.htecgroup.androidcore.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.htecgroup.androidcore.presentation.model.DataUiState
import com.htecgroup.androidcore.presentation.model.DataUiState.Idle

/**
 * Base [ViewModel] class, where [T] is enum representing actions.
 */
abstract class CoreViewModel<T : Any> : ViewModel() {

    var uiState by mutableStateOf<DataUiState<T>>(Idle())
        protected set

    open fun clearState() {
        uiState = Idle()
    }
}
