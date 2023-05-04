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

import com.htecgroup.core.presentation.model.DataUiState.Error
import com.htecgroup.core.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.FetchPosts
import com.htecgroup.coresample.domain.post.usecase.RetrievePosts
import com.htecgroup.coresample.presentation.post.toPostView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PostsViewModelTest : CoreViewModelTest() {
    @MockK
    lateinit var retrievePosts: RetrievePosts

    @MockK
    lateinit var fetchPosts: FetchPosts

    lateinit var sut: PostsViewModel

    private val post = Post(id = 1, title = "t")

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        coEvery { retrievePosts.invoke() } returns
            flowOf(Result.success(listOf(post)))
        sut = PostsViewModel(retrievePosts, fetchPosts)
    }

    @Test
    internal fun `When viewmodel initialized, should subscribe to posts flow`() {
        sut.uiState.data.shouldBeEqualTo(listOf(post.toPostView()))
    }

    @Test
    internal fun `Given fetch posts success, when refresh posts called, should fetch posts`() {
        coEvery { fetchPosts.invoke() } returns Result.success(Unit)

        sut.refreshPosts()

        coVerify { fetchPosts.invoke() }
        confirmVerified(fetchPosts)
    }

    @Test
    internal fun `Given fetch posts error, when refresh posts called, should set error state`() {
        coEvery { fetchPosts.invoke() } returns Result.failure(RuntimeException("Agh!"))
        sut.logger = mockk(relaxed = true)

        sut.refreshPosts()

        sut.uiState.shouldBeInstanceOf(Error::class)
    }
}
