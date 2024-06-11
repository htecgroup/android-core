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
    alias(libs.plugins.org.jetbrains.dokka)

    id("maven-publish")
}

apply(from = Config.Data.detekt)

android {
    namespace = Config.Data.namespace
    compileSdk = Config.Data.compileSdkVersion

    defaultConfig {
        minSdk = Config.Data.minSdkVersion
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

    implementation(libs.kotlinx.coroutines.android)
    api(libs.retrofit)
    api(libs.converter.moshi)
    api(libs.moshi.kotlin)

    dokkaHtmlPartialPlugin(libs.versioning.plugin)
}

configureReleasePublication(Config.Data, android.sourceSets["main"].java.srcDirs)
