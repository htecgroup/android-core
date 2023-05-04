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

package com.htecgroup.coresample.presentation.post.add

import android.os.Bundle
import android.view.View
import com.htecgroup.core.presentation.extension.observe
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseToolbarFragment
import com.htecgroup.coresample.presentation.databinding.FragmentAddPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment :
    BaseToolbarFragment<FragmentAddPostBinding, AddPostViewModel, AddPostRoutes>() {

    override fun provideLayoutId(): Int = R.layout.fragment_add_post

    override fun provideViewModelClass(): Class<AddPostViewModel> = AddPostViewModel::class.java

    override fun provideToolbarTitleId(): Int = R.string.title_add_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAction()
    }

    private fun observeAction() {
        observe(viewModel.getAction()) {
            if (it.code == AddPostViewModel.AddPostActionCode.FINISH) {
                navigation?.navigateBack()
            }
        }
    }
}
