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

package com.htecgroup.androidcore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey

/**
 * A factory interface for creating instances of [ViewModel] with assisted injection.
 *
 * @param K The type of the [NavKey] used to create the ViewModel.
 * @param VM The type of the [ViewModel] being created.
 */
interface AssistedViewModelFactory<K : NavKey, VM : ViewModel> {
    fun create(navKey: K): VM
}
