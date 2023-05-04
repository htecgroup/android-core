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

package com.htecgroup.androidcore.presentation.compose.composer

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.htecgroup.androidcore.extension.coreViewModel
import com.htecgroup.androidcore.presentation.compose.AnimateFade
import com.htecgroup.androidcore.presentation.compose.BarButton
import com.htecgroup.androidcore.presentation.compose.navigation.Destination

/**
 * Defines Compose UI and elements for a screen.
 */
abstract class DestinationComposer<ViewModelT : ViewModel> {

    abstract val destination: Destination

    /** Sets value to specify up button icon and behaviour action. */
    protected val upButton: MutableState<BarButton?> = mutableStateOf(null)

    /** Sets value to specify context button icon and behaviour action. */
    protected val contextButton: MutableState<BarButton?> = mutableStateOf(null)

    abstract val viewModelClass: Class<ViewModelT>

    /** Sets the visibility of the top bar **/
    abstract val topBarVisible: Boolean

    /**
     * Override to specify a [Composable] content for this screen.
     */
    @Composable
    fun Compose(navController: NavHostController) {
        Content(navController = navController, viewModel = coreViewModel(viewModelClass))
    }

    @Composable
    protected abstract fun Content(navController: NavHostController, viewModel: ViewModelT)

    /**
     * Override to specify [Composable] content for the top app bar
     * @param upButton the up or back button (start aligned)
     * @param contextButton the context button (end aligned)
     */
    @Composable
    abstract fun TopBarContent(
        upButton: State<BarButton?>,
        contextButton: State<BarButton?>
    )

    @Composable
    fun TopBar(navController: NavHostController) {
        if (topBarVisible) {
            AnimateFade(navController.isScreenVisible) {
                TopBarContent(upButton = upButton, contextButton = contextButton)
            }
        }
    }

    val NavController.isScreenVisible: Boolean
        get() = currentDestination?.route?.contains(destination.name) ?: true

    val NavController.arguments: Bundle?
        get() = getBackStackEntry(currentDestination?.route ?: "").arguments
}
