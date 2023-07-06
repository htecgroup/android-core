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

package com.htecgroup.coresample.presentation.post

import android.os.Parcelable
import com.htecgroup.coresample.domain.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserView(
    val id: Int = 0,
    val name: String = "",
    val email: String = ""
) : Parcelable

fun User.toUserView() =
    UserView(
        id = id,
        name = name,
        email = email
    )
