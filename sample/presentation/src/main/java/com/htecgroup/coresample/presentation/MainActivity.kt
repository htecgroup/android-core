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

package com.htecgroup.coresample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.htecgroup.androidcore.presentation.compose.CoreNavigableContent
import com.htecgroup.androidcore.presentation.compose.navigation.persistance.NavPersistence
import com.htecgroup.androidcore.presentation.model.BottomBarEntry
import com.htecgroup.coresample.presentation.post.add.addPostEntry
import com.htecgroup.coresample.presentation.post.knownDestinations
import com.htecgroup.coresample.presentation.post.list.postListEntry
import com.htecgroup.coresample.presentation.theme.AppTheme
import com.htecgroup.coresample.presentation.welcome.welcomeEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomTabs = listOf(
                BottomBarEntry(
                    label = stringResource(R.string.title_posts_list),
                    icon = Icons.AutoMirrored.Filled.List,
                    entry = postListEntry
                ),
                BottomBarEntry(
                    label = stringResource(R.string.title_add_post),
                    icon = Icons.Filled.Add,
                    entry = addPostEntry
                )
            )

            CoreNavigableContent(
                modifier = Modifier.fillMaxSize(),
                theme = { AppTheme(content = it) },
                startNavEntry = welcomeEntry,
                bottomTabs = bottomTabs,
                persistence = NavPersistence(knownDestinations)
            )
        }
    }
}
