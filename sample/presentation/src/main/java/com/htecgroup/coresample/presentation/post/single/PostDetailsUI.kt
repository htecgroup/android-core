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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.htecgroup.core.presentation.model.DataUiState
import com.htecgroup.core.presentation.model.DataUiState.Idle
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostView

@Composable
fun PostDetailsScreen(
    uiState: DataUiState<PostView>,
    onPostEdit: () -> Unit,
    onPostDelete: () -> Unit,
    onEdited: () -> Unit,
    onDeleted: () -> Unit
) {
    PostDetailsActions(uiState = uiState, onEdited, onDeleted)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.list_item_padding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val postDetails = uiState.data

        postDetails?.let { post ->
            PostDetails(post, onPostEdit, onPostDelete)
        }
    }
}

@Composable
private fun PostDetailsActions(
    uiState: DataUiState<PostView>,
    onEdited: () -> Unit,
    onDeleted: () -> Unit
) {
    if (uiState.data?.shouldDelete == true) {
        onDeleted()
        return
    }
    if (uiState.data?.shouldEdit == true) {
        onEdited()
        return
    }
}

@Composable
private fun PostDetails(
    post: PostView,
    onPostEdit: () -> Unit,
    onPostDelete: () -> Unit
) {
    Text(
        post.id.toString(),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))
    )
    Text(
        text = post.title,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))
    )
    Text(
        post.description,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))
    )
    Text(
        post.user?.name ?: stringResource(id = R.string.unknown_author),
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_default))
    )
    post.user?.email?.let { email ->
        Text(email)
    }

    Button(
        onClick = onPostEdit,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_default))
    ) {
        Text(text = stringResource(id = R.string.edit_post))
    }
    Button(
        onClick = onPostDelete,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_default))
    ) {
        Text(text = stringResource(id = R.string.delete_post))
    }
}

@Preview
@Composable
private fun PostDetailsScreenPreview() {
    PostDetailsScreen(
        uiState = Idle(PostView()),
        onPostEdit = {},
        onPostDelete = {},
        onEdited = {},
        onDeleted = {}
    )
}
