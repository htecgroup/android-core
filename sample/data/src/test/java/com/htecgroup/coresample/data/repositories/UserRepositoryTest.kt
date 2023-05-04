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

import com.htecgroup.core.test.CoreTest
import com.htecgroup.coresample.data.db.dao.UserDao
import com.htecgroup.coresample.data.db.entities.UserEntity
import com.htecgroup.coresample.data.network.api.UserApi
import com.htecgroup.coresample.data.network.entities.UserRaw
import com.htecgroup.coresample.domain.user.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response

/**
 * Test for [UserRepository]
 */
class UserRepositoryTest : CoreTest() {

    private companion object {
        const val USER_ID = 1
    }

    private val userDao: UserDao = mockk()
    private val userApi: UserApi = mockk()
    private val userRepository = UserRepositoryImpl(userDao, userApi)

    @Nested
    inner class GetUsers {
        @Test
        fun success() {
            coEvery { userDao.getAll() } returns flow { emit(emptyList<UserEntity>()) }

            val result = runBlocking { userRepository.getUsers().first() }

            assertTrue { result.isSuccess }
            coVerify { userDao.getAll() }
            confirmVerified(userDao)
        }

        @Test
        fun error() {
            coEvery { userDao.getAll() } returns flow { throw IllegalArgumentException() }

            val result = runBlocking { userRepository.getUsers().first() }

            assertTrue { result.isFailure }
            coVerify { userDao.getAll() }
            confirmVerified(userDao)
        }
    }

    @Nested
    inner class GetUser {
        @Test
        fun success() {
            coEvery { userDao.get(any()) } returns flow {
                emit(
                    UserEntity(
                        USER_ID,
                        "",
                        "",
                        "",
                        ""
                    )
                )
            }

            val result = runBlocking { userRepository.getUser(USER_ID).first() }

            assertTrue { result.isSuccess }
            coVerify { userDao.get(USER_ID) }
            confirmVerified(userDao)
        }

        @Test
        fun error() {
            coEvery { userDao.get(any()) } returns flow { throw IllegalArgumentException() }

            val result = runBlocking { userRepository.getUser(USER_ID).first() }

            assertTrue { result.isFailure }
            coVerify { userDao.get(USER_ID) }
            confirmVerified(userDao)
        }
    }

    @Nested
    inner class FetchUsers {
        @Test
        fun success() {
            coEvery { userApi.getUsers() } returns Response.success(listOf())
            coEvery { userDao.updateUsers(any()) } returns Unit

            val result = runBlocking { userRepository.fetchUsers() }

            assertTrue { result.isSuccess }
            coVerify { userApi.getUsers() }
            confirmVerified(userApi)
            coVerify { userDao.updateUsers(any()) }
            confirmVerified(userDao)
        }

        @Test
        fun error_Get() {
            coEvery { userApi.getUsers() } throws IllegalArgumentException()

            val result = runBlocking { userRepository.fetchUsers() }

            assertTrue { result.isFailure }
            coVerify { userApi.getUsers() }
            confirmVerified(userApi)
            confirmVerified(userDao)
        }

        @Test
        fun error_Update() {
            coEvery { userApi.getUsers() } returns Response.success(listOf())
            coEvery { userDao.updateUsers(any()) } throws IllegalArgumentException()

            val result = runBlocking { userRepository.fetchUsers() }

            assertTrue { result.isFailure }
            coVerify { userApi.getUsers() }
            confirmVerified(userApi)
            coVerify { userDao.updateUsers(any()) }
            confirmVerified(userDao)
        }
    }

    @Nested
    inner class FetchUser {
        @Test
        fun success() {
            coEvery { userApi.getUser(any()) } returns Response.success(
                UserRaw(
                    USER_ID,
                    null,
                    null,
                    null,
                    null
                )
            )
            coEvery { userDao.insert(any<UserEntity>()) } returns 1

            val result = runBlocking { userRepository.fetchUser(USER_ID) }

            assertTrue { result.isSuccess }
            coVerify { userApi.getUser(USER_ID) }
            confirmVerified(userApi)
            coVerify { userDao.insert(any<UserEntity>()) }
            confirmVerified(userDao)
        }

        @Test
        fun error_Get() {
            coEvery { userApi.getUser(any()) } throws IllegalArgumentException()

            val result = runBlocking { userRepository.fetchUser(USER_ID) }

            assertTrue { result.isFailure }
            coVerify { userApi.getUser(USER_ID) }
            confirmVerified(userApi)
            confirmVerified(userDao)
        }

        @Test
        fun error_Insert() {
            coEvery { userApi.getUser(any()) } returns Response.success(
                UserRaw(
                    USER_ID,
                    null,
                    null,
                    null,
                    null
                )
            )
            coEvery { userDao.insert(any<UserEntity>()) } throws IllegalArgumentException()

            val result = runBlocking { userRepository.fetchUser(USER_ID) }

            assertTrue { result.isFailure }
            coVerify { userApi.getUser(USER_ID) }
            confirmVerified(userApi)
            coVerify { userDao.insert(any<UserEntity>()) }
            confirmVerified(userDao)
        }
    }

    @Nested
    inner class RemoveUsers {
        @Test
        fun success() {
            coEvery { userDao.deleteAll() } returns 1

            val result = runBlocking { userRepository.removeUsers() }

            assertTrue { result.isSuccess }
            coVerify { userDao.deleteAll() }
            confirmVerified(userDao)
        }

        @Test
        fun error() {
            coEvery { userDao.deleteAll() } throws IllegalArgumentException()

            val result = runBlocking { userRepository.removeUsers() }

            assertTrue { result.isFailure }
            coVerify { userDao.deleteAll() }
            confirmVerified(userDao)
        }
    }
}
