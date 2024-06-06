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

import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
//        classpath(Libs.com_android_tools_build_gradle)
//        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.google_services)
        classpath(Libs.firebase_crashlytics_gradle)
        classpath(Libs.android_junit5)
        classpath(Libs.firebase_appdistribution_gradle) {
            // Conflicting versions with refreshVersions plugin
            exclude(group = "com.google.guava", module = "guava")
        }
        classpath(Libs.hilt_android_gradle_plugin)
        classpath(Libs.navigation_safe_args_gradle_plugin)
        classpath(Libs.versioning_plugin)
    }
}
dependencies {
    dokkaHtmlMultiModulePlugin(Libs.versioning_plugin)
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    id(Plugins.detekt).version(Libs.io_gitlab_arturbosch_detekt_gradle_plugin)
    id(Plugins.dokka).version(Libs.org_jetbrains_dokka_gradle_plugin)
    id(Plugins.gradleNexusPublishing).version(Libs.io_github_gradle_nexus_publish_plugin_gradle_plugin)
}

version = Config.Bom.version

apply(from = Config.Domain.publishingRoot)

tasks.wrapper {
    gradleVersion = "8.4.1"
    distributionType = Wrapper.DistributionType.ALL
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = Config.Bom.javaVersion.toString()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

tasks.dokkaHtmlMultiModule {
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = Config.Bom.version
        olderVersionsDir = file("docs/older")
    }
}

// Get version from config and set GitHub Action output
tasks.register("setVersionOutput") {
    println("::set-output name=BOM_VERSION::${Config.Bom.version}")
}

tasks {
    register("bumpDomainPatch", versioning.BumpPatch::class) { module = Config.Domain }
    register("bumpDomainMinor", versioning.BumpMinor::class) { module = Config.Domain }
    register("bumpDomainMajor", versioning.BumpMajor::class) { module = Config.Domain }

    register("bumpDataPatch", versioning.BumpPatch::class) { module = Config.Data }
    register("bumpDataMinor", versioning.BumpMinor::class) { module = Config.Data }
    register("bumpDataMajor", versioning.BumpMajor::class) { module = Config.Data }

    register("bumpPresentationPatch", versioning.BumpPatch::class) { module = Config.Presentation }
    register("bumpPresentationMinor", versioning.BumpMinor::class) { module = Config.Presentation }
    register("bumpPresentationMajor", versioning.BumpMajor::class) { module = Config.Presentation }

    register("bumpPresentationDatabindingPatch", versioning.BumpPatch::class) {
        module = Config.PresentationDatabinding
    }
    register("bumpPresentationDatabindingMinor", versioning.BumpMinor::class) {
        module = Config.PresentationDatabinding
    }
    register("bumpPresentationDatabindingMajor", versioning.BumpMajor::class) {
        module = Config.PresentationDatabinding
    }

    register("bumpTestPatch", versioning.BumpPatch::class) { module = Config.Test }
    register("bumpTestMinor", versioning.BumpMinor::class) { module = Config.Test }
    register("bumpTestMajor", versioning.BumpMajor::class) { module = Config.Test }

    register("bumpBomPatch", versioning.BumpBomPatch::class) { module = Config.Bom }
    register("bumpBomMinor", versioning.BumpBomMinor::class) { module = Config.Bom }
    register("bumpBomMajor", versioning.BumpBomMajor::class) { module = Config.Bom }

    register("resetPublishFlags", versioning.ResetPublishFlags::class)
}
