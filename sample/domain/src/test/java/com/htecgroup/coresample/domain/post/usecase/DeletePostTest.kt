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

package com.htecgroup.coresample.domain.post.usecase

import com.htecgroup.androidcore.test.CoreTest
import com.htecgroup.coresample.domain.post.PostRepository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * Test for [DeletePost]
 */
class DeletePostTest : CoreTest() {

    private companion object {
        const val POST_ID = 1
    }

    private val postRepository: PostRepository = mockk(relaxed = true)
    private val deletePost = DeletePost(postRepository)

    @Test
    fun execute() {
        runBlocking { deletePost(POST_ID) }

        coVerify { postRepository.deletePost(POST_ID) }
        confirmVerified(postRepository)
    }
}
