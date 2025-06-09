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
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

apply(from = Config.Presentation.detekt)

android {
    namespace = Config.Presentation.namespace
    compileSdk = Config.Presentation.compileSdkVersion

    defaultConfig {
        minSdk = Config.Presentation.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = Config.Presentation.javaVersion
        targetCompatibility = Config.Presentation.javaVersion
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.dagger.hilt.compiler)

    ksp(libs.androidx.compiler)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.activity.compose)
    api(libs.androidx.appcompat)
    api(libs.androidx.multidex)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)

    dokkaHtmlPartialPlugin(libs.versioning.plugin)
}

configureReleasePublication(Config.Presentation, android.sourceSets["main"].java.srcDirs)