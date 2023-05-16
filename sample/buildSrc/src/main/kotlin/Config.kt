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

import org.gradle.api.JavaVersion

object Config {

	object CoreSample {
		const val applicationId = "com.htecgroup.coresample"
		const val compileSdkVersion = 33
		const val minSdkVersion = 21
		const val targetSdkVersion = 33
		const val versionCode = 1
		const val versionName = "1.0.0"
		const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		const val release = "release"
		const val debug = "debug"
		const val minifyEnabled = true
		const val multiDex = true
		const val detekt = "../detekt.gradle"
		val javaVersion = JavaVersion.VERSION_17
	}

	object Module {
		const val presentationDatabinding = ":presentation-databinding"
		const val presentation = ":presentation"
		const val domain = ":domain"
		const val data = ":data"
		const val test = ":test"
	}

	object FirebaseDistribution {
		const val groups = "htec"
		const val releaseNotesFile = "./app/release-notes.txt"
		const val serviceCredentialsFile = "./app/core-sample-firebase.json"
	}

	object Dev {
		const val name = "dev"
		const val dimension = "core"
		const val appIdSuffix = ".dev"
	}

}