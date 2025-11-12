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

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a single item in the bottom navigation bar.
 *
 * @property label The text shown below the icon
 * @property icon The visual icon for this tab
 * @property entry The root [CoreNavEntry] this tab represents
 */
data class BottomBarEntry(
    val label: String,
    val icon: ImageVector,
    val entry: CoreNavEntry,
)
