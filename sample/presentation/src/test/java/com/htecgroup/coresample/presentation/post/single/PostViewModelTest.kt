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

import androidx.lifecycle.SavedStateHandle
import com.htecgroup.androidcore.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.DeletePost
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PostViewModelTest : CoreViewModelTest() {

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    @MockK
    lateinit var retrievePost: RetrievePost

    @MockK
    lateinit var deletePost: DeletePost

    lateinit var sut: PostViewModel

    val defaultPost = Post(id = 1, title = "t", description = "d")

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<Int>(any()) } returns 1
        coEvery { retrievePost.invoke(1) } returns flowOf(
            Result.success(defaultPost)
        )
        sut = PostViewModel(savedStateHandle, retrievePost, deletePost)
    }

    @Test
    internal fun `When edit post clicked, should set shouldEdit in state`() {
        sut.onEditPostClick()

        sut.uiState.data!!.shouldEdit.shouldBeTrue()
    }

    @Test
    internal fun `Given delete post success, when deleting a post, should set delete flag`() {
        coEvery { deletePost.invoke(1) } returns Result.success(Unit)

        sut.onDeletePostClick()

        sut.uiState.data!!.shouldDelete.shouldBeTrue()
    }

    @Test
    internal fun `Given post delete fails, when deleting a post, should log error`() {
        coEvery { deletePost.invoke(1) } returns Result.failure(RuntimeException("Agh!"))
        sut.logger = mockk(relaxed = true)

        sut.onDeletePostClick()

        verify { sut.logger.e(any(), any()) }
        confirmVerified(sut.logger)
    }

    @Test
    internal fun `Given should edit set, when clear state called, should clear state`() {
        sut.onEditPostClick()
        sut.uiState.data!!.shouldEdit.shouldBeTrue()

        sut.clearState()

        sut.uiState.data!!.shouldEdit.shouldBeFalse()
    }
}
