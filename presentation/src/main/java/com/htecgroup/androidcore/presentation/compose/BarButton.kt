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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import com.htecgroup.androidcore.presentation.compose.navigation.Destination

sealed class BarButton(
    open val icon: ImageVector,
    open val contentDescription: String,
    open val action: () -> Unit
) {
    data class CustomButton(
        override val icon: ImageVector,
        override val contentDescription: String,
        override val action: () -> Unit
    ) : BarButton(icon, contentDescription, action)

    data class BackButton(override val action: () -> Unit) :
        BarButton(Icons.Filled.ArrowBack, "Back", action)

    data class AddButton(override val action: () -> Unit) :
        BarButton(Icons.Filled.Add, "Add", action)

    data class ScreenButton(
        override val icon: ImageVector,
        override val contentDescription: String,
        val destination: Destination
    ) : BarButton(icon, contentDescription, {})
}
