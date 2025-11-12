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

package com.htecgroup.coresample.presentation.welcome

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.remember
import com.htecgroup.androidcore.presentation.compose.navigation.combineMetadata
import com.htecgroup.androidcore.presentation.compose.navigation.withBottomBarVisible
import com.htecgroup.androidcore.presentation.model.CoreNavEntry
import com.htecgroup.androidcore.presentation.model.DataUiState
import com.htecgroup.coresample.presentation.post.list.postListEntry

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val welcomeEntry = CoreNavEntry(
    key = WelcomeDestination,
    content = { backStack, _ ->
        var uiState: DataUiState<Unit> = remember { DataUiState.Idle() }
        WelcomeScreen(uiState = uiState) {
            uiState = uiState.toLoading()
            backStack.clear()
            backStack.addEntryAndPush(postListEntry)
        }
    },
    metadata = combineMetadata(
        withBottomBarVisible(false),
    )
)