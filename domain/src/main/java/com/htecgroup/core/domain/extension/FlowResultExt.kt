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

package com.htecgroup.core.domain.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Maps [DataT] using [mapper] function, and wraps the result [OutT] into [Flow]<[Result]>.
 */
fun <DataT, OutT> Flow<DataT>.mapWrapResult(
    mapper: suspend (DataT) -> OutT
): Flow<Result<OutT>> =
    this.map { Result.success(mapper(it)) }
        .catch { emit(Result.failure(it)) }

/**
 * Unwraps [DataT] from [Result], maps it using [mapper] function,
 * and wraps the result [OutT] into [Flow]<[Result]>.
 */
suspend fun <DataT, OutT> Flow<Result<DataT>>.mapUnwrapResult(
    mapper: suspend (DataT) -> OutT
): Flow<Result<OutT>> =
    this.map { result -> result.map { mapper(it) } }
        .catch { emit(Result.failure(it)) }

/**
 * Maps list of [DataT] using [mapper] function, and wraps the result
 * [List]<[OutT]> into [Flow]<[Result]>.
 */
fun <DataT, OutT> Flow<List<DataT>>.mapWrapListResult(
    mapper: suspend (DataT) -> OutT
): Flow<Result<List<OutT>>> =
    this.map { list -> Result.success(list.map { mapper(it) }) }
        .catch { emit(Result.failure(it)) }

/**
 * Unwraps [List]<[DataT]> from [Result], maps it using [mapper] function,
 * and wraps the result [List]<[OutT]> into [Flow]<[Result]>.
 */
suspend fun <DataT, OutT> Flow<Result<List<DataT>?>>.mapUnwrapListResult(
    mapper: suspend (DataT) -> OutT
): Flow<Result<List<OutT>?>> =
    this.map { result -> result.map { list -> list?.map { mapper(it) } } }
        .catch { emit(Result.failure(it)) }

/**
 * Collects/unwraps [Result] from [Flow] and delegates handling to the [collect] function.
 */
suspend fun <DataT> Flow<Result<DataT>>.collectResult(
    onSuccess: (DataT?) -> Unit,
    onError: ((Throwable?) -> Unit)? = null
) = this.catch { emit(Result.failure(it)) }.collect { it.collect(onSuccess, onError) }
