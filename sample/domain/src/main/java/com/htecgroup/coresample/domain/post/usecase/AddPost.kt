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

import com.htecgroup.androidcore.domain.CoreUseCase
import com.htecgroup.androidcore.domain.IResultParamUseCase
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.PostRepository
import javax.inject.Inject

class AddPost @Inject constructor(private val postRepository: PostRepository) :
    CoreUseCase(),
    IResultParamUseCase<Post, Unit> {

    override suspend fun invoke(data: Post) = postRepository.addPost(data)
}
