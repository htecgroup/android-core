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

package com.htecgroup.coresample.presentation.post

import com.htecgroup.androidcore.presentation.compose.composer.DestinationComposer
import com.htecgroup.androidcore.presentation.compose.composer.RouteComposer
import com.htecgroup.coresample.presentation.post.add.AddPostDestinationComposer
import com.htecgroup.coresample.presentation.post.edit.EditPostDestinationComposer
import com.htecgroup.coresample.presentation.post.list.PostListDestinationComposer
import com.htecgroup.coresample.presentation.post.single.PostDetailsDestinationComposer
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class PostsRouteComposer @Inject constructor(
    private val postListDestinationComposer: PostListDestinationComposer,
    private val postDetailsDestinationComposer: PostDetailsDestinationComposer,
    private val editPostDestinationComposer: EditPostDestinationComposer,
    private val addPostsDestinationsComposer: AddPostDestinationComposer
) : RouteComposer<PostsDestinations>() {

    override val destinations: Array<PostsDestinations> = PostsDestinations.values()

    override val destinationComposers: Array<DestinationComposer<*>> = arrayOf(
        postListDestinationComposer,
        postDetailsDestinationComposer,
        editPostDestinationComposer,
        addPostsDestinationsComposer
    )
}
