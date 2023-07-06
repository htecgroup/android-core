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

package com.htecgroup.coresample.data.repositories

import com.htecgroup.androidcore.data.CoreRepository
import com.htecgroup.androidcore.domain.extension.applyIfSome
import com.htecgroup.androidcore.domain.extension.mapWrapListResult
import com.htecgroup.androidcore.domain.extension.mapWrapResult
import com.htecgroup.androidcore.domain.extension.toUnitResult
import com.htecgroup.coresample.data.db.dao.UserDao
import com.htecgroup.coresample.data.db.entities.toUser
import com.htecgroup.coresample.data.network.api.UserApi
import com.htecgroup.coresample.data.network.entities.toUserEntity
import com.htecgroup.coresample.domain.user.User
import com.htecgroup.coresample.domain.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl
@Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi
) : CoreRepository(), UserRepository {

    override fun getUsers(): Flow<Result<List<User>>> =
        userDao.getAll()
            .mapWrapListResult { it.toUser() }

    override fun getUser(userId: Int): Flow<Result<User>> =
        userDao.get(userId)
            .mapWrapResult { it.toUser() }

    override suspend fun fetchUsers(): Result<Unit> =
        safeApiCall { userApi.getUsers() }
            .map { users -> users.map { it.toUserEntity() } }
            .applyIfSome { safeDbCall { userDao.updateUsers(it) } }
            .toUnitResult()

    override suspend fun fetchUser(userId: Int): Result<Unit> =
        safeApiCall { userApi.getUser(userId) }
            .map { it.toUserEntity() }
            .applyIfSome { safeDbCall { userDao.insert(it) } }
            .toUnitResult()

    override suspend fun removeUsers(): Result<Unit> =
        safeDbCall { userDao.deleteAll() }
}
