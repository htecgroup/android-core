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

package com.htecgroup.coresample.domain.post

import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<Result<List<Post>?>>

    suspend fun removePosts(): Result<Unit>

    fun getPost(postId: Int): Flow<Result<Post?>>

    suspend fun createPost(post: Post): Result<Unit>

    suspend fun updatePost(post: Post): Result<Unit>

    suspend fun deletePost(postId: Int): Result<Unit>

    suspend fun fetchPosts(): Result<Unit>

    suspend fun fetchPost(postId: Int): Result<Unit>

    suspend fun addPost(post: Post): Result<Unit>
}
