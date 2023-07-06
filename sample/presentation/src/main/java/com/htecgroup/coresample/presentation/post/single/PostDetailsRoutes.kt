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

package com.htecgroup.coresample.presentation.post.single

import com.htecgroup.androidcore.presentation.compose.navigation.ToDestination
import com.htecgroup.androidcore.presentation.compose.navigation.route
import com.htecgroup.coresample.presentation.post.PostsDestinations

data class PostDetailsRoutes(val id: Int) : ToDestination(
    PostsDestinations.EDIT_POST_SCREEN,
    listOf(id.toString()),
    {
        // Note: this is just an example to showcase that it's possible to pop a destination from the backstack
        // Since it pops to itself, it doesn't do much
        popUpTo(PostsDestinations.POST_DETAILS_SCREEN.route)
    }
)
