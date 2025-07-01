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

package com.htecgroup.androidcore.presentation.compose

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.htecgroup.androidcore.presentation.compose.navigation.CoreNavBackStack
import com.htecgroup.androidcore.presentation.compose.navigation.persistance.NavPersistence
import com.htecgroup.androidcore.presentation.compose.navigation.rememberCoreNavBackStack
import com.htecgroup.androidcore.presentation.model.BottomBarEntry
import com.htecgroup.androidcore.presentation.model.CoreNavEntry
import com.htecgroup.androidcore.presentation.model.toNavEntry

/**
 * Core UI container that manages dynamic navigation and scaffold layout.
 *
 * This function:
 * - Initializes a [CoreNavBackStack] starting from [startNavEntry].
 * - Hosts a [NavDisplay] with entry decorators and a scene strategy.
 * - Automatically renders a top bar based on each entry's metadata (e.g. title, back button, context actions).
 * - Optionally shows a [BottomBar] if [bottomTabs] are provided and the current entry allows it.
 * - Applies [theme] wrapper for consistent styling (default is [MaterialTheme]).
 *
 * @param modifier Modifier to apply to the root layout.
 * @param theme Composable theme wrapper (e.g. MaterialTheme).
 * @param startNavEntry Initial navigation entry to render on launch.
 * @param persistence An instance of [NavPersistence] that provides the Saver
 *                     used to save and restore the navigation back stack state.
 * @param bottomTabs Optional list of bottom bar tabs.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CoreNavigableContent(
    modifier: Modifier = Modifier,
    theme: @Composable (@Composable () -> Unit) -> Unit = { content -> MaterialTheme { content() } },
    startNavEntry: CoreNavEntry,
    persistence: NavPersistence,
    bottomTabs: List<BottomBarEntry>? = null,
) {
    val backStack = rememberCoreNavBackStack(startNavEntry, persistence)

    val entryDecorators: List<NavEntryDecorator<Any>> = mutableListOf(
        rememberSavedStateNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator(),
        rememberSceneSetupNavEntryDecorator()
    )
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val snackbarHostState = remember { SnackbarHostState() }

    theme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = { TopBar(backStack) },
            bottomBar = {
                bottomTabs?.let { tabs ->
                    BottomBar(tabs, backStack)
                }
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavDisplay(
                    backStack = backStack,
                    modifier = modifier,
                    onBack = { keysToRemove -> repeat(keysToRemove) { backStack.removeLastOrNull() } },
                    entryDecorators = entryDecorators,
                    sceneStrategy = listDetailStrategy,
                    entryProvider = { key ->
                        val entry = backStack.dynamicEntries[key]
                            ?: error("Invalid NavKey: $key. Available keys: ${backStack.dynamicEntries.keys}")
                        entry.toNavEntry(backStack, snackbarHostState)
                    },
                    transitionSpec = {
                        // Slide in from right when navigating forward
                        slideInHorizontally(initialOffsetX = { it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { -it })
                    },
                    popTransitionSpec = {
                        // Slide in from left when navigating back
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { it })
                    },
                    predictivePopTransitionSpec = {
                        // Slide in from left when navigating back
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { it })
                    }
                )
            }
        }
    }
}
