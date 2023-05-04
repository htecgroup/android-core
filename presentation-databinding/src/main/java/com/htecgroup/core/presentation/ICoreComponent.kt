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

package com.htecgroup.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Base component for all Activity and Fragment classes,
 * to provide methods for creating viewmodel and databinding.
 */
interface ICoreComponent<BindingT, ViewModelT> {

    /**
     * Checks if viewmodel is initialized to prevent [UninitializedPropertyAccessException].
     */
    val isViewModelInitialized: Boolean

    /**
     * Checks if binding is initialized to prevent [UninitializedPropertyAccessException].
     */
    val isBindingInitialized: Boolean

    /**
     * Provides viewmodel instance of type [ViewModelT].
     */
    fun provideViewModel(): ViewModelT

    /**
     * Provides binding instance of type [BindingT].
     */
    fun provideDataBinding(inflater: LayoutInflater? = null, container: ViewGroup? = null): BindingT

    /**
     * Provides layout id to be inflated.
     */
    @LayoutRes
    fun provideLayoutId(): Int

    /**
     * Provides [ViewModelT] class so that the viewmodel can be generated.
     */
    fun provideViewModelClass(): Class<ViewModelT>
}
