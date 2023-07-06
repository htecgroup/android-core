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

package com.htecgroup.coresample.data

import com.htecgroup.coresample.data.db.DatabaseModule
import com.htecgroup.coresample.data.network.NetworkModule
import com.htecgroup.coresample.data.repositories.PostRepositoryImpl
import com.htecgroup.coresample.data.repositories.UserRepositoryImpl
import com.htecgroup.coresample.domain.post.PostRepository
import com.htecgroup.coresample.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindPostRepository(postRepository: PostRepositoryImpl): PostRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
