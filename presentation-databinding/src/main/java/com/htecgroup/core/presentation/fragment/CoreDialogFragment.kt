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

package com.htecgroup.core.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htecgroup.core.presentation.ICoreComponent
import com.htecgroup.core.presentation.viewmodel.ViewModelIdProvider

/**
 * Base [AppCompatDialogFragment] class. Main responsibilities are
 * binding layout and providing viewmodels.
 */
@Suppress("unused")
abstract class CoreDialogFragment<BindingT : ViewDataBinding, ViewModelT : ViewModel> :
    AppCompatDialogFragment(), ICoreComponent<BindingT, ViewModelT> {

    protected lateinit var binding: BindingT
        private set

    protected lateinit var viewModel: ViewModelT
        private set

    override val isViewModelInitialized: Boolean get() = ::viewModel.isInitialized

    override val isBindingInitialized: Boolean get() = ::binding.isInitialized

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(inflater, container)
        return this.binding.root
    }

    /**
     * Initializes [viewModel] and [binding].
     */
    protected open fun init(inflater: LayoutInflater, container: ViewGroup?) {
        this.viewModel = provideViewModel()
        this.binding = provideDataBinding(inflater, container)
        setDataBinding()
    }

    override fun provideViewModel() = ViewModelProvider(this)[this.provideViewModelClass()]

    override fun provideDataBinding(inflater: LayoutInflater?, container: ViewGroup?): BindingT =
        DataBindingUtil.inflate(inflater!!, this.provideLayoutId(), container, false)

    /**
     * Connects Fragment's lifecycle and [binding], and provides [viewModel] to the [binding].
     */
    protected open fun setDataBinding() {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            setVariable(ViewModelIdProvider.viewModelId, viewModel)
        }
    }

    override fun dismiss() {
        try {
            super.dismissAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.e(CoreDialogFragment::class.java.simpleName, e.localizedMessage, e)
        }
    }
}
