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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base [ViewModel] class for recyclerview adapter [ItemT]s.
 */
@Suppress("unused")
abstract class CoreItemViewModel<ItemT>(
    item: ItemT,
    private val listener: ((ItemT) -> Unit)? = null
) : CoreViewModel<Unit>() {

    private val itemLive = MutableLiveData(item)

    fun getItemLive() = itemLive as LiveData<ItemT>

    fun setItemLive(item: ItemT) {
        itemLive.value = item
    }

    open fun onItemClick() {
        listener?.invoke(getItemLive().value!!)
    }
}
