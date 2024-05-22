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

package com.htecgroup.androidcore.domain.extension

/**
 * Applies [block] function on [DataT] and wraps the result [OutT] into [Result].
 */
suspend fun <OutT : Any, DataT : Any> Result<DataT>.flatMap(
    block: suspend (DataT?) -> Result<OutT>
): Result<OutT> {
    val exception = exceptionOrNull()
    return when {
        isSuccess -> block(getOrNull())
        isFailure && exception != null -> Result.failure(exception)
        else -> Result.failure(IllegalStateException("Result exception is null"))
    }
}

/**
 * Applies [block] function on [DataT] if it's non-null, and wraps the result [OutT] into [Result].
 * Returns success if [DataT] is null.
 */
suspend fun <OutT : Any, DataT : Any> Result<DataT?>.flatMapIfSome(
    block: suspend (DataT) -> Result<OutT?>
): Result<OutT?> {
    val data: DataT? = getOrNull()
    val exception = exceptionOrNull()
    return when {
        isSuccess && data != null -> block(data)
        isSuccess && data == null -> Result.success(null)
        isFailure && exception != null -> Result.failure(exception)
        else -> Result.failure(IllegalStateException("Result exception is null"))
    }
}

/**
 * Maps [Result]<[DataT]> to [Result]<[Unit]>, used when only success/failure
 * information is needed (generic type can be ignored).
 */
suspend fun <DataT> Result<DataT>.toUnitResult(): Result<Unit> {
    val exception = exceptionOrNull()
    return when {
        isSuccess -> Result.success(Unit)
        isFailure && exception != null -> Result.failure(exception)
        else -> Result.failure(IllegalStateException("Result exception is null"))
    }
}

/**
 * Collects/unwraps [DataT] from [Result] using success and error callbacks.
 */
suspend fun <DataT> Result<DataT?>.collect(
    onSuccess: (DataT?) -> Unit,
    onError: ((Throwable?) -> Unit)?
) {
    when {
        isSuccess -> onSuccess(getOrNull())
        isFailure -> onError?.invoke(exceptionOrNull())
    }
}
