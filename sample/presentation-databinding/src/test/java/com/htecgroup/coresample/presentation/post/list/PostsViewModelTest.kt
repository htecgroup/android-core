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

package com.htecgroup.coresample.presentation.post.list

import com.htecgroup.androidcore.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.DeletePosts
import com.htecgroup.coresample.domain.post.usecase.FetchPosts
import com.htecgroup.coresample.domain.post.usecase.RetrievePosts
import com.htecgroup.coresample.domain.service.Logger
import com.htecgroup.coresample.domain.user.DeleteUsers
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test for [PostsViewModel]
 */
class PostsViewModelTest : CoreViewModelTest() {

    private val retrievePosts: RetrievePosts = mockk()
    private val deletePosts: DeletePosts = mockk()
    private val fetchPosts: FetchPosts = mockk()
    private val deleteUsers: DeleteUsers = mockk()
    private val logger: Logger = mockk(relaxed = true)
    private lateinit var viewModel: PostsViewModel
    private val exception: Throwable = mockk()

    @BeforeEach
    fun setup() {
        coEvery { retrievePosts() } returns flow { Result.success<List<Post>>(listOf()) }

        viewModel = PostsViewModel(retrievePosts, deletePosts, fetchPosts, deleteUsers)
        viewModel.logger = logger
    }

    @Test
    fun getPosts_NoData_Success() {
        coEvery { retrievePosts() } returns flow { emit(Result.success(listOf())) }

        viewModel.getPosts()

        viewModel.getStatus().value?.noData()?.shouldBeTrue()
        coVerify { retrievePosts() }
        confirmVerified(retrievePosts)
    }

    @Test
    fun getPosts_HasData_Success() {
        coEvery { retrievePosts() } returns flow { emit(Result.success(listOf(Post()))) }

        viewModel.getPosts()

        viewModel.getStatus().value?.hasData()?.shouldBeTrue()
        viewModel.postListLive.value?.size?.shouldBeEqualTo(1)
        coVerify { retrievePosts() }
        confirmVerified(retrievePosts)
    }

    @Test
    fun getPosts_Error() {
        coEvery { retrievePosts() } returns flow { emit(Result.failure(exception)) }

        viewModel.getPosts()

        viewModel.getStatus().value?.isError()?.shouldBeTrue()
        coVerify { retrievePosts() }
        confirmVerified(retrievePosts)
        verify { logger.e(any(), any()) }
        confirmVerified(logger)
    }

    @Test
    fun deleteAllPosts_Success() {
        coEvery { deletePosts() } returns Result.success(Unit)
        coEvery { deleteUsers() } returns Result.success(Unit)

        viewModel.deleteAllPosts()

        viewModel.postListLive.value?.size?.shouldBeEqualTo(0)
        coVerify { deletePosts() }
        confirmVerified(deletePosts)
        coVerify { deleteUsers() }
        confirmVerified(deleteUsers)
    }

    @Test
    fun refreshPosts_Success() {
        coEvery { fetchPosts() } returns Result.success(Unit)

        val hasData = viewModel.getStatus().value?.hasData()
        val noData = viewModel.getStatus().value?.noData()

        viewModel.refreshPosts()

        viewModel.getStatus().value?.let {
            it.hasData().shouldBeEqualTo(hasData)
            it.noData().shouldBeEqualTo(noData)
            it.isLoading().shouldBeFalse()
        }
        viewModel.postListLive.value?.size?.shouldBeEqualTo(1)
        coVerify { fetchPosts() }
        confirmVerified(fetchPosts)
        verify { logger.i(any(), any()) }
        confirmVerified(logger)
    }

    @Test
    fun refreshPosts_Error() {
        coEvery { fetchPosts() } returns Result.failure(exception)

        viewModel.refreshPosts()

        viewModel.getStatus().value?.isError()?.shouldBeTrue()
        coVerify { fetchPosts() }
        confirmVerified(fetchPosts)
        verify { logger.e(any(), any()) }
        confirmVerified(logger)
    }
}
