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

package com.htecgroup.coresample.presentation.di

import com.htecgroup.core.presentation.routes.IRoutesFactory
import com.htecgroup.core.presentation.routes.RouteKey
import com.htecgroup.core.presentation.routes.Routes
import com.htecgroup.core.presentation.routes.RoutesFactory
import com.htecgroup.coresample.presentation.post.add.AddPostFragment
import com.htecgroup.coresample.presentation.post.add.AddPostRoutes
import com.htecgroup.coresample.presentation.post.edit.EditPostFragment
import com.htecgroup.coresample.presentation.post.edit.EditPostRoutes
import com.htecgroup.coresample.presentation.post.list.PostsFragment
import com.htecgroup.coresample.presentation.post.list.PostsRoutes
import com.htecgroup.coresample.presentation.post.single.PostDetailsFragment
import com.htecgroup.coresample.presentation.post.single.PostDetailsRoutes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class PostsRoutesNavigationProvider {

    @Binds
    internal abstract fun bindFactory(routesModelFactory: RoutesFactory): IRoutesFactory

    @Binds
    @IntoMap
    @RouteKey(PostsFragment::class)
    internal abstract fun bindPostsRoutes(route: PostsRoutes): Routes

    @Binds
    @IntoMap
    @RouteKey(AddPostFragment::class)
    internal abstract fun bindAddPostRoutes(route: AddPostRoutes): Routes

    @Binds
    @IntoMap
    @RouteKey(PostDetailsFragment::class)
    internal abstract fun bindPostDetailsRoutes(route: PostDetailsRoutes): Routes

    @Binds
    @IntoMap
    @RouteKey(EditPostFragment::class)
    internal abstract fun bindEditPostRoutes(route: EditPostRoutes): Routes
}
