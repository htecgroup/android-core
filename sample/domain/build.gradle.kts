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
}

apply(from = Config.Sample.detekt)

android {
	compileSdk = Config.Sample.compileSdkVersion

	defaultConfig {
		minSdk = Config.Sample.minSdkVersion
		targetSdk = Config.Sample.targetSdkVersion

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

	lint {
		abortOnError = false
	}

	testOptions {
		unitTests.apply {
			isReturnDefaultValues = true
			isIncludeAndroidResources = true
		}
	}
}

dependencies {
	api(project(Config.Domain.moduleName))

	// Hilt
	implementation(Libs.hilt_android)
	kapt(Libs.hilt_android_compiler)

	// Coroutines/Flows
	implementation(Libs.kotlinx_coroutines_core)
	implementation(Libs.kotlinx_coroutines_android)

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

}

repositories {
	mavenCentral()
}