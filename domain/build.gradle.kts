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
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.org.jetbrains.dokka)
    id("maven-publish")
}

apply(from = Config.Domain.detekt)

java {
    sourceCompatibility = Config.Domain.javaVersion
    targetCompatibility = Config.Domain.javaVersion
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    dokkaHtmlPartialPlugin(libs.versioning.plugin)
}

configureJavaPublication(Config.Domain, sourceSets["main"].java.srcDirs)
