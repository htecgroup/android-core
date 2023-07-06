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
import com.htecgroup.coresample.data.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_ID}=:id")
    fun get(id: Int): Flow<UserEntity>

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    suspend fun deleteAll(): Int

    @Transaction
    suspend fun updateUsers(user: List<UserEntity>) {
        insertOrUpdate(user)
        deleteExcept(user.map { it.id })
    }

    @Transaction
    suspend fun insertOrUpdate(users: List<UserEntity>) {
        insertOrIgnore(users).also { insertResult ->
            users
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
     * Inserts list of users to database.
     *
     * A method that returns the inserted rows ids will return -1 for rows that are not inserted
     * since this strategy will ignore the row if there is a conflict.
     *
     * @return List of results of insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(users: List<UserEntity>): List<Long>

    /**
     * Deletes users from database which ids are not in parameters list.
     */
    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_ID} NOT IN (:ids)")
    suspend fun deleteExcept(ids: List<Int>)
}
