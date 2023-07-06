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

package com.htecgroup.androidcore.util

import android.app.Activity
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.htecgroup.androidcore.presentation.compose.navigation.Action
import com.htecgroup.androidcore.presentation.compose.navigation.CommonNavigationAction
import com.htecgroup.androidcore.presentation.compose.navigation.ToDestination
import com.htecgroup.androidcore.presentation.compose.navigation.route
import com.htecgroup.androidcore.presentation.viewmodel.CoreViewModel

/**
 * Handles navigation [action].
 */
private fun NavController.handleAction(action: Action) {
    return when (action) {
        is Action.NavigationAction -> navigate(action)
        else -> throw IllegalArgumentException("Unhandled action: $action")
    }
}

/**
 * Handles navigation [action]. Resets viewmodel's state when navigating.
 */
fun NavController.handleAction(action: Action, viewModel: CoreViewModel<*>? = null) {
    viewModel?.clearState()
    handleAction(action)
}

/**
 * Handles navigating using different types of [action].
 */
private fun NavController.navigate(action: Action.NavigationAction) =
    when (action) {
        is ToDestination -> navigate(action.route, action.navOptions)
        is CommonNavigationAction -> navigate(action)
        else -> throw IllegalArgumentException("Unhandled navigation action: $action")
    }

/**
 * Handles most common types of navigation using [action].
 */
private fun NavController.navigate(action: CommonNavigationAction) {
    when (action) {
        is CommonNavigationAction.Back -> onBack()
        is CommonNavigationAction.Up -> navigateUp()
    }
}

fun NavController.onBack() {
    if (isFirstScreen) {
        (context as Activity).finish()
    } else {
        popBackStack()
    }
}

val NavController.isFirstScreen: Boolean
    get() = currentBackStackEntry?.isFirstScreen == true &&
        backQueue.size <= 2 // initial backstack size

private val NavBackStackEntry.isFirstScreen: Boolean
    get() =
        destination.parent?.startDestinationRoute == destination.route
