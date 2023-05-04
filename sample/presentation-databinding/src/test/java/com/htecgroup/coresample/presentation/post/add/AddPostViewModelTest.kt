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
import com.htecgroup.androidcore.test.CoreViewModelTest
import com.htecgroup.coresample.domain.post.usecase.AddPost
import io.mockk.coEvery
import io.mockk.mockk
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

/**
 * Test for [AddPostViewModel]
 */
class AddPostViewModelTest : CoreViewModelTest() {

    private val addPost: AddPost = mockk()
    private val resources: Resources = mockk()
    private val viewModel = AddPostViewModel(addPost, resources)

    @Test
    fun onSaveClick() {
        coEvery { addPost(any()) } returns Result.success(Unit)

        viewModel.onSaveClick()

        viewModel.getAction().value?.code.shouldBe(AddPostViewModel.AddPostActionCode.FINISH)
    }
}
