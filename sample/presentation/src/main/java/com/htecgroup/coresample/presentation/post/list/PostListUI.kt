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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.htecgroup.androidcore.presentation.model.DataUiState
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostView

@Composable
fun PostListScreen(
    uiState: DataUiState<List<PostView>>,
    onRefresh: () -> Unit,
    onClickItem: (PostView) -> Unit
) {
    SwipeRefreshPostList(
        state = uiState,
        onClickItem = onClickItem,
        onRefresh = onRefresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeRefreshPostList(
    state: DataUiState<List<PostView>>,
    onRefresh: () -> Unit,
    onClickItem: (PostView) -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }
    if (!state.isLoading && refreshState.isRefreshing) {
        refreshState.endRefresh()
    }
    state.data?.let { posts ->
        Box(modifier = Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
            LazyColumn(state = rememberLazyListState()) {
                itemsIndexed(items = posts) { index, item ->
                    if (index > 0) {
                        HorizontalDivider(
                            thickness = dimensionResource(id = R.dimen.divider)
                        )
                    }
                    PostItem(item, onClickItem)
                }
            }
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = refreshState
            )
        }
    }
}

@Composable
private fun PostItem(item: PostView, onClick: ((PostView) -> Unit)? = null) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.list_item_padding))
            .clickable {
                onClick?.invoke(item)
            }
    ) {
        Text(
            text = item.title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = item.description, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Preview
@Composable
fun PostsPreview() {
    MaterialTheme {
        val item = PostView(0, "Example Title1", "Description1")

        PostItem(item)
    }
}
