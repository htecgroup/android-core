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
import com.htecgroup.coresample.data.db.dao.PostDao
import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.data.db.entities.PostWithUserEntity
import com.htecgroup.coresample.data.db.entities.UserEntity
import com.htecgroup.coresample.data.network.api.PostApi
import com.htecgroup.coresample.data.network.entities.PostRaw
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.PostRepository
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
 * Test for [PostRepository]
 */
class PostRepositoryTest : CoreTest() {

    private companion object {
        const val POST_ID = 1
        val POST = Post()
        val POST_RAW = PostRaw(id = POST_ID, title = "title", body = "body")

        val POST_ENTITY = PostEntity(0)
        val USER_ENTITY = UserEntity(0, "", "", "", "")
    }

    private val postDao: PostDao = mockk()
    private val postApi: PostApi = mockk()
    private val repo = PostRepositoryImpl(postDao, postApi)

    @Nested
    inner class GetPosts {
        @Test
        fun success() {
            coEvery { postDao.getAll() } returns flow { emit(emptyList<PostEntity>()) }

            val result = runBlocking { repo.getPosts().first() }

            assertTrue { result.isSuccess }
            coVerify { postDao.getAll() }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postDao.getAll() } returns flow { throw IllegalArgumentException() }

            val result = runBlocking { repo.getPosts().first() }

            assertTrue { result.isFailure }
            coVerify { postDao.getAll() }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class RemovePosts {
        @Test
        fun success() {
            coEvery { postDao.deleteAll() } returns 1

            val result = runBlocking { repo.removePosts() }

            assertTrue { result.isSuccess }
            coVerify { postDao.deleteAll() }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postDao.deleteAll() } throws Exception()

            val result = runBlocking { repo.removePosts() }

            assertTrue { result.isFailure }
            coVerify { postDao.deleteAll() }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class GetPost {

        @Test
        fun success() {
            coEvery { postDao.get(any()) } returns flow {
                emit(
                    PostWithUserEntity(
                        POST_ENTITY,
                        USER_ENTITY
                    )
                )
            }

            val result = runBlocking { repo.getPost(POST_ID).first() }

            assertTrue { result.isSuccess }
            coVerify { postDao.get(any()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postDao.get(any()) } returns flow { throw IllegalArgumentException() }

            val result = runBlocking { repo.getPost(POST_ID).first() }

            assertTrue { result.isFailure }
            coVerify { postDao.get(any()) }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class CreatePost {
        @Test
        fun success() {
            coEvery { postDao.insert(any<PostEntity>()) } returns 1

            val result = runBlocking { repo.createPost(POST) }

            assertTrue { result.isSuccess }
            coVerify { postDao.insert(any<PostEntity>()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postDao.insert(any<PostEntity>()) } throws Exception()

            val result = runBlocking { repo.createPost(POST) }

            assertTrue { result.isFailure }
            coVerify { postDao.insert(any<PostEntity>()) }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class UpdatePost {
        @Test
        fun success() {
            coEvery { postDao.update(any<PostEntity>()) } returns 1

            val result = runBlocking { repo.updatePost(POST) }

            assertTrue { result.isSuccess }
            coVerify { postDao.update(any<PostEntity>()) }
            confirmVerified(postDao)
        }

        @Test
        fun updatePost_Error() {
            coEvery { postDao.update(any<PostEntity>()) } throws Exception()

            val result = runBlocking { repo.updatePost(POST) }

            assertTrue { result.isFailure }
            coVerify { postDao.update(any<PostEntity>()) }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class DeletePost {
        @Test
        fun success() {
            coEvery { postDao.delete(any<PostEntity>()) } returns 1

            val result = runBlocking { repo.deletePost(POST_ID) }

            assertTrue { result.isSuccess }
            coVerify { postDao.delete(any<PostEntity>()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postDao.delete(any<PostEntity>()) } throws Exception()

            val result = runBlocking { repo.deletePost(POST_ID) }

            assertTrue { result.isFailure }
            coVerify { postDao.delete(any<PostEntity>()) }
            confirmVerified(postDao)
        }
    }

    @Nested
    inner class FetchPosts {
        @Test
        fun success() {
            coEvery { postApi.getPosts() } returns Response.success(listOf())
            coEvery { postDao.updatePosts(any()) } returns Unit

            val result = runBlocking { repo.fetchPosts() }

            assertTrue { result.isSuccess }
            coVerify { postApi.getPosts() }
            confirmVerified(postApi)
            coVerify { postDao.updatePosts(any()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postApi.getPosts() } throws Exception()

            val result = runBlocking { repo.fetchPosts() }

            assertTrue { result.isFailure }
            coVerify { postApi.getPosts() }
            confirmVerified(postApi)
        }
    }

    @Nested
    inner class FetchPost {
        @Test
        fun success() {
            coEvery { postApi.getPost(any()) } returns Response.success(POST_RAW)
            coEvery { postDao.insert(any<PostEntity>()) } returns 1

            val result = runBlocking { repo.fetchPost(POST_ID) }

            assertTrue { result.isSuccess }
            coVerify { postApi.getPost(any()) }
            confirmVerified(postApi)
            coVerify { postDao.insert(any<PostEntity>()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postApi.getPost(any()) } throws Exception()

            val result = runBlocking { repo.fetchPost(POST_ID) }

            assertTrue { result.isFailure }
            coVerify { postApi.getPost(any()) }
            confirmVerified(postApi)
        }
    }

    @Nested
    inner class AddPost {
        @Test
        fun success() {
            coEvery { postApi.addPost(any()) } returns Response.success(POST_RAW)
            coEvery { postDao.insert(any<PostEntity>()) } returns 1

            val result = runBlocking { repo.addPost(POST) }

            assertTrue { result.isSuccess }
            coVerify { postApi.addPost(any()) }
            confirmVerified(postApi)
            coVerify { postDao.insert(any<PostEntity>()) }
            confirmVerified(postDao)
        }

        @Test
        fun error() {
            coEvery { postApi.addPost(any()) } throws Exception()

            val result = runBlocking { repo.addPost(POST) }

            assertTrue { result.isFailure }
            coVerify { postApi.addPost(any()) }
            confirmVerified(postApi)
        }
    }
}
