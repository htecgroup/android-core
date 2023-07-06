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

import com.htecgroup.androidcore.test.CoreTest
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.post.PostView
import io.mockk.every
import io.mockk.spyk
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

/**
 * Test for [PostAdapter]
 */
class PostAdapterTest : CoreTest() {

    private val adapter = spyk(PostAdapter())

    @Test
    fun provideLayoutId() {
        adapter.provideLayoutId(0).shouldBeEqualTo(R.layout.item_post)
    }

    @Test
    fun provideViewModel() {
        every { adapter.get(any()) } returns PostView(0, "", "", null)

        adapter.provideViewModel(0).shouldNotBeNull()
    }
}
