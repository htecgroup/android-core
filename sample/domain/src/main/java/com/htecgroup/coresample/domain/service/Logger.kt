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

package com.htecgroup.coresample.domain.service

interface Logger {
    fun d(tag: String, message: String? = "", error: Throwable? = null)

    fun e(tag: String, error: Throwable? = null, message: String? = "")

    fun i(tag: String, message: String? = "", error: Throwable? = null)

    fun v(tag: String, message: String? = "", error: Throwable? = null)

    fun w(tag: String, message: String? = "", error: Throwable? = null)
}
