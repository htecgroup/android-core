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
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
    id(Plugins.dokka)
    id(Plugins.mavenPublish)
}

apply(from = Config.Presentation.detekt)

android {
    compileSdk = Config.Presentation.compileSdkVersion

    defaultConfig {
        minSdk = Config.Presentation.minSdkVersion
        targetSdk = Config.Presentation.targetSdkVersion
    }

    compileOptions {
        sourceCompatibility = Config.Presentation.javaVersion
        targetCompatibility = Config.Presentation.javaVersion
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(Libs.androidx_compose_compiler_compiler)
    }
}

dependencies {
    implementation(Libs.hilt_android)
    implementation(Libs.hilt_navigation_compose)
    kapt(Libs.hilt_android_compiler)
    kapt(Libs.hilt_compiler)

    kapt(Libs.androidx_compose_compiler_compiler)
    implementation(Libs.androidx_compose_runtime_runtime)
    implementation(Libs.activity_compose)
    implementation(Libs.material3)
    api(Libs.appcompat)
    api(Libs.multidex)

    dokkaHtmlPartialPlugin(Libs.versioning_plugin)
}

repositories {
    mavenCentral()
}

configureReleasePublication(Config.Presentation, android.sourceSets["main"].java.srcDirs)