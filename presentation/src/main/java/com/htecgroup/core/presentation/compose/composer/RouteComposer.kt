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

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.htecgroup.core.presentation.compose.BarButton
import com.htecgroup.core.presentation.compose.navigation.Destination
import com.htecgroup.core.presentation.compose.navigation.route

/**
 * Composes screens with basic UI components (TopBar, BottomBar) and attached [NavHostController].
 * Saves and holds the state for the current screen.
 */
abstract class RouteComposer<D : Destination> {

    abstract val destinations: Array<D>

    abstract val destinationComposers: Array<DestinationComposer<*>>

    /**
     * Sets first screen as an initial screen.
     */
    protected open val screen: MutableState<D> by lazy { mutableStateOf(destinations.first()) }
    val currentScreen: State<D> by lazy { screen }

    protected open val bottomScreenButtons: List<BarButton.ScreenButton> = emptyList()

    /**
     * Returns [DestinationComposer] for each screen.
     */
    private fun getComposer(destination: D): DestinationComposer<*> =
        destinationComposers.single { it.destination == destination }

    /**
     * Extension navigation function for composing screens.
     */
    fun NavGraphBuilder.composeNavGraph(navController: NavHostController) {
        destinations.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.args ?: emptyList(),
            ) {
                BackHandler(navController)

                this@RouteComposer.screen.value = screen

                with(getComposer(screen)) {
                    Compose(navController)
                }
            }
        }
    }

    @Composable
    fun ComposeTopBar(navController: NavHostController) {
        val screen by this@RouteComposer.screen
        getComposer(screen).TopBar(navController)
    }

    @Composable
    fun ComposeBottomBar(navController: NavHostController) {
        val currentScreen by this@RouteComposer.currentScreen
        NavigationBar {
            bottomScreenButtons.forEach { screenButton ->
                NavigationBarItem(
                    selected = screenButton.destination == currentScreen,
                    onClick = {
                        navController.navigate(screenButton.destination.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screenButton.icon,
                            contentDescription = screenButton.contentDescription
                        )
                    },
                    label = {
                        Text(text = screenButton.contentDescription)
                    }
                )
            }
        }
    }

    @Composable
    fun BackHandler(navController: NavHostController) {
        navController.enableOnBackPressed(true)
    }
}
