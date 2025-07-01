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
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.htecgroup.androidcore.domain.extension.mapUnwrapResult
import com.htecgroup.androidcore.presentation.model.DataUiState.Loading
import com.htecgroup.androidcore.presentation.viewmodel.AssistedViewModelFactory
import com.htecgroup.coresample.domain.post.usecase.RetrievePost
import com.htecgroup.coresample.domain.post.usecase.UpdatePost
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.AssistedBaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPost
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditPostViewModel.Factory::class)
class EditPostViewModel @AssistedInject constructor(
    private val retrievePost: RetrievePost,
    private val updatePost: UpdatePost,
    private val resources: Resources,
    @Assisted navKey: EditPostDestination
) : AssistedBaseViewModel<EditPostDestination, PostView>(navKey) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<EditPostDestination, EditPostViewModel>

    val postTitle = mutableStateOf("")
    val postBody = mutableStateOf("")

    init {
        initPost(navKey.id)
    }

    private fun initPost(postId: Int) {
        uiState = Loading()
        viewModelScope.launch {
            retrievePost.invoke(postId)
                .mapUnwrapResult { it?.toPostView() }
                .mapUnwrapResult {
                    postTitle.value = it?.title ?: ""
                    postBody.value = it?.description ?: ""
                    it
                }
                .take(1)
                .collectToUiState()
        }
    }

    fun onSaveClick() {
        uiState = uiState.toLoading()
        uiState.data?.run {
            title = postTitle.value.ifEmpty { resources.getString(R.string.no_title) }
            description = postBody.value.ifEmpty { resources.getString(R.string.no_description) }

            viewModelScope.launch {
                updatePost.invoke(toPost())
                    .map {
                        changesSaved = true
                        this@run
                    }
                    .collectToUiState()
            }
        }
    }
}
