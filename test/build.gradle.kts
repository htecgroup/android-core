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

apply(from = Config.Test.detekt)

android {
    namespace = Config.Test.namespace
    compileSdk = Config.Test.compileSdkVersion

    defaultConfig {
        minSdk = Config.Test.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = Config.Test.javaVersion
        targetCompatibility = Config.Test.javaVersion
    }

    publishing {
        singleVariant("release") {}
    }
}

dependencies {
    api(libs.junit.jupiter.api)
    runtimeOnly(libs.junit.jupiter.engine)
    api(libs.mockk)
    api(libs.androidx.core.testing)
    api(libs.kotlinx.coroutines.test)
    api(libs.kluent.android)

    dokkaHtmlPlugin(libs.versioning.plugin)
}

configureReleasePublication(Config.Test, android.sourceSets["main"].java.srcDirs)