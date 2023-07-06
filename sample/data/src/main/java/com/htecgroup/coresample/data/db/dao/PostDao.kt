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

package com.htecgroup.coresample.data.db.dao

import androidx.room.*
import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.data.db.entities.PostWithUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao : BaseDao<PostEntity> {

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME}")
    fun getAll(): Flow<List<PostEntity>>

    @Transaction
    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} WHERE ${PostEntity.COLUMN_ID} = :postId")
    fun get(postId: Long): Flow<PostWithUserEntity?>

    @Query("DELETE FROM ${PostEntity.TABLE_NAME}")
    suspend fun deleteAll(): Int

    @Transaction
    suspend fun updatePosts(posts: List<PostEntity>) {
        insertOrUpdate(posts)
        deleteExcept(posts.map { it.id })
    }

    @Transaction
    suspend fun insertOrUpdate(posts: List<PostEntity>) {
        insertOrIgnore(posts).also { insertResult ->
            posts
                .filterIndexed { index, _ ->
                    insertResult[index] == -1L
                }
                .takeIf { it.isNotEmpty() }
                ?.let {
                    update(it)
                }
        }
    }

    /**
     * Inserts list of posts to database.
     *
     * A method that returns the inserted rows ids will return -1 for rows that are not inserted
     * since this strategy will ignore the row if there is a conflict.
     *
     * @return List of results of insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(posts: List<PostEntity>): List<Long>

    /**
     * Deletes posts from database which ids are not in parameters list.
     */
    @Query("DELETE FROM ${PostEntity.TABLE_NAME} WHERE ${PostEntity.COLUMN_ID} NOT IN (:ids)")
    suspend fun deleteExcept(ids: List<Int>)
}
