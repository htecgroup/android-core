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

package com.htecgroup.androidcore.presentation.compose.navigation.persistance

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.navigation3.runtime.NavBackStack
import com.htecgroup.androidcore.presentation.compose.navigation.CoreNavBackStack

/**
 * Handles persistence of navigation state by saving and restoring navigation stacks.
 *
 * @property knownDestinations A map from navigation key IDs to factory functions
 *                             that can recreate PersistableNavDestination instances from params.
 */
class NavPersistence(
    private val knownDestinations: Map<String, (Map<String, String>) -> PersistableNavDestination>
) {
    /**
     * A saver to convert [CoreNavBackStack] instances to a serializable form and back.
     *
     * Saves each [PersistableNavDestination] as a JSON string and restores by invoking
     * the corresponding factory from knownDestinations.
     */
    val saver: Saver<CoreNavBackStack, Any> = listSaver(
        save = { stack ->
            stack.map {
                val dest = it as? PersistableNavDestination
                    ?: error("Non-persistable key: $it")
                PersistedNavKey(dest.navKeyId, dest.encodeParams()).toJson()
            }
        },
        restore = { saved ->
            val keys = saved.map {
                val (id, params) = PersistedNavKey.fromJson(it)
                knownDestinations[id]?.invoke(params)
                    ?: error("Unknown destination id: $id")
            }
            CoreNavBackStack(
                NavBackStack().apply { addAll(keys) },
                keys.associateWith { it.createEntry() }.toMutableMap()
            )
        }
    )
}
