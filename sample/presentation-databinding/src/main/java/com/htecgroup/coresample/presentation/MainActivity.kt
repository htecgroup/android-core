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
import android.preference.PreferenceManager
import androidx.navigation.fragment.NavHostFragment
import com.htecgroup.coresample.domain.service.analytics.AnalyticConstants
import com.htecgroup.coresample.domain.service.analytics.Analytics
import com.htecgroup.coresample.presentation.base.BaseActivity
import com.htecgroup.coresample.presentation.databinding.ActivityMainBinding
import com.htecgroup.coresample.presentation.welcome.WelcomeRoutes
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject

private const val KEY_PROGRAMMING_LANGUAGE = "preferred_programming_language"
private const val PL_BOUND = 4

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var navigation: WelcomeRoutes

    // For concrete projects, best practice is to inject this only once in BaseActivity
    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentMain) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph_posts)

        setPreferredProgrammingLanguage()
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun provideViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

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
