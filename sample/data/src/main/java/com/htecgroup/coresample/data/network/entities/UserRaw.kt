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

package com.htecgroup.coresample.data.network.entities

import com.htecgroup.coresample.data.db.entities.UserEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserRaw(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "username")
    val username: String?,
    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "phone")
    val phone: String?
)

fun UserRaw.toUserEntity() =
    UserEntity(
        id = id,
        name = name ?: "",
        username = username ?: "",
        email = email ?: "",
        phone = phone ?: ""
    )
