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

package com.htecgroup.androidcore.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htecgroup.androidcore.presentation.viewmodel.ViewModelIdProvider

/**
 * Base [AppCompatActivity] class. Main responsibilities are
 * binding layout and providing viewmodels.
 */
abstract class CoreActivity<BindingT : ViewDataBinding, ViewModelT : ViewModel> :
    AppCompatActivity(), ICoreComponent<BindingT, ViewModelT> {

    protected lateinit var binding: BindingT
        private set

    protected lateinit var viewModel: ViewModelT

    override val isViewModelInitialized: Boolean get() = ::viewModel.isInitialized

    override val isBindingInitialized: Boolean get() = ::binding.isInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
    }

    /**
     * Initializes [viewModel] and [binding].
     */
    protected open fun initActivity() {
        viewModel = provideViewModel()
        binding = provideDataBinding()
        setDataBinding()
    }

    override fun provideViewModel() = ViewModelProvider(this)[provideViewModelClass()]

    override fun provideDataBinding(inflater: LayoutInflater?, container: ViewGroup?): BindingT =
        DataBindingUtil.setContentView(this, provideLayoutId())

    /**
     * Connects Activity's lifecycle and [binding], and provides [viewModel] to the [binding].
     */
    protected open fun setDataBinding() {
        binding.lifecycleOwner = this
        binding.setVariable(ViewModelIdProvider.viewModelId, viewModel)
    }
}
