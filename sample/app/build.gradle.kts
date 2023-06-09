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
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
    id(Plugins.firebaseCrashlytics)
    id(Plugins.firebaseAppDistribution)
}

apply(from = Config.CoreSample.detekt)

android {
    compileSdk = Config.CoreSample.compileSdkVersion

    defaultConfig {
        applicationId = Config.CoreSample.applicationId
        minSdk = Config.CoreSample.minSdkVersion
        targetSdk = Config.CoreSample.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = Config.CoreSample.instrumentationRunner
        multiDexEnabled = Config.CoreSample.multiDex
    }

    signingConfigs {

        Signing.loadReleaseProperties(project)

        create(Config.CoreSample.release) {
            if (Signing.keystoreProperties.isNotEmpty()) {
                keyAlias = Signing.keyAlias
                keyPassword = Signing.keyPassword
                storeFile = Signing.getStoreFile(project)
                storePassword = Signing.storePassword
            }
        }
    }

    buildTypes {
        getByName(Config.CoreSample.release) {
            isMinifyEnabled = Config.CoreSample.minifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(Config.CoreSample.release)
        }
        getByName(Config.CoreSample.debug) {
            firebaseAppDistribution {
                groups = Config.FirebaseDistribution.groups
                releaseNotesFile = Config.FirebaseDistribution.releaseNotesFile
                serviceCredentialsFile = Config.FirebaseDistribution.serviceCredentialsFile
            }
        }
    }
    compileOptions {
        sourceCompatibility = Config.CoreSample.javaVersion
        targetCompatibility = Config.CoreSample.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.CoreSample.javaVersion.toString()
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(Config.Module.domain))
    implementation(project(Config.Module.data))
    /**
     * #DataBindingSample
     * Change with the following line to enable databinding example
     * implementation(project(Config.Module.presentationDatabinding))
     */
    implementation(project(Config.Module.presentation))

    // Hilt
    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)
    kapt(Libs.hilt_compiler)

    implementation(Libs.play_services_ads)

    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_crashlytics_ktx)
    implementation(Libs.firebase_analytics_ktx)
    implementation(Libs.firebase_messaging_ktx)

    implementation(Libs.work_runtime_ktx)
}

apply(plugin = Plugins.googleServices)

repositories {
    mavenCentral()
}