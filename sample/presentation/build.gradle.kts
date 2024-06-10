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

	alias(libs.plugins.android.library)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.com.google.dagger.hilt.android)
	alias(libs.plugins.org.jetbrains.kotlin.kapt)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.android.junit5)
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

	compileOptions {
		sourceCompatibility = Config.Sample.javaVersion
		targetCompatibility = Config.Sample.javaVersion
	}

	kotlinOptions {
		jvmTarget = Config.Sample.javaVersion.toString()
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
		kotlinCompilerExtensionVersion = libs.versions.compiler.get()
	}
}

dependencies {
	api(project(Config.Presentation.moduleName))
	implementation(project(Config.Sample.Module.domain))

	// Compose
	implementation(libs.androidx.material3)
	implementation(libs.androidx.runtime)
	implementation(libs.androidx.lifecycle.viewmodel.compose)
	implementation(libs.androidx.hilt.navigation.compose)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.androidx.activity.compose)
	implementation(libs.ui.tooling.preview)
	implementation(libs.ui.tooling)
	implementation(libs.accompanist.swiperefresh)

	// Hilt
	implementation(libs.dagger.hilt)
	kapt(libs.dagger.hilt.compiler)

	implementation(libs.play.services.ads)

	implementation(libs.androidx.work.runtime.ktx)

	// Test
	testImplementation(project(Config.Test.moduleName))
	testImplementation(libs.robolectric)
	testImplementation(libs.androidx.core.testing)
	testImplementation(libs.core.ktx)
	testImplementation(libs.kotlinx.coroutines.test)
	testImplementation(libs.androidx.junit.ktx)
	testImplementation(libs.mockk)
	testImplementation(libs.kluent.android)

	// (Required) Writing and executing Unit Tests on the JUnit Platform
	testImplementation(libs.junit.jupiter.api)
	testImplementation(libs.junit.jupiter.engine)

	// (Optional) If you need "Parameterized Tests"
	testImplementation(libs.junit.jupiter.params)

	// (Optional) If you also have JUnit 4-based tests
	//	testImplementation(libs.junit)
	testImplementation(libs.junit.vintage.engine)

	// Android Test

	androidTestImplementation(libs.androidx.runner)
	androidTestImplementation(libs.androidx.rules)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.espresso.contrib)
}
