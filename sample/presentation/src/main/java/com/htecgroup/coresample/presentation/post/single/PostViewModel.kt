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

import androidx.lifecycle.viewModelScope
import com.htecgroup.androidcore.domain.extension.TAG
import com.htecgroup.androidcore.domain.extension.collect
import com.htecgroup.androidcore.domain.extension.mapUnwrapResult
import com.htecgroup.androidcore.presentation.model.DataUiState.Loading
import com.htecgroup.androidcore.presentation.viewmodel.AssistedViewModelFactory
import com.htecgroup.coresample.domain.post.usecase.DeletePost
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.presentation.base.AssistedBaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PostViewModel.Factory::class)
class PostViewModel @AssistedInject constructor(
    private val retrievePost: RetrievePost,
    private val deletePost: DeletePost,
    @Assisted navKey: PostDetailsDestination,
) : AssistedBaseViewModel<PostDetailsDestination, PostView>(navKey) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<PostDetailsDestination, PostViewModel>

    init {
        uiState = Loading()
        initPost(navKey.id)
    }

    private fun initPost(postId: Int) {
        if (uiState.data == null) {
            viewModelScope.launch {
                retrievePost.invoke(postId)
                    .mapUnwrapResult { it?.toPostView() ?: PostView(shouldDelete = true) }
                    .collectToUiState()
            }
        }
    }

    fun onEditPostClick() {
        uiState = uiState.toIdle { it?.copy(shouldEdit = true) }
    }

    fun onDeletePostClick() {
        uiState.data?.let { postView ->
            viewModelScope.launch {
                deletePost.invoke(postView.id)
                    .collect(
                        onSuccess = {
                            uiState = uiState.toIdle { it?.copy(shouldDelete = true) }
                        },
                        onError = { logger.e(TAG, it) }
                    )
            }
        }
    }

    override fun clearState() {
        uiState = uiState.toIdle { it?.copy(shouldEdit = false, shouldDelete = false) }
    }
}
