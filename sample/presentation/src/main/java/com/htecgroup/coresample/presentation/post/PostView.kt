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

import android.os.Parcelable
import com.htecgroup.coresample.domain.post.Post
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
data class PostView(
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    val user: UserView? = UserView(),
    var changesSaved: Boolean = false,
    var shouldEdit: Boolean = false,
    var shouldDelete: Boolean = false
) : Parcelable

fun PostView.toPost() = Post(id, title.lowercase(Locale.getDefault()), description)

fun Post.toPostView() = PostView(
    id,
    title = if (title.isNotEmpty()) title.toUpperCase(Locale.getDefault()) else "--",
    description = if (description.isNotEmpty()) description else "--",
    user = user?.toUserView()
)
