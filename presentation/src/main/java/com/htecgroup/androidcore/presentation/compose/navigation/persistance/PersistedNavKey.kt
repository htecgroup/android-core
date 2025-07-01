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

import org.json.JSONObject

/**
 * Data class representing a persisted navigation key with its ID and parameters.
 *
 * @property id The unique identifier for the navigation key.
 * @property params The map of parameters associated with this navigation key.
 */
data class PersistedNavKey(
    val id: String,
    val params: Map<String, String>
) {
    /**
     * Serializes this [PersistedNavKey] into a JSON string.
     *
     * @return JSON string representation of this PersistedNavKey.
     */
    fun toJson(): String = JSONObject(
        mapOf(
            KEY_ID to id,
            KEY_PARAMS to JSONObject(params)
        )
    ).toString()

    companion object {
        const val KEY_ID = "id"
        const val KEY_PARAMS = "params"

        /**
         * Parses a JSON string to create a [PersistedNavKey]. instance.
         *
         * @param json The JSON string representing a persisted nav key.
         * @return The corresponding PersistedNavKey instance.
         */
        fun fromJson(json: String): PersistedNavKey {
            val obj = JSONObject(json)
            val id = obj.getString(KEY_ID)
            val params = obj.getJSONObject(KEY_PARAMS).toMapOfStrings()
            return PersistedNavKey(id, params)
        }
    }
}

/**
 * Extension function to convert a JSONObject into a Map<String, String>.
 *
 * @receiver JSONObject to convert.
 * @return Map with keys and string values extracted from the JSONObject.
 */
fun JSONObject.toMapOfStrings(): Map<String, String> =
    keys().asSequence().associateWith { getString(it) }
