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

import android.os.Bundle
import android.preference.PreferenceManager
import com.htecgroup.coresample.domain.service.analytics.AnalyticConstants
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.presentation.R
import com.htecgroup.coresample.presentation.base.BaseActivity
import com.htecgroup.coresample.presentation.databinding.ActivityPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

private const val KEY_PROGRAMMING_LANGUAGE = "preferred_programming_language"
private const val PL_BOUND = 4

@AndroidEntryPoint
class PostsActivity : BaseActivity<ActivityPostsBinding, PostsMainViewModel>() {

    @Inject
    lateinit var navigation: PostsMainRoutes

    // For concrete projects, best practice is to inject this only once in BaseActivity
    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPreferredProgrammingLanguage()
    }

    public override fun provideLayoutId() = R.layout.activity_posts

    public override fun provideViewModelClass() = PostsMainViewModel::class.java

    override fun onSupportNavigateUp(): Boolean = navigation.navController.navigateUp()

    private fun setPreferredProgrammingLanguage() {
        var programmingLanguage = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(KEY_PROGRAMMING_LANGUAGE, null)
        if (programmingLanguage == null) {
            // show dialog on first-time opening and get user answer
            val languages = arrayOf("Java", "Kotlin", "C#", "C", "Swift")
            programmingLanguage = languages[Random().nextInt(PL_BOUND)]
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(KEY_PROGRAMMING_LANGUAGE, programmingLanguage)
                .apply()
            analytics.setUserProperty(
                AnalyticConstants.UserProperty.PROGRAMMING_LANGUAGE,
                programmingLanguage
            )
        }
    }
}
