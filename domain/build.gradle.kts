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
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)
    id(Plugins.dokka)
    id(Plugins.mavenPublish)
}

apply(from = Config.Domain.detekt)

java {
    sourceCompatibility = Config.Domain.javaVersion
    targetCompatibility = Config.Domain.javaVersion
}

dependencies {
    implementation(Libs.kotlinx_coroutines_android)

    dokkaHtmlPartialPlugin(Libs.versioning_plugin)
}

configureJavaPublication(Config.Domain, sourceSets["main"].java.srcDirs)
