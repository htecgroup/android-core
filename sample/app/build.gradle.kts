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

import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.firebase.crashlytics)
    alias(libs.plugins.com.google.firebase.appdistribution)
    alias(libs.plugins.com.google.gms.google.services)

    /**
     * #DataBindingSample
     * There is no support for ksp with databinding (kapt should be used instead).
     * Change with the following line to enable databinding example:
     * alias(libs.plugins.org.jetbrains.kotlin.kapt)
     */
    alias(libs.plugins.com.google.developers.ksp)
}

apply(from = Config.Sample.detekt)

android {
    namespace = "${Config.Sample.applicationId}.app"
    compileSdk = Config.Sample.compileSdkVersion

    defaultConfig {
        applicationId = Config.Sample.applicationId
        minSdk = Config.Sample.minSdkVersion
        targetSdk = Config.Sample.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = Config.Sample.instrumentationRunner
        multiDexEnabled = Config.Sample.multiDex
    }

    signingConfigs {

        Signing.loadReleaseProperties(project)

        create(Config.Sample.release) {
            if (Signing.keystoreProperties.isNotEmpty()) {
                keyAlias = Signing.keyAlias
                keyPassword = Signing.keyPassword
                storeFile = Signing.getStoreFile(project)
                storePassword = Signing.storePassword
            }
        }
    }

    buildTypes {
        getByName(Config.Sample.release) {
            isMinifyEnabled = Config.Sample.minifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(Config.Sample.release)
        }
        getByName(Config.Sample.debug) {
            firebaseAppDistribution {
                groups = Config.Sample.FirebaseDistribution.groups
                releaseNotesFile = Config.Sample.FirebaseDistribution.releaseNotesFile
                serviceCredentialsFile = Config.Sample.FirebaseDistribution.serviceCredentialsFile
            }
        }
    }
    compileOptions {
        sourceCompatibility = Config.Sample.javaVersion
        targetCompatibility = Config.Sample.javaVersion
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(Config.Sample.javaVersion.toString())
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(Config.Sample.Module.domain))
    implementation(project(Config.Sample.Module.data))
    /**
     * #DataBindingSample
     * Change with the following line to enable databinding example
     * implementation(project(Config.Sample.Module.presentationDatabinding))
     */
    implementation(project(Config.Sample.Module.presentation))

    // Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.hilt.navigation.compose)

    /**
     * #DataBindingSample
     * Change with the following line to enable databinding example
     * kapt(libs.dagger.hilt.compiler)
     */
    ksp(libs.dagger.hilt.compiler)

    implementation(libs.play.services.ads)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.okhttp3)

    implementation(libs.androidx.material3.navigation3)
}
