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

package com.htecgroup.coresample.presentation.post.edit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import com.htecgroup.androidcore.presentation.compose.AnimateSlide
import com.htecgroup.androidcore.presentation.compose.BarButton
import com.htecgroup.androidcore.presentation.compose.DefaultTopBar
import com.htecgroup.androidcore.presentation.compose.composer.DestinationComposer
import com.htecgroup.androidcore.presentation.compose.navigation.CommonNavigationAction.Back
import com.htecgroup.androidcore.presentation.compose.navigation.CommonNavigationAction.Up
import com.htecgroup.androidcore.presentation.compose.navigation.Destination
import com.htecgroup.androidcore.util.handleAction
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostsDestinations
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EditPostDestinationComposer @Inject constructor() : DestinationComposer<EditPostViewModel>() {

    override val topBarVisible = true
    override val destination: Destination = PostsDestinations.EDIT_POST_SCREEN
    override val viewModelClass: Class<EditPostViewModel> = EditPostViewModel::class.java
    private val titleResId = R.string.title_edit_post

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun TopBarContent(upButton: State<BarButton?>, contextButton: State<BarButton?>) {
        DefaultTopBar(
            titleResId = titleResId,
            topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
            upButton = upButton,
            contextButton = contextButton
        )
    }

    @Composable
    override fun Content(navController: NavHostController, viewModel: EditPostViewModel) {
        upButton.value = BarButton.BackButton {
            navController.handleAction(Up)
        }

        AnimateSlide(navController.isScreenVisible, -1) {
            EditPostScreen(
                uiState = viewModel.uiState,
                titleState = viewModel.postTitle,
                bodyState = viewModel.postBody,
                onSaveClick = viewModel::onSaveClick,
                onSaved = { navController.handleAction(Back, viewModel) }
            )
        }
    }
}
