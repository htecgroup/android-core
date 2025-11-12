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

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.htecgroup.androidcore.presentation.compose.navigation.CoreNavBackStack
import com.htecgroup.androidcore.presentation.compose.navigation.NavMetadataKeys
import com.htecgroup.androidcore.presentation.model.BottomBarEntry

/**
 * Displays a bottom navigation bar with dynamic tab selection and visibility.
 *
 * Highlights the selected tab based on current back stack,
 * and allows dynamic tab switching without duplication.
 * Hides itself if the current entry's metadata contains `SHOW_BOTTOM_BAR = false`.
 *
 * @param tabs List of bottom bar tabs.
 * @param backStack Navigation back stack used for tab selection and navigation.
 */
@Composable
fun BottomBar(
    tabs: List<BottomBarEntry>,
    backStack: CoreNavBackStack
) {
    val currentKey = backStack.lastOrNull()
    val currentEntry = currentKey?.let { backStack.dynamicEntries[it] }
    val metadata = currentEntry?.metadata.orEmpty()

    if (metadata[NavMetadataKeys.SHOW_BOTTOM_BAR] as? Boolean == false) return

    val tabKeys = tabs.map { it.entry.key }.toSet()
    val activeTabKey = backStack
        .asReversed()
        .firstOrNull { it in tabKeys }

    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(tab.label) },
                selected = tab.entry.key == activeTabKey,
                onClick = {
                    backStack.navigateToTab(tab.entry.key, tab.entry)
                }
            )
        }
    }
}
