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
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Base [RecyclerView] adapter. Contains basic data manipulation
 * methods and logic for creating and binding view holder.
 */
@Suppress("TooManyFunctions", "unused", "MemberVisibilityCanBePrivate")
abstract class CoreAdapter<ItemT>(private var items: MutableList<ItemT> = ArrayList()) :
    RecyclerView.Adapter<BindingViewHolder>() {

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

    override fun getItemCount() = items.size

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

    /**
     * Checks if [position] for methods like [get], [remove], [update]
     * is in a valid range. This way [IndexOutOfBoundsException] should be prevented.
     */
    private fun isPositionValid(position: Int) = position in 0 until itemCount

    /**
     * Sets new [items] and updates UI.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<ItemT>) {
        this.items = items as MutableList<ItemT>
        notifyDataSetChanged()
    }

    /**
     * Returns all [ItemT]s.
     */
    fun getAll() = items as List<ItemT>

    /**
     * Returns [ItemT] at specified [position].
     */
    fun get(position: Int): ItemT? = if (isPositionValid(position)) items[position] else null

    /**
     * Adds [item] in list and updates UI for last position.
     */
    fun add(item: ItemT) = item.let {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    /**
     * Add [items] in list and updates UI for newly added positions.
     */
    fun addAll(items: Collection<ItemT>) {
        this.items.addAll(items)
        notifyItemRangeInserted(itemCount - items.size, items.size)
    }

    /**
     * Adds [item] in list and updates UI at specified [position].
     */
    fun insert(position: Int, item: ItemT) {
        if (position in 0..itemCount) {
            items.add(position, item)
            notifyItemInserted(position)
        }
    }

    /**
     * Updates [item] in list and updates UI at specified [position].
     */
    fun update(position: Int, item: ItemT) {
        if (isPositionValid(position)) {
            items[position] = item
            notifyItemChanged(position)
        }
    }

    /**
     * Removes item from list at [position], updates UI at specified [position],
     * and returns the removed [ItemT].
     */
    fun remove(position: Int): ItemT? {
        var removed: ItemT? = null
        if (isPositionValid(position)) {
            removed = items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
        return removed
    }

    /**
     * Removes all items and updates UI.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * Changes list of items, and updates UI using [DiffUtil].
     */
    fun updateDiffList(new: List<ItemT>, specificCallback: DiffUtil.DiffResult? = null) {
        (specificCallback ?: getDiffCallback(getAll(), new)).also {
            items.clear()
            items.addAll(new)
        }.dispatchUpdatesTo(this)
    }
}
