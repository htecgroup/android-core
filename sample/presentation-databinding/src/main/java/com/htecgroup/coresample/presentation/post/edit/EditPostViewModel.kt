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

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.htecgroup.core.domain.extension.TAG
import com.htecgroup.core.domain.extension.collect
import com.htecgroup.core.domain.extension.collectResult
import com.htecgroup.core.domain.extension.mapUnwrapResult
import com.htecgroup.core.presentation.viewmodel.State
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.post.usecase.UpdatePost
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPost
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val retrievePost: RetrievePost,
    private val updatePost: UpdatePost,
    private val resources: Resources,
    private val analytics: Analytics
) : BaseViewModel<EditPostViewModel.EditPostActionCode>() {

    enum class EditPostActionCode {
        FINISH
    }

    private var postView: PostView? = null

    val titleLive = MutableLiveData<String>()
    val bodyLive = MutableLiveData<String>()

    fun initPost(postId: Int) {
        viewModelScope.launch {
            retrievePost(postId)
                .mapUnwrapResult { it?.toPostView() }
                .collectResult(
                    onSuccess = {
                        it?.let {
                            postView = it
                            titleLive.postValue(postView?.title)
                            bodyLive.postValue(postView?.description)
                            setStatus(State.DATA)
                        } ?: setStatus(State.NO_DATA)
                    }
                )
        }
    }

    fun onSaveClick() {
        postView?.run {
            title = titleLive.value ?: resources.getString(R.string.no_title)
            description = bodyLive.value ?: resources.getString(R.string.no_description)

            viewModelScope.launch {
                updatePost(toPost())
                    .collect(
                        onSuccess = { setAction(EditPostActionCode.FINISH) },
                        onError = { logger.e(TAG, it) }
                    )
            }
        }
    }
}
