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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.htecgroup.androidcore.presentation.compose.navigation.persistance.NavPersistence
import com.htecgroup.androidcore.presentation.model.CoreNavEntry

/**
 * A wrapper over [NavBackStack] that supports dynamically created navigation entries.
 *
 * This enables:
 * - Pushing entries that are not known statically
 * - Associating custom content per key
 * - Supporting dynamic navigation in modular apps
 *
 * @property navBackStack The actual stack of [NavKey]s
 * @property entries Map of keys to their corresponding [CoreNavEntry]
 */
class CoreNavBackStack(
    private val navBackStack: NavBackStack,
    private val entries: MutableMap<NavKey, CoreNavEntry> = mutableMapOf(),
) : MutableList<NavKey> by navBackStack {

    /**
     * Map of current active entries by key. Retrieves the actual content definition per key.
     */
    val dynamicEntries: MutableMap<NavKey, CoreNavEntry>
        get() = entries

    /**
     * Adds the given [CoreNavEntry] to the internal map and pushes its key onto the stack.
     * If the key already exists, it is not re-added to the entry map.
     */
    fun addEntryAndPush(entry: CoreNavEntry) {
        if (entry.key !in entries) entries[entry.key] = entry
        add(entry.key)
    }

    /**
     * Pops the last key from the backStack and removes its associated entry.
     *
     * @return the removed key, or null if the stack was empty
     */
    fun removeLastEntryOrNull(): NavKey? {
        if (navBackStack.isNotEmpty()) {
            val key = navBackStack.removeLastOrNull()
            entries.remove(key)
            return key
        }
        return null
    }

    /**
     * Navigates to the given tab entry in the back stack.
     * Avoids duplicate tab entries and ensures only one instance of a tab is in the back stack.
     *
     * @param tabKey The key representing the root [NavKey] of the tab
     * @param tabEntry The [CoreNavEntry] associated with the tab
     */
    fun navigateToTab(tabKey: NavKey, tabEntry: CoreNavEntry) {
        if (contains(tabKey)) {
            while (lastOrNull() != tabKey) {
                removeLastOrNull()
            }
        } else {
            addEntryAndPush(tabEntry)
        }
    }
}

/**
 * Composable function that remembers and saves/restores a [CoreNavBackStack].
 *
 * @param startNavEntry The initial navigation entry to start the stack with.
 * @param persistence The [NavPersistence] instance providing the saver logic.
 *
 * @return A remembered DynamicNavBackStack that survives configuration changes and process death.
 */
@Composable
fun rememberCoreNavBackStack(
    startNavEntry: CoreNavEntry,
    persistence: NavPersistence
): CoreNavBackStack {
    return rememberSaveable(saver = persistence.saver) {
        val stack = NavBackStack().apply { add(startNavEntry.key) }
        val entries = mutableMapOf(startNavEntry.key to startNavEntry)
        CoreNavBackStack(stack, entries)
    }
}
