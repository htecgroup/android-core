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
        val compileSdkVersion = 36
        val minSdkVersion = 21
        val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val release = "release"
        val debug = "debug"
        val detekt = "../scripts/detekt.gradle"
        val publishingRoot = "scripts/publishing-root.gradle"
        override val group = "com.htecgroup.androidcore"
        val javaVersion = JavaVersion.VERSION_17
        open val namespace: String get() = "${group}.${artifactId}"
        abstract val moduleName: String
        override val version: String get() = VersionProperties(this).version
        override val skipPublishing: Boolean get() = VersionProperties(this).skipPublishing
    }

    object Bom : Core() {
        override val configName = "Core"
        override val artifactId = "bom"
        override val moduleName = ":bom"
        override val description = "Standard bill of service module used for uniting and easing the version management of the rest of the modules."
    }

    object Domain : Core() {
        override val configName = "Domain"
        override val artifactId = "domain"
        override val moduleName = ":domain"
        override val description = "A set of most common extensions, result wrappers and base use-case classes."
    }

    object Data : Core() {
        override val configName = "Data"
        override val artifactId = "data"
        override val moduleName = ":data"
        override val description = "A set of frequently used methods expecially in the repository classes."
    }

    object Presentation : Core() {
        override val configName = "Presentation"
        override val artifactId = "presentation"
        override val moduleName = ":presentation"
        override val description = "A set of base classes for creating UI primarily based on Jetpack Compose."
    }

    object PresentationDatabinding : Core() {
        override val configName = "PresentationDatabinding"
        override val artifactId = "presentation-databinding"
        override val moduleName = ":presentation-databinding"
        override val namespace = "${group}.presentation"
        override val description = "A set of base classes for creating UI primarily based on Data Binding."
    }

    object Test : Core() {
        override val configName = "Test"
        override val artifactId = "test"
        override val moduleName = ":test"
        override val description = "A set of classes wich contain some basic setup for testing."
    }

    object Sample {
        const val applicationId = "com.htecgroup.coresample"
        const val compileSdkVersion = 36
        const val minSdkVersion = 23
        const val targetSdkVersion = 36
        const val versionCode = 1
        const val versionName = "1.0.0"
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val release = "release"
        const val debug = "debug"
        const val minifyEnabled = true
        const val multiDex = true
        const val detekt = "../../scripts/detekt.gradle"
        val javaVersion = JavaVersion.VERSION_17

        object Module {
            const val presentationDatabinding = ":sample:presentation-databinding"
            const val presentation = ":sample:presentation"
            const val domain = ":sample:domain"
            const val data = ":sample:data"
            const val test = ":sample:test"
        }

        object FirebaseDistribution {
            const val groups = "htec"
            const val releaseNotesFile = "./app/release-notes.txt"
            const val serviceCredentialsFile = "./app/core-sample-firebase.json"
        }

        object Dev {
            const val name = "dev"
            const val dimension = "core"
            const val appIdSuffix = ".dev"
        }
    }
}
