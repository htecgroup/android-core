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

import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

object Signing {

    var keystoreProperties = Properties()

    fun loadReleaseProperties(project: Project) {
        try {
            val keystorePropertiesFile = project.file("signing/keystore.properties")
            keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        } catch (e: FileNotFoundException) {
            print("Not found keystore.properties file. It should be located under the app/signing directory")
        }
    }

    val keyAlias get() = keystoreProperties["keyAlias"] as String
    val keyPassword get() = keystoreProperties["keyPassword"] as String
    fun getStoreFile(project: Project) = project.file(keystoreProperties["storeFile"] as String)
    val storePassword get() = keystoreProperties["storePassword"] as String

}