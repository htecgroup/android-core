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
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
    id(Plugins.androidxNavigationSafeargs)
    id(Plugins.dokka)
    id(Plugins.mavenPublish)
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
    api(AndroidX.recyclerView)
    api(AndroidX.navigation.fragmentKtx)
    api(AndroidX.navigation.uiKtx)
    api(AndroidX.multidex)
    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)

    dokkaHtmlPartialPlugin(Libs.versioning_plugin)
}

configureReleasePublication(Config.PresentationDatabinding, android.sourceSets["main"].java.srcDirs)