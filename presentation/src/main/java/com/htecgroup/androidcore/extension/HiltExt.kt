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

package com.htecgroup.androidcore.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry

/**
 * Provides [ViewModelT] from class declaration.
 */
@Composable
fun <ViewModelT : ViewModel> coreViewModel(clazz: Class<ViewModelT>): ViewModelT {
    val owner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val factory = if (owner is NavBackStackEntry) {
        HiltViewModelFactory(LocalContext.current, owner)
    } else {
        null
    }

    return androidx.lifecycle.viewmodel.compose.viewModel(
        modelClass = clazz,
        viewModelStoreOwner = owner,
        factory = factory
    )
}
