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

package com.htecgroup.androidcore.data

import retrofit2.HttpException
import retrofit2.Response

/**
 * Base repository class.
 */
abstract class CoreRepository {

    /**
     * Suspend function wrapping db calls in a safe manner (using try/catch).
     *
     * Returns [Result] of type [DataT] after db call execution.
     */
    protected suspend inline fun <DataT : Any> safeDbCall(
        crossinline dbCall: suspend () -> DataT
    ): Result<DataT> =
        try {
            Result.success(dbCall())
        } catch (e: Exception) {
            Result.failure(e)
        }

    /**
     * Suspend function wrapping api calls in a safe manner (using try/catch).
     *
     * Returns [Result] of type [DataT] after api call execution.
     */
    protected suspend inline fun <DataT : Any> safeApiCall(
        crossinline apiCall: suspend () -> Response<DataT>
    ): Result<DataT> =
        try {
            val response = apiCall()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Result.success(responseBody)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
}
