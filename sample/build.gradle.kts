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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

	repositories {
		google()
		mavenCentral()
		sonatypeSnapshot()
		mavenLocal()
	}
	dependencies {
		classpath(Libs.com_android_tools_build_gradle)
		classpath(Libs.kotlin_gradle_plugin)
		classpath(Libs.google_services)
		classpath(Libs.firebase_crashlytics_gradle)
		classpath(Libs.android_junit5)
		classpath(Libs.firebase_appdistribution_gradle) {
			// Conflicting versions with refreshVersions plugin
			exclude(group = "com.google.guava", module = "guava")
		}
		classpath(Libs.hilt_android_gradle_plugin)
		classpath(Libs.navigation_safe_args_gradle_plugin)
	}
}

plugins {
	id(Plugins.detekt).version(Libs.io_gitlab_arturbosch_detekt_gradle_plugin)
}

allprojects {
	repositories {
		google()
		mavenCentral()
		sonatypeSnapshot()
		mavenLocal()
		jitpack()
	}
}

tasks.wrapper {
	gradleVersion = "7.5.1"
	distributionType = Wrapper.DistributionType.ALL
}

tasks.withType<KotlinCompile>().all {
	kotlinOptions {
		jvmTarget = Config.CoreSample.javaVersion.toString()
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}