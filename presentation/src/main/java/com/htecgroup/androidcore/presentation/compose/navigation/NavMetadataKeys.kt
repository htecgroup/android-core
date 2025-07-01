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

/**
 * Keys used in DynamicNavEntry.metadata to configure screen-level behaviors
 * such as title, top bar actions, and navigation options.
 */
object NavMetadataKeys {
    /** Integer resource ID for the screen title */
    const val SCREEN_TITLE = "screen_title"

    /** Flag to indicate whether the top bar should show the back/up button */
    const val SHOW_BACK_BUTTON = "show_back_button"

    /** A [DeferredTopBarButton] to display as the contextual action on the top bar */
    const val CONTEXT_BUTTON = "context_button"

    /** Flag that controls bottom bar visibility for a navigation entry. */
    const val SHOW_BOTTOM_BAR = "showBottomBar"
}
