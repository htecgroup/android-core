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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import com.htecgroup.androidcore.presentation.compose.navigation.CoreNavBackStack
import com.htecgroup.androidcore.presentation.compose.navigation.DeferredTopBarButton
import com.htecgroup.androidcore.presentation.compose.navigation.NavMetadataKeys
import com.htecgroup.androidcore.presentation.model.CoreNavEntry

/**
 * Displays the top app bar based on the current navigation entry metadata.
 *
 * This function dynamically configures the top bar by reading metadata associated with
 * the current [CoreNavEntry]. It supports:
 * - Dynamic screen titles via [NavMetadataKeys.SCREEN_TITLE]
 * - Optional back/up button via [NavMetadataKeys.SHOW_BACK_BUTTON]
 * - Optional context action button via [NavMetadataKeys.CONTEXT_BUTTON]
 *
 * The metadata keys are intended to be set when defining each [CoreNavEntry].
 *
 * @param backStack The navigation back stack used to determine current entry and perform back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(backStack: CoreNavBackStack) {
    val currentEntry: CoreNavEntry? =
        backStack.lastOrNull()?.let { backStack.dynamicEntries[it] }
    val metadata = currentEntry?.metadata.orEmpty()

    // Conditionally show the "up" button if specified in entry metadata
    val upButton = if (metadata[NavMetadataKeys.SHOW_BACK_BUTTON] as? Boolean == true) {
        TopBarButton.BackButton { backStack.removeLastOrNull() }
    } else {
        null
    }

    // Optional context button, built lazily via a deferred function stored in metadata
    val contextButton = remember(currentEntry) {
        val deferred = metadata[NavMetadataKeys.CONTEXT_BUTTON] as? DeferredTopBarButton
        deferred?.build(backStack)
    }

    // Render the default top bar with dynamic content
    DefaultTopBar(
        titleResId = metadata[NavMetadataKeys.SCREEN_TITLE] as? Int,
        upButton = upButton,
        contextButton = contextButton,
        topAppBarColors = TopAppBarDefaults.topAppBarColors(),
        textAlign = TextAlign.Center
    )
}
