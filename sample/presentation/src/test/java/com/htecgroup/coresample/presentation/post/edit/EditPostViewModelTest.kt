@file:Suppress("MaxLineLength")

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

package com.htecgroup.coresample.presentation.post.edit

import android.content.res.Resources
import com.htecgroup.androidcore.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.post.usecase.UpdatePost
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.toPostView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class EditPostViewModelTest : CoreViewModelTest() {

    @MockK
    lateinit var retrievePost: RetrievePost

    @MockK
    lateinit var updatePost: UpdatePost

    @MockK
    lateinit var resources: Resources

    @MockK
    lateinit var destination: EditPostDestination

    lateinit var sut: EditPostViewModel

    val postEntry = Post(id = 1, title = "T", description = "D")

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        coEvery { retrievePost.invoke(1) } returns flowOf(Result.success(postEntry))
        every { destination.id } returns 1
        sut = EditPostViewModel(retrievePost, updatePost, resources, destination)
    }

    @Test
    internal fun `Given existing post, when initialized, should set data from retrieved post`() {
        sut.uiState.data.shouldBeEqualTo(postEntry.toPostView())
        sut.postTitle.value.shouldBeEqualTo(postEntry.title)
        sut.postBody.value.shouldBeEqualTo(postEntry.description)
    }

    @Test
    internal fun `Given existing post and initialized, when changes made and save clicked, should update data`() {
        val expectedUiState = postEntry
            .toPostView()
            .copy(title = "Tt", description = "Dd", changesSaved = true)
        coEvery { updatePost.invoke(any()) } returns Result.success(Unit)

        sut.postTitle.value = "Tt"
        sut.postBody.value = "Dd"
        sut.onSaveClick()

        sut.uiState.data.shouldBeEqualTo(expectedUiState)
        sut.postTitle.value.shouldBeEqualTo(expectedUiState.title)
        sut.postBody.value.shouldBeEqualTo(expectedUiState.description)
    }

    @Test
    internal fun `Given existing post and initialized, when title and description cleared and save clicked, should update post and set no title and no description`() {
        every { resources.getString(R.string.no_title) } returns "No title"
        every { resources.getString(R.string.no_description) } returns "No description"
        val expectedUiState = postEntry
            .toPostView()
            .copy(title = "No title", description = "No description", changesSaved = true)
        coEvery { updatePost.invoke(any()) } returns Result.success(Unit)

        sut.postTitle.value = ""
        sut.postBody.value = ""
        sut.onSaveClick()

        sut.uiState.data.shouldBeEqualTo(expectedUiState)
        sut.postTitle.value.shouldBeEmpty()
        sut.postBody.value.shouldBeEmpty()
    }
}
