import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
	alias(libs.plugins.com.google.developers.ksp)
	alias(libs.plugins.android.junit5)
	alias(libs.plugins.com.google.dagger.hilt.android)
}

apply(from = Config.Sample.detekt)

android {
	namespace = "${Config.Sample.applicationId}.data"
	compileSdk = Config.Sample.compileSdkVersion

	defaultConfig {
		minSdk = Config.Sample.minSdkVersion

		testInstrumentationRunner = Config.Sample.instrumentationRunner
		consumerProguardFiles("consumer-rules.pro")

		javaCompileOptions {
			annotationProcessorOptions {
				mutableMapOf("room.schemaLocation" to "$projectDir/schemas")

			}
		}
	}

	compileOptions {
		sourceCompatibility = Config.Sample.javaVersion
		targetCompatibility = Config.Sample.javaVersion
	}

	kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(Config.Sample.javaVersion.toString())
        }
    }

	buildFeatures {
		buildConfig = true
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

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation(project(Config.Data.moduleName))

	implementation(project(Config.Sample.Module.domain))

	// Hilt
	implementation(libs.dagger.hilt)
	ksp(libs.dagger.hilt.compiler)

	// Room
	implementation(libs.androidx.room.runtime)
	ksp(libs.androidx.room.compiler)
	implementation(libs.androidx.room.ktx)

	// Squareup
    implementation(libs.okhttp3)
	implementation(libs.logging.interceptor)

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.crashlytics)
	implementation(libs.firebase.analytics)
	implementation(libs.firebase.messaging)

	implementation(libs.androidx.work.runtime.ktx)

	ksp(libs.moshi.kotlin.codegen)

	// Test
	testImplementation(project(Config.Test.moduleName))
	testImplementation(libs.robolectric)
	testImplementation(libs.androidx.core.testing)
	testImplementation(libs.androidx.core.ktx)

	testImplementation(libs.kotlinx.coroutines.test)
	testImplementation(libs.androidx.junit.ktx)
	testImplementation(libs.mockk)
	testImplementation(libs.kluent.android)

	// (Required) Writing and executing Unit Tests on the JUnit Platform
	testImplementation(libs.junit.jupiter.api)
	testRuntimeOnly(libs.junit.jupiter.engine)

	// (Optional) If you need "Parameterized Tests"
	testRuntimeOnly(libs.junit.jupiter.params)

	// (Optional) If you also have JUnit 4-based tests
	//testImplementation(Libs.junit)
	testRuntimeOnly(libs.junit.vintage.engine)
}