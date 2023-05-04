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

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * [DiffUtil.ItemCallback] is generic callback used in [ListAdapter].
 */
@Suppress("WRONG_TYPE_PARAMETER_NULLABILITY_FOR_JAVA_OVERRIDE")
fun <ItemT> getItemDiffCallback() = object : DiffUtil.ItemCallback<ItemT>() {
    override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT): Boolean =
        oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT): Boolean =
        oldItem == newItem
}

/**
 * [DiffUtil.DiffResult] is used to calculate diff between [old] and [new] lists.
 */
fun <ItemT> getDiffCallback(old: List<ItemT>, new: List<ItemT>) =
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] === new[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] == new[newItemPosition]

        override fun getOldListSize() = old.size
        override fun getNewListSize() = new.size
    })
