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

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<TypeT> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TypeT): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<TypeT>): LongArray

    @Update
    suspend fun update(item: TypeT): Int

    @Update
    suspend fun update(items: List<TypeT>): Int

    @Delete
    suspend fun delete(item: TypeT): Int

    @Delete
    suspend fun delete(items: List<TypeT>): Int
}
