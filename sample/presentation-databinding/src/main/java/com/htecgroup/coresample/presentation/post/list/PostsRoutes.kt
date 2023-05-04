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

package com.htecgroup.coresample.presentation.post.list

import com.htecgroup.coresample.presentation.base.BaseRoutes
import com.htecgroup.coresample.presentation.post.PostNavigationController
import com.htecgroup.coresample.presentation.post.PostView
import javax.inject.Inject

class PostsRoutes @Inject constructor(
    private val postNavigationController: PostNavigationController
) : BaseRoutes(postNavigationController.activity) {

    override val navController by lazy { postNavigationController.navController }

    fun navigateToPostDetails(postView: PostView) {
        postNavigationController.navigate(
            PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(
                postView
            )
        )
    }

    fun navigateToAddPost() {
        postNavigationController.navigate(
            PostsFragmentDirections.actionPostsFragmentToAddPostFragment()
        )
    }
}
