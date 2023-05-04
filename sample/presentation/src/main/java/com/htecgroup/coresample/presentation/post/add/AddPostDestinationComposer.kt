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

package com.htecgroup.coresample.presentation.post.add

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.htecgroup.core.presentation.compose.AnimateSlide
import com.htecgroup.core.presentation.compose.BarButton
import com.htecgroup.core.presentation.compose.composer.DestinationComposer
import com.htecgroup.core.presentation.compose.navigation.CommonNavigationAction.Back
import com.htecgroup.core.presentation.compose.navigation.CommonNavigationAction.Up
import com.htecgroup.core.presentation.compose.navigation.Destination
import com.htecgroup.core.util.handleAction
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostsDestinations
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AddPostDestinationComposer @Inject constructor() : DestinationComposer<AddPostViewModel>() {

    override val titleResId = R.string.title_add_post
    override val destination: Destination = PostsDestinations.ADD_POST_SCREEN
    override val viewModelClass: Class<AddPostViewModel> = AddPostViewModel::class.java

    @Composable
    override fun Content(navController: NavHostController, viewModel: AddPostViewModel) {
        upButton.value = BarButton.BackButton {
            navController.handleAction(Up)
        }

        if (viewModel.uiState.data == null) {
            navController.handleAction(Back, viewModel)
        }

        AnimateSlide(navController.isScreenVisible, -1) {
            AddPostScreen(viewModel.postTitle, viewModel.postBody, viewModel::onSaveClick)
        }
    }
}
