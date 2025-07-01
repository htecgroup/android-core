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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.htecgroup.androidcore.presentation.compose.TopBarButton
import com.htecgroup.androidcore.presentation.compose.navigation.DeferredTopBarButton
import com.htecgroup.androidcore.presentation.compose.navigation.combineMetadata
import com.htecgroup.androidcore.presentation.compose.navigation.withBackButton
import com.htecgroup.androidcore.presentation.compose.navigation.withContextButton
import com.htecgroup.androidcore.presentation.compose.navigation.withListPane
import com.htecgroup.androidcore.presentation.compose.navigation.withTitle
import com.htecgroup.androidcore.presentation.model.CoreNavEntry
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.SceneKey
import com.htecgroup.coresample.presentation.post.add.addPostEntry
import com.htecgroup.coresample.presentation.post.single.PostDetailsDestination
import com.htecgroup.coresample.presentation.post.single.postDetailsEntry

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val postListEntry = CoreNavEntry(
    key = PostListDestination,
    content = { backStack, snackbarHostState ->
        val viewModel: PostsViewModel = hiltViewModel()
        PostListScreen(
            uiState = viewModel.uiState,
            onRefresh = viewModel::refreshPosts,
            onClickItem = { postView ->
                with(PostDetailsDestination(postView.id)) {
                    backStack.addEntryAndPush(postDetailsEntry(this))
                }
            },
        )
    },
    metadata = combineMetadata(
        withTitle(R.string.title_posts_list),
        withBackButton(false),
        withListPane(sceneKey = SceneKey.Posts, detailPlaceholder = {
            Text(
                stringResource(R.string.placeholder_click_for_details),
                Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }),
        withContextButton(
            DeferredTopBarButton { backStack ->
                TopBarButton.AddButton {
                    backStack.addEntryAndPush(addPostEntry)
                }
            }
        )
    )
)
