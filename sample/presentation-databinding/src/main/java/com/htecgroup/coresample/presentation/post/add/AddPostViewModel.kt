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

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.htecgroup.androidcore.domain.extension.TAG
import com.htecgroup.androidcore.domain.extension.collect
import com.htecgroup.coresample.domain.post.Post
import com.htecgroup.coresample.domain.post.usecase.AddPost
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val addPost: AddPost,
    private val resources: Resources
) : BaseViewModel<AddPostViewModel.AddPostActionCode>() {

    enum class AddPostActionCode {
        FINISH
    }

    val titleLive = MutableLiveData("")
    val bodyLive = MutableLiveData("")

    fun onSaveClick() {
        viewModelScope.launch {
            addPost(
                Post(
                    0,
                    titleLive.value ?: resources.getString(R.string.no_title),
                    bodyLive.value ?: resources.getString(R.string.no_body)
                )
            ).collect(
                onSuccess = { setAction(AddPostActionCode.FINISH) },
                onError = { logger.e(TAG, it) }
            )
        }
    }
}
