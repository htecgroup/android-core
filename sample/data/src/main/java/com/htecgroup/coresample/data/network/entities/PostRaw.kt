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

import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.domain.post.Post
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostRaw(

    @Json(name = "userId")
    val userId: Int? = null,

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "body")
    val body: String
)

fun PostRaw.toPost() = Post(id, title, body, userId ?: -1)

fun PostRaw.toPostEntity() = PostEntity(id = id, title = title, body = body, userId = userId)

fun Post.toPostRaw() = PostRaw(id = id, title = title, body = description)
