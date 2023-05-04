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

package com.htecgroup.coresample.presentation.post.list

import androidx.lifecycle.viewModelScope
import com.htecgroup.core.domain.extension.TAG
import com.htecgroup.core.domain.extension.collect
import com.htecgroup.core.domain.extension.mapUnwrapListResult
import com.htecgroup.coresample.domain.post.usecase.FetchPosts
import com.htecgroup.coresample.domain.post.usecase.RetrievePosts
import com.htecgroup.coresample.presentation.base.BaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val retrievePosts: RetrievePosts,
    private val fetchPosts: FetchPosts
) : BaseViewModel<List<PostView>>() {

    init {
        subscribeToPostsFlow()
    }

    private fun subscribeToPostsFlow() {
        viewModelScope.launch {
            retrievePosts.invoke()
                .mapUnwrapListResult { it.toPostView() }
                .collectToUiState()
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            uiState = uiState.toLoading()
            fetchPosts.invoke()
                .collect(
                    onSuccess = {
                        logger.i(TAG, "Posts updated")
                    },
                    onError = {
                        uiState = uiState.toError(errorMessage = it?.message)
                        logger.e(TAG, it)
                    }
                )
        }
    }
}
