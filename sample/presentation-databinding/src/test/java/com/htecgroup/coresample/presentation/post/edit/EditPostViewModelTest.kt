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
import com.htecgroup.core.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.post.usecase.UpdatePost
import com.htecgroup.coresample.domain.service.analytics.Analytics
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test for [EditPostViewModel]
 */
class EditPostViewModelTest : CoreViewModelTest() {

    private companion object {
        const val POST_ID = 1
        val POST = Post(POST_ID, "title", "description")
    }

    private val retrievePost: RetrievePost = mockk()
    private val updatePost: UpdatePost = mockk()
    private val resources: Resources = mockk()
    private val analytics: Analytics = mockk()
    private lateinit var viewModel: EditPostViewModel

    @BeforeEach
    fun setup() {
        viewModel = EditPostViewModel(retrievePost, updatePost, resources, analytics)
    }

    @Test
    fun initPost_HasData() {
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }

        viewModel.initPost(POST_ID)

        viewModel.titleLive.value?.shouldBeEqualTo(POST.title.uppercase())
        viewModel.bodyLive.value?.shouldBeEqualTo(POST.description)
        viewModel.getStatus().value?.hasData()?.shouldBeTrue()
        coVerify { retrievePost(any()) }
        confirmVerified(retrievePost)
    }

    @Test
    fun onSaveClick_NoData() {
        viewModel.onSaveClick()

        coVerify(exactly = 0) { updatePost(any()) }
        confirmVerified(updatePost)
    }

    @Test
    fun onSaveClick_HasData() {
        coEvery { updatePost(any()) } returns Result.success(Unit)
        coEvery { retrievePost(any()) } returns flow { emit(Result.success(POST)) }
        viewModel.initPost(POST_ID)

        viewModel.onSaveClick()

        viewModel.getAction().value?.code.shouldBe(EditPostViewModel.EditPostActionCode.FINISH)
        coVerify { updatePost(any()) }
        confirmVerified(updatePost)
    }
}
