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

package com.htecgroup.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Base [RecyclerView] adapter implemented using [ListAdapter].
 */
abstract class CoreDiffAdapter<ItemT>(diffUtilItemCallback: DiffUtil.ItemCallback<ItemT> = getItemDiffCallback()) :
    ListAdapter<ItemT, BindingViewHolder>(diffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) =
        holder.bindViewModel(provideViewModel(position))

    override fun getItemViewType(position: Int) = provideLayoutId(position)

    /**
     * Returns the layout id of an item depending on the [position].
     * Different layout id for different view type is returned.
     */
    @LayoutRes
    abstract fun provideLayoutId(position: Int): Int

    /**
     * Provides [ViewModel] instance depending on the [position].
     */
    abstract fun provideViewModel(position: Int): ViewModel
}
