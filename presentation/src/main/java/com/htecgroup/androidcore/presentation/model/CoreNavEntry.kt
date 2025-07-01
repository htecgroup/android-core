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

package com.htecgroup.androidcore.presentation.model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.htecgroup.androidcore.presentation.compose.navigation.CoreNavBackStack

/**
 * Represents a dynamically defined navigation entry.
 *
 * This allows content to be registered and rendered at runtime,
 * supporting modular and decoupled navigation logic.
 *
 * @property key The unique navigation key identifying this screen.
 * @property content Composable content to render when this entry is active.
 *   Receives the [CoreNavBackStack] for navigation actions and [SnackbarHostState] for UI feedback.
 * @property contentKey A stable, saveable identifier for the content (used for recomposition and state).
 * @property metadata Optional map for passing screen-specific data such as toolbar config or tags.
 */
class CoreNavEntry(
    val key: NavKey,
    var content: @Composable (
        backStack: CoreNavBackStack,
        snackbarHostState: SnackbarHostState
    ) -> Unit,
    val contentKey: Any = key.toString(),
    val metadata: Map<String, Any> = emptyMap()
)

/**
 * Maps a [CoreNavEntry] to a concrete [NavEntry] required by the NavDisplay.
 *
 * This wraps the dynamic entry into the structure expected by the navigation library.
 *
 * @param backStack The dynamic back stack context used by the composable content.
 * @param snackbarHostState Snackbar state used for showing transient messages.
 * @return A [NavEntry] ready to be rendered in a navigation display.
 */
fun CoreNavEntry.toNavEntry(
    backStack: CoreNavBackStack,
    snackbarHostState: SnackbarHostState
): NavEntry<NavKey> = NavEntry(
    key = key,
    content = { content(backStack, snackbarHostState) },
    contentKey = contentKey,
    metadata = metadata,
)
