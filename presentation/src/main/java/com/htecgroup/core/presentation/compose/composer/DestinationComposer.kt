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

package com.htecgroup.core.presentation.compose.composer

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.htecgroup.core.extension.coreViewModel
import com.htecgroup.core.presentation.compose.AnimateFade
import com.htecgroup.core.presentation.compose.BarButton
import com.htecgroup.core.presentation.compose.DefaultTopBar
import com.htecgroup.core.presentation.compose.navigation.Destination

/**
 * Defines Compose UI and elements for a screen.
 */
abstract class DestinationComposer<ViewModelT : ViewModel> {

    abstract val titleResId: Int

    abstract val destination: Destination

    /** Sets value to specify up button icon and behaviour action. */
    protected val upButton: MutableState<BarButton?> = mutableStateOf(null)

    /** Sets value to specify context button icon and behaviour action. */
    protected val contextButton: MutableState<BarButton?> = mutableStateOf(null)

    /** Sets value to specify bottom bar button icons and behaviour actions. */
    protected val bottomBarButtons: SnapshotStateList<BarButton.ScreenButton> = mutableStateListOf()

    abstract val viewModelClass: Class<ViewModelT>

    /**
     * Override to specify a [Composable] content for this screen.
     */
    @Composable
    fun Compose(navController: NavHostController) {
        Content(navController = navController, viewModel = coreViewModel(viewModelClass))
    }

    @Composable
    protected abstract fun Content(navController: NavHostController, viewModel: ViewModelT)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    open fun TopBarContent(
        titleResId: Int,
        upButton: State<BarButton?>,
        contextButton: State<BarButton?>
    ) {
        DefaultTopBar(
            titleResId = titleResId,
            topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
            upButton = upButton,
            contextButton = contextButton
        )
    }

    @Composable
    fun TopBar(navController: NavHostController) {
        AnimateFade(navController.isScreenVisible) {
            TopBarContent(titleResId, upButton = upButton, contextButton = contextButton)
        }
    }

    val NavController.isScreenVisible: Boolean
        get() = currentDestination?.route?.contains(destination.name) ?: true

    val NavController.arguments: Bundle?
        get() = getBackStackEntry(currentDestination?.route ?: "").arguments
}
