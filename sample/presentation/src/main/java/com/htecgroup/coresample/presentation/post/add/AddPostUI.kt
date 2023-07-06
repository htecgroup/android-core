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

package com.htecgroup.coresample.presentation.post.add

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.htecgroup.coresample.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    title: MutableState<String>,
    body: MutableState<String>,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.list_item_padding)),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title.value,
            maxLines = 1,
            onValueChange = { title.value = it },
            label = { Text(text = stringResource(id = R.string.title)) }
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_default)),
            value = body.value,
            maxLines = 1,
            onValueChange = { body.value = it },
            label = { Text(text = stringResource(id = R.string.body)) }
        )

        AnimatedVisibility(
            visible = body.value.isNotEmpty(),
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_default)),
                    onClick = onSaveClick
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun AddPostScreenPreview() {
    AddPostScreen(
        title = mutableStateOf("Title"),
        body = mutableStateOf("Body"),
        onSaveClick = {}
    )
}
