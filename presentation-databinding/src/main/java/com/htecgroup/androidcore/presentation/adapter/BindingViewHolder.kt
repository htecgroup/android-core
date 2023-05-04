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

package com.htecgroup.androidcore.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.htecgroup.androidcore.presentation.viewmodel.ViewModelIdProvider

/**
 * Base [RecyclerView.ViewHolder] class which provides
 * automated view databinding for adapter items.
 */
class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewModel(viewModel: ViewModel) {
        binding.apply {
            setVariable(ViewModelIdProvider.viewModelId, viewModel)
            executePendingBindings()
        }
    }
}
