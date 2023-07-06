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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.htecgroup.androidcore.domain.extension.TAG
import com.htecgroup.androidcore.domain.extension.collect
import com.htecgroup.androidcore.domain.extension.collectResult
import com.htecgroup.androidcore.domain.extension.mapUnwrapListResult
import com.htecgroup.androidcore.presentation.viewmodel.State
import com.htecgroup.coresample.domain.post.usecase.DeletePosts
import com.htecgroup.coresample.domain.post.usecase.FetchPosts
import com.htecgroup.coresample.domain.post.usecase.RetrievePosts
import com.htecgroup.coresample.domain.user.DeleteUsers
import com.htecgroup.coresample.presentation.base.BaseViewModel
import com.htecgroup.coresample.presentation.post.PostView
import com.htecgroup.coresample.presentation.post.toPostView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val retrievePosts: RetrievePosts,
    private val deletePosts: DeletePosts,
    private val fetchPosts: FetchPosts,
    private val deleteUsers: DeleteUsers
) : BaseViewModel<Unit>() {

    private val _postListLive = MutableLiveData<List<PostView>>()
    val postListLive: LiveData<List<PostView>>
        get() = _postListLive

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            setStatus(State.LOADING)
            retrievePosts()
                .mapUnwrapListResult { it.toPostView() }
                .collectResult(
                    onSuccess = {
                        if (it?.isNotEmpty() == true) {
                            _postListLive.value = it
                            setStatus(State.DATA)
                        } else {
                            setStatus(State.NO_DATA)
                        }
                    },
                    onError = {
                        logger.e(TAG, it)
                        setStatus(State.ERROR)
                    }
                )
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            deletePosts()
                .collect(
                    onSuccess = { _postListLive.postValue(listOf()) },
                    onError = { logger.e(TAG, it) }
                )
            deleteUsers()
                .collect(
                    onSuccess = { logger.i(TAG, "Users deleted") },
                    onError = { logger.e(TAG, it) }
                )
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            setStatus(State.LOADING)
            fetchPosts()
                .collect(
                    onSuccess = {
                        removeStatus(State.LOADING)
                        logger.i(TAG, "Posts updated")
                    },
                    onError = {
                        setStatus(State.ERROR)
                        logger.e(TAG, it)
                    }
                )
        }
    }
}
