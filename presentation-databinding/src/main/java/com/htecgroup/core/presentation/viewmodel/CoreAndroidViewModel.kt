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

package com.htecgroup.core.presentation.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base [ViewModel] class with [Application] context,
 * where [ActionCodeT] is enum representing actions.
 */
@Suppress("unused")
abstract class CoreAndroidViewModel<ActionCodeT>(application: Application) :
    ICoreViewModel<ActionCodeT>, AndroidViewModel(application) {

    private var state = State.LOADING
    private val action by lazy { SingleLiveEvent<Action<ActionCodeT>>() }
    private val status by lazy { MutableLiveData(Status(isIdle = true)) }

    override fun getAction(): LiveData<Action<ActionCodeT>> = action

    @JvmOverloads
    fun setAction(code: ActionCodeT, @StringRes message: Int = 0) {
        this.action.postValue(Action(code, message))
    }

    override fun getStatus(): LiveData<Status> = status

    fun setStatus(status: Status) {
        this.status.postValue(status)
    }

    override fun setStatus(@State state: Int) {
        synchronized(this) {
            when (state) {
                State.IDLE -> status.postValue(Status(isIdle = true))
                State.LOADING -> status.postValue(Status(isLoading = true))
                State.NO_DATA -> status.postValue(Status(noData = true))
                State.DATA -> status.postValue(Status(hasData = true))
                State.NO_INTERNET -> status.postValue(Status(noInternet = true))
                State.ERROR -> status.postValue(Status(isError = true))
            }
        }
    }

    override fun removeStatus(state: Int) {
        synchronized(this) {
            status.value?.run {
                when (state) {
                    State.IDLE -> status.postValue(copy(isIdle = false))
                    State.LOADING -> status.postValue(copy(isLoading = false))
                    State.NO_DATA -> status.postValue(copy(noData = false))
                    State.DATA -> status.postValue(copy(hasData = false))
                    State.NO_INTERNET -> status.postValue(copy(noInternet = false))
                    State.ERROR -> status.postValue(copy(isError = false))
                }
            }
        }
    }
}
