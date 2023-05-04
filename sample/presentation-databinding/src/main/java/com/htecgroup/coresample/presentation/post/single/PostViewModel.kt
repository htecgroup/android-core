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

package com.htecgroup.coresample.presentation.post.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.htecgroup.core.domain.extension.TAG
import com.htecgroup.core.domain.extension.collect
import com.htecgroup.core.domain.extension.collectResult
import com.htecgroup.core.domain.extension.mapUnwrapResult
import com.htecgroup.core.presentation.viewmodel.State
import com.htecgroup.coresample.domain.post.usecase.DeletePost
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.service.analytics.AnalyticConstants
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.domain.service.analytics.AnalyticsParam
import com.htecgroup.coresample.domain.service.analytics.Event
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val retrievePost: RetrievePost,
    private val deletePost: DeletePost,
    private val analytics: Analytics,
    private val analyticsParam: AnalyticsParam
) : BaseViewModel<PostViewModel.PostActionCode>() {

    enum class PostActionCode {
        CLICK_EDIT_POST,
        CLICK_DELETE_POST
    }

    private val _postLive = MutableLiveData<PostView>()
    val postLive: LiveData<PostView>
        get() = _postLive

    init {
        setStatus(State.LOADING)
    }

    fun initPost(postId: Int) {
        if (_postLive.value == null) {
            viewModelScope.launch {
                retrievePost(postId)
                    .mapUnwrapResult { it?.toPostView() }
                    .collectResult(
                        onSuccess = {
                            it?.let {
                                _postLive.value = it
                                setStatus(State.DATA)
                            } ?: setStatus(State.NO_DATA)
                        },
                        onError = {
                            logger.e(TAG, it)
                            setStatus(State.ERROR)
                        }
                    )
            }
        }
    }

    fun onEditPostClick() {
        postLive.value?.let {
            analytics.logEvent(
                Event.Builder(AnalyticConstants.Event.EDIT_POST)
                    .setIntegerParam(analyticsParam.itemId, it.id)
                    .setStringParam(AnalyticConstants.Param.POST_TITLE, it.title)
                    .build()
            )
            setAction(PostActionCode.CLICK_EDIT_POST, R.string.edit_post)
        }
    }

    fun onDeletePostClick() {
        postLive.value?.let { postView ->
            viewModelScope.launch {
                deletePost(postView.id)
                    .collect(
                        onSuccess = {
                            analytics.logEvent(
                                Event.Builder(AnalyticConstants.Event.POST_DELETED)
                                    .setIntegerParam(analyticsParam.itemId, postView.id)
                                    .setStringParam(
                                        AnalyticConstants.Param.POST_TITLE,
                                        postView.title
                                    )
                                    .build()
                            )
                            setAction(PostActionCode.CLICK_DELETE_POST)
                        },
                        onError = { logger.e(TAG, it) }
                    )
            }
        }
    }
}
