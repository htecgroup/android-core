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

import com.htecgroup.androidcore.presentation.compose.navigation.combineMetadata
import com.htecgroup.androidcore.presentation.compose.navigation.withBackButton
import com.htecgroup.androidcore.presentation.compose.navigation.withExtraPane
import com.htecgroup.androidcore.presentation.compose.navigation.withTitle
import com.htecgroup.androidcore.presentation.model.CoreNavEntry
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.di.hiltAssistedViewModel
import com.htecgroup.coresample.presentation.post.SceneKey

fun editPostEntry(key: EditPostDestination) = CoreNavEntry(
    key = key,
    content = { backStack, snackbarHostState ->
        val viewModel =
            hiltAssistedViewModel<EditPostViewModel, EditPostViewModel.Factory, EditPostDestination>(
                navKey = key
            ) { factory -> factory.create(key) }
        EditPostScreen(
            uiState = viewModel.uiState,
            titleState = viewModel.postTitle,
            bodyState = viewModel.postBody,
            onSaveClick = viewModel::onSaveClick,
            onSaved = {
                backStack.removeLastEntryOrNull()
            }
        )
    },
    metadata = combineMetadata(
        withTitle(R.string.title_edit_post),
        withBackButton(true),
        withExtraPane(sceneKey = SceneKey.Posts)
    )
)
