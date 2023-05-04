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

import android.content.Context
import androidx.room.Room
import com.htecgroup.coresample.data.db.dao.PostDao
import com.htecgroup.coresample.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    internal fun providePostDao(database: SampleDatabase): PostDao = database.postDao()

    @Singleton
    @Provides
    internal fun provideUserDao(database: SampleDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    internal fun provideDatabase(@ApplicationContext context: Context): SampleDatabase =
        Room.databaseBuilder(context, SampleDatabase::class.java, SampleDatabase.NAME)
            .addMigrations(Migrations.MIGRATION_1_2)
            .build()

    @Singleton
    @InMemoryDatabase
    @Provides
    internal fun provideInMemoryDatabase(@ApplicationContext context: Context): SampleDatabase =
        Room.inMemoryDatabaseBuilder(context, SampleDatabase::class.java)
            .addMigrations(Migrations.MIGRATION_1_2)
            .build()

    @Qualifier
    @kotlin.annotation.Retention(value = AnnotationRetention.RUNTIME)
    annotation class InMemoryDatabase
}
