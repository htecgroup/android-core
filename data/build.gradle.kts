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
	id(Plugins.kotlinAndroid)
	id(Plugins.dokka)
	id(Plugins.mavenPublish)
}

apply(from = Config.Data.detekt)

android {
	compileSdk = Config.Data.compileSdkVersion

	defaultConfig {
		minSdk = Config.Data.minSdkVersion
		targetSdk = Config.Data.targetSdkVersion
		testInstrumentationRunner = Config.Data.instrumentationRunner
		consumerProguardFiles("consumer-rules.pro")
	}

	compileOptions {
		sourceCompatibility = Config.Data.javaVersion
		targetCompatibility = Config.Data.javaVersion
	}
}

dependencies {
	implementation(project(Config.Domain.moduleName))
	implementation(Libs.kotlinx_coroutines_android)
	api(Libs.retrofit)
	api(Libs.converter_moshi)
	api(Libs.moshi_kotlin)

	dokkaHtmlPartialPlugin(Libs.versioning_plugin)
}

releasePublication(Config.Data)