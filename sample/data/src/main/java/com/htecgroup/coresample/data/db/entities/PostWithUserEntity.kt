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

package com.htecgroup.coresample.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.htecgroup.coresample.domain.post.Post

@Entity
class PostWithUserEntity(
    @Embedded
    val post: PostEntity?,
    @Relation(
        entity = UserEntity::class,
        entityColumn = UserEntity.COLUMN_ID,
        parentColumn = PostEntity.COLUMN_USER_ID
    )
    val user: UserEntity?
)

fun PostWithUserEntity.toPost(): Post? =
    post?.let {
        Post(
            id = it.id,
            title = it.title,
            description = it.body,
            userId = it.userId ?: -1,
            user = user?.toUser()
        )
    }
