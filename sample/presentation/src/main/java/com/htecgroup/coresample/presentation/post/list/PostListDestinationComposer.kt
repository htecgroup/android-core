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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.navigation.NavHostController
import com.htecgroup.androidcore.presentation.compose.AnimateSlide
import com.htecgroup.androidcore.presentation.compose.BarButton
import com.htecgroup.androidcore.presentation.compose.DefaultTopBar
import com.htecgroup.androidcore.presentation.compose.composer.DestinationComposer
import com.htecgroup.androidcore.presentation.compose.navigation.Destination
import com.htecgroup.androidcore.presentation.compose.navigation.route
import com.htecgroup.androidcore.util.handleAction
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostsDestinations
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class PostListDestinationComposer @Inject constructor() : DestinationComposer<PostsViewModel>() {

    override val topBarVisible = true
    override val destination: Destination = PostsDestinations.POSTS_SCREEN
    override val viewModelClass: Class<PostsViewModel> = PostsViewModel::class.java
    private val titleResId = R.string.title_posts_list

    @Composable
    override fun Content(navController: NavHostController, viewModel: PostsViewModel) {
        contextButton.value = BarButton.AddButton {
            navController.navigate(PostsDestinations.ADD_POST_SCREEN.route)
        }

        AnimateSlide(navController.isScreenVisible, -1) {
            PostListScreen(viewModel.uiState, viewModel::refreshPosts) { postView ->
                navController.handleAction(PostListRoutes(postView.id))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun TopBarContent(
        upButton: State<BarButton?>,
        contextButton: State<BarButton?>
    ) {
        DefaultTopBar(
            titleResId = titleResId,
            topAppBarColors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            contextButton = contextButton,
            textAlign = Companion.Center
        )
    }
}
