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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htecgroup.core.presentation.ICoreComponent
import com.htecgroup.core.presentation.routes.IRoutesFactory
import com.htecgroup.core.presentation.routes.Routes
import com.htecgroup.core.presentation.viewmodel.ViewModelIdProvider
import javax.inject.Inject

/**
 * Base [Fragment] class. Main responsibilities are binding layout and providing viewmodels.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class CoreFragment<BindingT : ViewDataBinding, ViewModelT : ViewModel, RouteT : Routes> :
    Fragment(), ICoreComponent<BindingT, ViewModelT> {

    @Inject lateinit var routesFactory: IRoutesFactory

    protected lateinit var binding: BindingT
        private set

    protected lateinit var viewModel: ViewModelT
        private set

    lateinit var localView: View

    protected var navigation: RouteT? = null

    override val isViewModelInitialized: Boolean get() = ::viewModel.isInitialized

    override val isBindingInitialized: Boolean get() = ::binding.isInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation = getRoute()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(inflater, container)
        localView = binding.root
        return localView
    }

    /**
     * Initializes [viewModel] and [binding].
     */
    protected open fun init(inflater: LayoutInflater, container: ViewGroup?) {
        viewModel = provideViewModel()
        binding = provideDataBinding(inflater, container)
        setDataBinding()
    }

    override fun provideDataBinding(inflater: LayoutInflater?, container: ViewGroup?): BindingT =
        if (::localView.isInitialized) {
            DataBindingUtil.getBinding(localView)!!
        } else {
            DataBindingUtil.inflate(inflater!!, provideLayoutId(), container, false)
        }

    /**
     * Connects Fragment's lifecycle and [binding], and provides [viewModel] to the [binding].
     */
    protected open fun setDataBinding() {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            setVariable(ViewModelIdProvider.viewModelId, viewModel)
        }
    }

    override fun provideViewModel() = if (useActivityViewModel()) {
        ViewModelProvider(requireActivity())[provideViewModelClass()]
    } else {
        ViewModelProvider(this)[provideViewModelClass()]
    }

    /**
     * Override this method in a subclass and return true to use Activity's viewmodel.
     */
    protected open fun useActivityViewModel(): Boolean = false

    /**
     * Returns [RouteT] which is used for navigation.
     */
    @Suppress("UNCHECKED_CAST")
    fun getRoute() = routesFactory.get(this::class.java) as? RouteT?

    override fun onPause() {
        super.onPause()
        if (isRemoving) {
            binding.unbind()
        }
    }
}
