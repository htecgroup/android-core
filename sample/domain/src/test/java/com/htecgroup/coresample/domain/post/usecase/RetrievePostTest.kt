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

import com.htecgroup.core.test.CoreTest
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.PostRepository
import com.htecgroup.coresample.domain.user.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * Test for [RetrievePost]
 */
class RetrievePostTest : CoreTest() {

    private companion object {
        const val POST_ID = 1
        const val USER_ID = 2
    }

    private val postRepository: PostRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val retrievePost = RetrievePost(postRepository, userRepository)

    @Test
    fun execute() {
        val post = Post(POST_ID, userId = USER_ID)
        every { postRepository.getPost(any()) } returns flow { emit(Result.success(post)) }
        coEvery { userRepository.fetchUser(any()) } returns Result.success(Unit)

        runBlocking { retrievePost(POST_ID).collect() }

        verify { postRepository.getPost(POST_ID) }
        confirmVerified(postRepository)
        coVerify { userRepository.fetchUser(USER_ID) }
        confirmVerified(userRepository)
    }
}
