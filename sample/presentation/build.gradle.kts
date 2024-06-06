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

import de.fayard.refreshVersions.core.versionFor

plugins {
	id(Plugins.androidLibrary)
	kotlin(Plugins.android)
	kotlin(Plugins.kapt)
	id(Plugins.junit5)
	id(Plugins.hilt)
	id(Plugins.kotlinParcelize)
}

apply(from = Config.Sample.detekt)

android {

	namespace = "${Config.Sample.applicationId}.presentation"
	compileSdk = Config.Sample.compileSdkVersion

	defaultConfig {
		minSdk = Config.Sample.minSdkVersion
		testInstrumentationRunner = Config.Sample.instrumentationRunner
		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		getByName(Config.Sample.release) {
			isMinifyEnabled = Config.Sample.minifyEnabled
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = Config.Sample.javaVersion
		targetCompatibility = Config.Sample.javaVersion
	}

	kotlinOptions {
		jvmTarget = Config.Sample.javaVersion.toString()
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
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion = versionFor(Libs.androidx_compose_compiler_compiler)
	}
}

dependencies {
	api(project(Config.Presentation.moduleName))
	implementation(project(Config.Sample.Module.domain))

	// Compose
	implementation(Libs.material3)
	implementation(Libs.androidx_compose_runtime_runtime)
	implementation(Libs.lifecycle_viewmodel_compose)
	implementation(Libs.hilt_navigation_compose)
	implementation(Libs.navigation_compose)
	implementation(Libs.activity_compose)
	implementation(Libs.ui_tooling_preview)
	implementation(Libs.ui_tooling)
	implementation(Libs.accompanist_swiperefresh)

	// Hilt
	implementation(Libs.hilt_android)
	kapt(Libs.hilt_android_compiler)
	kapt(Libs.hilt_compiler)

	implementation(Libs.play_services_ads)

	implementation(Libs.work_runtime_ktx)

	// Test
	testImplementation(project(Config.Test.moduleName))
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
