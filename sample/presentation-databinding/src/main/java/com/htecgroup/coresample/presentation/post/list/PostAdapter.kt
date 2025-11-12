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

import com.htecgroup.androidcore.presentation.adapter.CoreAdapter
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostView
import javax.inject.Inject

/**
 * Commented code is example of different approach
 *
 */
class PostAdapter @Inject constructor() : CoreAdapter<PostView>() {
    // BaseDiffAdapter<PostView>

    var listener: ((PostView) -> Unit)? = null

	/*@Inject
	public PostAdapter() {
		super(new DiffUtil.ItemCallback<PostView>() {
			@Override
			public boolean areItemsTheSame(PostView oldItem, PostView newItem) {
				return oldItem.getId().equals(newItem.getId());
			}

			@Override
			public boolean areContentsTheSame(PostView oldItem, PostView newItem) {
				return oldItem.compareTo(newItem) == 0;
			}
		});
	}*/

    override fun provideLayoutId(position: Int) = R.layout.item_post

    override fun provideViewModel(position: Int) =
        PostItemViewModel(get(position) ?: PostView(), listener)
}
