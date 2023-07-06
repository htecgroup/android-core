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

package com.htecgroup.coresample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htecgroup.coresample.data.db.dao.PostDao
import com.htecgroup.coresample.data.db.dao.UserDao
import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.data.db.entities.UserEntity

@Database(
    entities = [PostEntity::class, UserEntity::class],
    version = SampleDatabase.VERSION,
    exportSchema = false
)
abstract class SampleDatabase : RoomDatabase() {

    companion object {
        const val NAME = "SampleDatabase"
        const val VERSION = 2
    }

    abstract fun postDao(): PostDao

    abstract fun userDao(): UserDao
}
