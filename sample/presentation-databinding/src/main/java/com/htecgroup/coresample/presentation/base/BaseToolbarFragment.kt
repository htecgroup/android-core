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

package com.htecgroup.coresample.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.htecgroup.androidcore.presentation.routes.Routes
import com.htecgroup.coresample.presentation.R

abstract class BaseToolbarFragment<BindingT : ViewDataBinding, ViewModelT : ViewModel, Route : Routes> :
    BaseFragment<BindingT, ViewModelT, Route>() {

    protected var supportActionBar: ActionBar? = null

    /**
     * Provide Toolbar title to be set.
     *
     * @return title res id.
     */
    @StringRes
    protected abstract fun provideToolbarTitleId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
    }

    open fun setUpToolbar() {
        binding.root.findViewById<Toolbar>(R.id.toolbar)?.let {
            (activity as? AppCompatActivity)?.run {
                configureActionBar(this, it)
                provideToolbarTitleId()
                    .takeIf { titleId -> titleId != 0 }
                    ?.let { titleRes ->
                        (it.getChildAt(0) as? TextView)?.setText(titleRes)
                    }
            }
        }
    }

    open fun configureActionBar(activity: AppCompatActivity, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            supportActionBar = this
        }
    }
}
