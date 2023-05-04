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

package com.htecgroup.core.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Base starting screen with base UI implementation.
 */
@Suppress("FunctionName")
interface CoreScreenRoot {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainContent() {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }

        Theme {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = { TopBar(snackbarHostState, navController) },
                bottomBar = { BottomBar(snackbarHostState, navController) }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    ScaffoldContent(navController)
                }
            }
        }
    }

    @Composable
    fun TopBar(snackbarHostState: SnackbarHostState, navController: NavHostController) {
        // No op
    }

    @Composable
    fun BottomBar(snackbarHostState: SnackbarHostState, navController: NavHostController) {
        // No op
    }

    @Composable
    fun ScaffoldContent(navController: NavHostController)

    @Composable
    fun Theme(content: @Composable () -> Unit)
}
