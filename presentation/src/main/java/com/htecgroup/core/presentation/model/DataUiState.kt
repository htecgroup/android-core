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

package com.htecgroup.core.presentation.model

sealed class DataUiState<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val errorDescription: String? = null
) {
    fun toError(errorMessage: String?, errorDescription: String? = null) =
        Error(errorMessage, errorDescription, data)

    fun toLoading() = Loading(data)
    fun toIdle(newData: T? = null) = Idle(newData ?: data)
    fun toIdle(applyNewData: (T?) -> T?) = Idle(applyNewData(data))

    val isLoading get() = this is Loading

    class Idle<T>(data: T? = null) : DataUiState<T>(data = data)
    class Loading<T>(data: T? = null) : DataUiState<T>(data)
    class Error<T>(errorMessage: String?, errorDescription: String? = null, data: T? = null) :
        DataUiState<T>(
            data = data,
            errorMessage = errorMessage,
            errorDescription = errorDescription
        )
}
