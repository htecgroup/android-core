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

package com.htecgroup.coresample.presentation.post.add

import android.content.res.Resources
import com.htecgroup.core.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.AddPost
import com.htecgroup.coresample.presentation.R
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class AddPostViewModelTest : CoreViewModelTest() {

    @MockK
    lateinit var addPost: AddPost

    @MockK
    lateinit var resources: Resources

    @InjectMockKs
    lateinit var sut: AddPostViewModel

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    internal fun `When ViewModel initialised, should set starting state`() {
        val initialState = Post()

        sut.uiState.data.shouldBeEqualTo(initialState)
    }

    @Test
    internal fun `Given post title and body filled, when save clicked, should add post`() {
        coEvery { addPost.invoke(any()) } returns Result.success(Unit)
        val expectedPost = Post(title = "Hello", description = "World")
        sut.postTitle.value = "Hello"
        sut.postBody.value = "World"

        sut.onSaveClick()

        sut.uiState.data.shouldBeEqualTo(null)
        coVerify { addPost.invoke(expectedPost) }
        confirmVerified(addPost)
    }

    @Test
    internal fun `Given post title and body empty, when save clicked, should set title and body from resources`() {
        coEvery { addPost.invoke(any()) } returns Result.success(Unit)
        val expectedPost = Post(title = "title", description = "body")

        every { resources.getString(R.string.no_title) } returns "title"
        every { resources.getString(R.string.no_body) } returns "body"

        sut.onSaveClick()

        sut.uiState.data.shouldBeEqualTo(null)
        coVerify { addPost.invoke(expectedPost) }
        confirmVerified(addPost)
    }

    @Test
    internal fun `Given post saved, when state cleared, should set state to idle with empty post`() {
        coEvery { addPost.invoke(any()) } returns Result.success(Unit)
        sut.postTitle.value = "t"
        sut.postBody.value = "b"
        sut.onSaveClick()
        sut.uiState.data.shouldBeNull()

        sut.clearState()

        sut.uiState.data.shouldBeEqualTo(Post())
    }
}
