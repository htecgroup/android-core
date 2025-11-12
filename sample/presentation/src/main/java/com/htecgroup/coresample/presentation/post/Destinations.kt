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

import com.htecgroup.androidcore.presentation.compose.navigation.persistance.PersistableNavDestination
import com.htecgroup.coresample.presentation.post.add.AddPostDestination
import com.htecgroup.coresample.presentation.post.edit.EditPostDestination
import com.htecgroup.coresample.presentation.post.list.PostListDestination
import com.htecgroup.coresample.presentation.post.single.PostDetailsDestination
import com.htecgroup.coresample.presentation.welcome.WelcomeDestination

val knownDestinations = mapOf<String, (Map<String, String>) -> PersistableNavDestination>(
    WelcomeDestination.navKeyId to { WelcomeDestination },
    PostListDestination.navKeyId to { PostListDestination },
    AddPostDestination.navKeyId to { AddPostDestination },
    PostDetailsDestination.NAV_KEY_ID to PostDetailsDestination.Companion::fromParams,
    EditPostDestination.NAV_KEY_ID to EditPostDestination.Companion::fromParams
)
