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

package com.htecgroup.coresample.presentation.post.edit

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.htecgroup.androidcore.presentation.extension.observe
import com.htecgroup.coresample.domain.service.analytics.AnalyticConstants
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.domain.service.analytics.AnalyticsParam
import com.htecgroup.coresample.domain.service.analytics.Event
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseToolbarFragment
import com.htecgroup.coresample.presentation.databinding.FragmentEditPostBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditPostFragment :
    BaseToolbarFragment<FragmentEditPostBinding, EditPostViewModel, EditPostRoutes>() {

    private val args: EditPostFragmentArgs by navArgs()

    @Inject
    lateinit var analytics: Analytics

    @Inject
    lateinit var analyticsParam: AnalyticsParam

    public override fun provideLayoutId() = R.layout.fragment_edit_post

    public override fun provideViewModelClass() = EditPostViewModel::class.java

    override fun provideToolbarTitleId(): Int = R.string.title_edit_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initPost(args.postView.id)
        observeAction()
    }

    override fun onResume() {
        super.onResume()
        analytics.setCurrentScreen(
            requireActivity(),
            EditPostFragment::class.java.simpleName,
            EditPostFragment::class.java.simpleName
        )
    }

    private fun observeAction() {
        observe(viewModel.getAction()) {
            if (it.code == EditPostViewModel.EditPostActionCode.FINISH) {
                logAnalyticsEvent()
                navigation?.navigateBack()
            }
        }
    }

    private fun logAnalyticsEvent() {
        analytics.logEvent(
            Event.Builder(AnalyticConstants.Event.POST_EDITED)
                .setIntegerParam(analyticsParam.itemId, args.postView.id)
                .build()
        )
    }
}
