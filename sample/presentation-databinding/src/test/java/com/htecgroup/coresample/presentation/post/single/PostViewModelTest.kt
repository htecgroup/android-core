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

package com.htecgroup.coresample.presentation.post.single

import com.htecgroup.core.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.DeletePost
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.service.Logger
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.domain.service.analytics.AnalyticsParam
import com.htecgroup.coresample.presentation.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test for [PostViewModel]
 */
@ExperimentalCoroutinesApi
class PostViewModelTest : CoreViewModelTest() {

    private companion object {
        const val POST_ID = 1
        val POST = Post(POST_ID)
    }

    private val retrievePost: RetrievePost = mockk()
    private val deletePost: DeletePost = mockk()
    private val analytics: Analytics = mockk(relaxed = true)
    private val analyticsParam: AnalyticsParam = mockk(relaxed = true)
    private val logger: Logger = mockk(relaxed = true)
    private lateinit var viewModel: PostViewModel
    private val excption: Throwable = mockk()

    @BeforeEach
    fun setup() {
        viewModel = PostViewModel(retrievePost, deletePost, analytics, analyticsParam)
        viewModel.logger = logger
    }

    @Test
    fun init() {
        viewModel.postLive.value.shouldBeNull()
        viewModel.getStatus().value?.isLoading()?.shouldBeTrue()
    }

    @Test
    fun initPost_HasData() {
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }

        viewModel.initPost(POST_ID)

        viewModel.getStatus().value?.hasData()?.shouldBeTrue()
        coVerify { retrievePost(any()) }
        confirmVerified(retrievePost)
    }

    @Test
    fun initPost_Error() {
        coEvery { retrievePost(any()) } returns flow {
            emit(
                Result.failure(
                    IllegalArgumentException()
                )
            )
        }

        viewModel.initPost(POST_ID)

        viewModel.getStatus().value?.isError()?.shouldBeTrue()
        coVerify { retrievePost(any()) }
        confirmVerified(retrievePost)
        verify { logger.e(any(), any()) }
        confirmVerified(logger)
    }

    @Test
    fun onEditPostClick() {
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }
        viewModel.initPost(POST_ID)

        viewModel.onEditPostClick()

        viewModel.getAction().value.let {
            it.shouldNotBeNull()
            it.code.shouldBe(PostViewModel.PostActionCode.CLICK_EDIT_POST)
            it.message.shouldBeEqualTo(R.string.edit_post)
        }
    }

    @Test
    fun onDeletePostClick_Success() {
        coEvery { deletePost(any()) } returns Result.success(Unit)
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }
        viewModel.initPost(POST_ID)

        viewModel.onDeletePostClick()

        coVerifyAll {
            deletePost(any())
            analytics.logEvent(any())
        }
        confirmVerified(deletePost)
        confirmVerified(analytics)
    }

    @Test
    fun onDeletePostClick_Error() {
        coEvery { deletePost(any()) } returns Result.failure(excption)
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }
        viewModel.initPost(POST_ID)

        viewModel.onDeletePostClick()

        coVerify { deletePost(any()) }
        verify(exactly = 0) { analytics.logEvent(any()) }
        confirmVerified(deletePost)
        confirmVerified(analytics)
    }

    @Test
    fun onDeletePostClick_NoData() {
        viewModel.onDeletePostClick()

        coVerify(exactly = 0) { deletePost(any()) }
        verify(exactly = 0) { analytics.logEvent(any()) }
    }
}
