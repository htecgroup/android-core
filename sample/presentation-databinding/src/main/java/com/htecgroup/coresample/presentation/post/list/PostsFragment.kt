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

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.htecgroup.core.presentation.extension.observe
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseToolbarFragment
import com.htecgroup.coresample.presentation.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : BaseToolbarFragment<FragmentPostsBinding, PostsViewModel, PostsRoutes>() {

    @Inject
    lateinit var adapter: PostAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_posts

    override fun provideViewModelClass(): Class<PostsViewModel> = PostsViewModel::class.java

    override fun provideToolbarTitleId(): Int = R.string.title_posts_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        initOptionsMenu()
        initList()
        observeData()
    }

    private fun initOptionsMenu() {
        activity?.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                    menuInflater.inflate(R.menu.activity_posts, menu)

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                    when (menuItem.itemId) {
                        R.id.item_remove -> {
                            viewModel.deleteAllPosts()
                            true
                        }
                        R.id.item_add -> {
                            navigation?.navigateToAddPost()
                            true
                        }
                        else -> false
                    }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    private fun initList() {
        adapter.listener = { navigation?.navigateToPostDetails(it) }
        binding.recView.adapter = adapter
    }

    private fun observeData() {
        observe(viewModel.getStatus()) { binding.swipeRefresh.isRefreshing = it.isLoading() }
    }
}
