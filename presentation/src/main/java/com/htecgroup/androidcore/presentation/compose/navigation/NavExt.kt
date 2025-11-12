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

package com.htecgroup.androidcore.presentation.compose.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.Composable

/**
 * Provides metadata for setting a context (action) button in the top bar.
 *
 * @param button A deferred top bar button builder.
 * @return A metadata map to include in [com.htecgroup.androidcore.presentation.model.CoreNavEntry].
 */
fun withContextButton(button: DeferredTopBarButton) = mapOf(
    NavMetadataKeys.CONTEXT_BUTTON to button
)

/**
 * Provides metadata for setting a title in the top bar.
 *
 * @param titleResId string resource id of the tile.
 * @return A metadata map to include in [com.htecgroup.androidcore.presentation.model.CoreNavEntry].
 */
fun withTitle(titleResId: Int) = mapOf(
    NavMetadataKeys.SCREEN_TITLE to titleResId
)

/**
 * Provides metadata to show a back/up button in the top bar.
 *
 * @param enabled A flag that indicates if the back/up button should be visible in the top bar.
 * @return A metadata map to include in [com.htecgroup.androidcore.presentation.model.CoreNavEntry].
 */
fun withBackButton(enabled: Boolean = true) = mapOf(
    NavMetadataKeys.SHOW_BACK_BUTTON to enabled
)

/**
 * Sets whether the bottom bar should be visible for this screen.
 *
 * Default is true if not explicitly set.
 */
fun withBottomBarVisible(visible: Boolean) = mapOf(
    NavMetadataKeys.SHOW_BOTTOM_BAR to visible
)

/**
 * Provides metadata to configure the list pane in a list-detail scene layout.
 *
 * @param sceneKey Optional key to identify the scene.
 * @param detailPlaceholder Optional content to show when the detail pane is empty.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun withListPane(
    sceneKey: Any = Unit,
    detailPlaceholder: @Composable ThreePaneScaffoldScope.() -> Unit = {},
): Map<String, Any> =
    ListDetailSceneStrategy.listPane(sceneKey, detailPlaceholder)

/**
 * Provides metadata to configure the detail pane in a list-detail scene layout.
 *
 * @param sceneKey Optional key to identify the scene.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun withDetailPane(sceneKey: Any = Unit): Map<String, Any> =
    ListDetailSceneStrategy.detailPane(sceneKey)

/**
 * Provides metadata to configure the extra pane in a list-detail scene layout.
 *
 * @param sceneKey Optional key to identify the scene.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun withExtraPane(sceneKey: Any = Unit): Map<String, Any> =
    ListDetailSceneStrategy.extraPane(sceneKey)

/**
 * Merges multiple metadata maps into one.
 *
 * Later maps override earlier keys if duplicated.
 */
fun combineMetadata(vararg maps: Map<String, Any>) = buildMap {
    maps.forEach { putAll(it) }
}
