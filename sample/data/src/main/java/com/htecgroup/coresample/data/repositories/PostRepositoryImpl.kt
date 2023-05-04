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

package com.htecgroup.coresample.data.repositories

import com.htecgroup.core.data.CoreRepository
import com.htecgroup.core.domain.extension.applyIfSome
import com.htecgroup.core.domain.extension.mapWrapListResult
import com.htecgroup.core.domain.extension.mapWrapResult
import com.htecgroup.core.domain.extension.toUnitResult
import com.htecgroup.coresample.data.db.dao.PostDao
import com.htecgroup.coresample.data.db.entities.PostEntity
import com.htecgroup.coresample.data.db.entities.toPost
import com.htecgroup.coresample.data.db.entities.toPostEntity
import com.htecgroup.coresample.data.network.api.PostApi
import com.htecgroup.coresample.data.network.entities.toPostEntity
import com.htecgroup.coresample.data.network.entities.toPostRaw
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val postApi: PostApi
) : CoreRepository(), PostRepository {

    override fun getPosts(): Flow<Result<List<Post>>> =
        postDao.getAll().mapWrapListResult { it.toPost() }

    override suspend fun removePosts(): Result<Unit> = safeDbCall { postDao.deleteAll() }

    override fun getPost(postId: Int): Flow<Result<Post?>> =
        postDao.get(postId.toLong()).mapWrapResult { it?.toPost() }

    override suspend fun createPost(post: Post): Result<Unit> =
        safeDbCall { postDao.insert(post.toPostEntity()) }

    override suspend fun updatePost(post: Post): Result<Unit> =
        safeDbCall { postDao.update(post.toPostEntity()) }

    override suspend fun deletePost(postId: Int): Result<Unit> =
        safeDbCall { postDao.delete(PostEntity(postId)) }

    override suspend fun fetchPosts(): Result<Unit> =
        safeApiCall {
            postApi.getPosts()
        }
            .map { posts -> posts.map { it.toPostEntity() } }
            .applyIfSome { safeDbCall { postDao.updatePosts(it) } }
            .toUnitResult()

    override suspend fun fetchPost(postId: Int): Result<Unit> =
        safeApiCall { postApi.getPost(postId) }
            .map { it.toPostEntity() }
            .applyIfSome { safeDbCall { postDao.insert(it) } }
            .toUnitResult()

    override suspend fun addPost(post: Post): Result<Unit> =
        safeApiCall { postApi.addPost(post.toPostRaw()) }
            .map { postRaw -> postRaw.toPostEntity() }
            .applyIfSome { safeDbCall { postDao.insert(it) } }
            .toUnitResult()
}
