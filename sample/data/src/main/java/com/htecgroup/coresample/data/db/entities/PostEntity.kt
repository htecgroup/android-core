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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.htecgroup.coresample.data.network.entities.PostRaw
import com.htecgroup.coresample.domain.post.Post
import java.util.*

@Entity(tableName = PostEntity.TABLE_NAME)
data class PostEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String = "",
    @ColumnInfo(name = COLUMN_BODY)
    val body: String = "",
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Int? = null
) : Comparable<PostEntity> {

    companion object {
        const val TABLE_NAME = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
        const val COLUMN_USER_ID = "user_id"
    }

    @Ignore
    override fun compareTo(other: PostEntity): Int {
        if (id == other.id && title == other.title && body == other.body) {
            return 0
        }
        return if (id < other.id) -1 else 1
    }
}

fun PostEntity.toPost() = Post(id, title, body, userId ?: -1)

fun PostEntity.toPostRaw() =
    PostRaw(id = id, title = title.uppercase(Locale.getDefault()), body = body)

fun Post.toPostEntity() = PostEntity(id, title, description, userId)
