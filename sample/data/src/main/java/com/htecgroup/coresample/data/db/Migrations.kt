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

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.data.db.entities.UserEntity

object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE ${PostEntity.TABLE_NAME} ADD COLUMN ${PostEntity.COLUMN_USER_ID} INTEGER")
            db.execSQL(
                """
                   CREATE TABLE IF NOT EXISTS ${UserEntity.TABLE_NAME}(
                   ${UserEntity.COLUMN_ID} INTEGER PRIMARY KEY NOT NULL,
                   ${UserEntity.COLUMN_NAME} TEXT NOT NULL,
                   ${UserEntity.COLUMN_USERNAME} TEXT NOT NULL,
                   ${UserEntity.COLUMN_EMAIL} TEXT NOT NULL,
                   ${UserEntity.COLUMN_PHONE} TEXT NOT NULL
                   ) 
                """
            )
        }
    }
}
