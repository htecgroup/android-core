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

plugins {
	id(Plugins.androidLibrary)
	kotlin(Plugins.android)
	kotlin(Plugins.kapt)
	id(Plugins.junit5)
	id(Plugins.hilt)
	id(Plugins.kotlinParcelize)
	id(Plugins.androidxNavigationSafeargs)
}

apply(from = Config.CoreSample.detekt)

android {
	compileSdk = Config.CoreSample.compileSdkVersion

	defaultConfig {
		minSdk = Config.CoreSample.minSdkVersion
		targetSdk = Config.CoreSample.targetSdkVersion
		testInstrumentationRunner = Config.CoreSample.instrumentationRunner
		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		getByName(Config.CoreSample.release) {
			isMinifyEnabled = Config.CoreSample.minifyEnabled
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_11.toString()
	}

	lint {
		abortOnError = false
	}

	testOptions {
		unitTests.apply {
			isReturnDefaultValues = true
			isIncludeAndroidResources = true
		}
	}

	buildFeatures {
		dataBinding = true
		viewBinding = true
	}
}

dependencies {

	api(platform(Libs.core_bom))
	api(Libs.presentation_databinding)

	implementation(project(Config.Module.domain))

	// Hilt
	implementation(Libs.hilt_android)
	kapt(Libs.hilt_android_compiler)


	implementation(Libs.constraintlayout)

	implementation(Libs.play_services_ads)

	implementation(Libs.work_runtime_ktx)

	implementation(Libs.swiperefreshlayout)

	// Test
	testImplementation(Libs.test)
	testImplementation(Libs.robolectric)
	testImplementation(Libs.core_testing)
	testImplementation(Libs.core_ktx)
	testImplementation(Libs.kotlinx_coroutines_test)
	testImplementation(Libs.junit_ktx)
	testImplementation(Libs.mockk)
	testImplementation(Libs.kluent_android)

	// (Required) Writing and executing Unit Tests on the JUnit Platform
	testImplementation(Libs.junit_jupiter_api)
	testRuntimeOnly(Libs.junit_jupiter_engine)

	// (Optional) If you need "Parameterized Tests"
	testImplementation(Libs.junit_jupiter_params)

	// (Optional) If you also have JUnit 4-based tests
	//testImplementation(Libs.junit)
	testRuntimeOnly(Libs.junit_vintage_engine)

	// Android Test

	androidTestImplementation(Libs.androidx_test_runner)
	androidTestImplementation(Libs.androidx_test_rules)
	androidTestImplementation(Libs.espresso_core)
	androidTestImplementation(Libs.espresso_contrib)
}