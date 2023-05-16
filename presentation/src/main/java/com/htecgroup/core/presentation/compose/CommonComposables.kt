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

package com.htecgroup.core.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AnimateSlide(
    visible: Boolean,
    reverse: Int = 1,
    targetOffsetX: Int = 4,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally { -1 * reverse * it / targetOffsetX },
        exit = slideOutHorizontally { reverse * it / targetOffsetX },
        content = content
    )
}

@Composable
fun AnimateFade(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        content = content
    )
}

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    titleResId: Int,
    topAppBarColors: TopAppBarColors,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    upButton: State<BarButton?>? = null,
    contextButton: State<BarButton?>? = null
) {
    DefaultTopBar(
        title = stringResource(id = titleResId),
        topAppBarColors = topAppBarColors,
        modifier = modifier,
        textAlign = textAlign,
        titleTextStyle = titleTextStyle,
        upButton = upButton,
        contextButton = contextButton
    )
}


@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: String,
    topAppBarColors: TopAppBarColors,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    upButton: State<BarButton?>? = null,
    contextButton: State<BarButton?>? = null
) {
    TopAppBar(
        modifier = modifier,
        colors = topAppBarColors,
        navigationIcon = { upButton?.let { Up(it) } },
        actions = {
            contextButton?.value?.run {
                IconButton(onClick = action) {
                    Icon(
                        icon,
                        contentDescription,
                    )
                }
            }
        },
        title = {
            Text(
                modifier = modifier.then(Modifier.fillMaxWidth()),
                text = title,
                textAlign = textAlign,
                style = titleTextStyle,
                fontWeight = FontWeight.Medium
            )
        }
    )
}

@Composable
fun Up(topBarButton: State<BarButton?>) {
    val up by topBarButton
    up?.let {
        IconButton(it.action) {
            Icon(
                imageVector = it.icon,
                contentDescription = it.contentDescription
            )
        }
    }
}
