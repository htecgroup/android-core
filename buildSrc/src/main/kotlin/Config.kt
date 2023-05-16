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

import org.gradle.api.JavaVersion
import versioning.VersionProperties

object Config {

    abstract class Core : MavenPublishingConfig {
        val compileSdkVersion = 31
        val minSdkVersion = 21
        val targetSdkVersion = 31
        val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val release = "release"
        val debug = "debug"
        val detekt = "../detekt.gradle"
        override val group = "com.htecgroup.core"
        val javaVersion = JavaVersion.VERSION_17
        abstract val moduleName: String
        override val version: String get() = VersionProperties(this).version
    }

    object Bom : Core() {
        override val configName = "Core"
        override val artifactId = "bom"
        override val moduleName = ":bom"
    }

    object Domain : Core() {
        override val configName = "Domain"
        override val artifactId = "domain"
        override val moduleName = ":domain"
    }

    object Data : Core() {
        override val configName = "Data"
        override val artifactId = "data"
        override val moduleName = ":data"
    }

    object Presentation : Core() {
        override val configName = "Presentation"
        override val artifactId = "presentation"
        override val moduleName = ":presentation"
    }

    object PresentationDatabinding : Core() {
        override val configName = "PresentationDatabinding"
        override val artifactId = "presentation-databinding"
        override val moduleName = ":presentation-databinding"
    }

    object Test : Core() {
        override val configName = "Test"
        override val artifactId = "test"
        override val moduleName = ":test"
    }

}