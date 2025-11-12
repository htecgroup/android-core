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

import com.htecgroup.androidcore.presentation.compose.navigation.persistance.NavKeyFactory
import com.htecgroup.androidcore.presentation.compose.navigation.persistance.PersistableNavDestination
import com.htecgroup.androidcore.presentation.model.CoreNavEntry

data class EditPostDestination(val id: Int) : PersistableNavDestination {
    override val navKeyId: String = NAV_KEY_ID
    override fun encodeParams(): Map<String, String> = mapOf(ID_PARAM to id.toString())
    override fun createEntry(): CoreNavEntry = editPostEntry(this)

    companion object : NavKeyFactory<EditPostDestination> {
        const val NAV_KEY_ID = "edit_post"
        private const val ID_PARAM = "id"
        override fun fromParams(params: Map<String, String>): EditPostDestination {
            val id = params[ID_PARAM]?.toIntOrNull() ?: error("Missing id")
            return EditPostDestination(id)
        }
    }
}
