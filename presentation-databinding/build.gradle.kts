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
    alias(libs.plugins.com.google.developers.ksp)
    alias(libs.plugins.org.jetbrains.dokka)
    alias(libs.plugins.androidx.navigation.safeargs)

    id("maven-publish")
}

apply(from = Config.PresentationDatabinding.detekt)

android {
    namespace = Config.PresentationDatabinding.namespace
    compileSdk = Config.PresentationDatabinding.compileSdkVersion

    defaultConfig {
        minSdk = Config.PresentationDatabinding.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = Config.PresentationDatabinding.javaVersion
        targetCompatibility = Config.PresentationDatabinding.javaVersion
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(libs.androidx.recyclerview)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.multidex)

    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    dokkaHtmlPartialPlugin(libs.versioning.plugin)
}

configureReleasePublication(Config.PresentationDatabinding, android.sourceSets["main"].java.srcDirs)