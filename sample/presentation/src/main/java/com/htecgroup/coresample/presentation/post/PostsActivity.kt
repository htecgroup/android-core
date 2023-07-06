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

package com.htecgroup.coresample.presentation.post

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.htecgroup.androidcore.presentation.CoreActivity
import com.htecgroup.androidcore.presentation.compose.composer.RouteComposer
import com.htecgroup.coresample.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsActivity : CoreActivity() {

    override val startDestination: String = PostsDestinations.POSTS_SCREEN.name

    @Inject
    lateinit var postsRoute: RouteComposer<PostsDestinations>

    @Composable
    override fun Theme(content: @Composable () -> Unit) = AppTheme(content = content)

    override fun NavGraphBuilder.navGraph(navController: NavHostController) {
        with(postsRoute) {
            composeNavGraph(navController)
        }
    }

    @Composable
    override fun TopBar(snackbarHostState: SnackbarHostState, navController: NavHostController) {
        with(postsRoute) {
            ComposeTopBar(navController)
        }
    }
}
