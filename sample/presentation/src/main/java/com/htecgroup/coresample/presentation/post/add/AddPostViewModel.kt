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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.htecgroup.androidcore.domain.extension.TAG
import com.htecgroup.androidcore.domain.extension.collect
import com.htecgroup.androidcore.presentation.model.DataUiState.Idle
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
) : BaseViewModel<Post>() {

    val postTitle: MutableState<String> = mutableStateOf("")
    val postBody: MutableState<String> = mutableStateOf("")

    init {
        uiState = uiState.toIdle(Post())
    }

    fun onSaveClick() {
        viewModelScope.launch {
            val post = Post(
                id = 0,
                title = postTitle.value.ifEmpty { resources.getString(R.string.no_title) },
                description = postBody.value.ifEmpty { resources.getString(R.string.no_body) }
            )
            addPost.invoke(post).collect(
                onSuccess = { uiState = Idle() },
                onError = { logger.e(TAG, it) }
            )
        }
    }

    override fun clearState() {
        uiState = uiState.toIdle(Post())
    }
}
