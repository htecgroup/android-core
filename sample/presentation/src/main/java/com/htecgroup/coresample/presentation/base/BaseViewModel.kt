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

package com.htecgroup.coresample.presentation.base

import com.htecgroup.core.domain.extension.TAG
import com.htecgroup.core.domain.extension.collect
import com.htecgroup.core.domain.extension.collectResult
import com.htecgroup.core.presentation.model.DataUiState.Error
import com.htecgroup.core.presentation.model.DataUiState.Idle
import com.htecgroup.core.presentation.viewmodel.CoreViewModel
import com.htecgroup.coresample.domain.service.Logger
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class BaseViewModel<T : Any> : CoreViewModel<T>() {

    @Inject
    lateinit var logger: Logger

    suspend fun Result<T>.collectToUiState(
        onSuccess: (T?) -> Unit = { uiState = Idle(it) },
        onError: (Throwable?) -> Unit = {
            logger.e(TAG, it)
            uiState = Error(errorMessage = it?.message)
        }
    ) {
        collect(onSuccess = onSuccess, onError = onError)
    }

    suspend fun Flow<Result<T?>>.collectToUiState(
        onSuccess: (T?) -> Unit = { uiState = Idle(it) },
        onError: (Throwable?) -> Unit = {
            logger.e(TAG, it)
            uiState = Error(errorMessage = it?.message)
        }
    ) {
        collectResult(onSuccess, onError)
    }
}
