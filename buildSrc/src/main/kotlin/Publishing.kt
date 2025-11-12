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

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.task
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin
import java.io.File

interface MavenPublishingConfig {

    companion object {
        const val PUBLISH_URL = "https://github.com/htecgroup/android-core"
        const val PUBLISH_LICENSE_NAME = "Apache License"
        const val PUBLISH_LICENSE_URL =
            "https://github.com/htecgroup/android-core/blob/main/LICENSE"

        const val PUBLISH_SCM_URL = "https://github.com/htecgroup/android-core/tree/main"
        const val PUBLISH_SCM_CONNECTION = "scm:git:github.com/htecgroup/android-core.git"
        const val PUBLISH_SCM_DEVELOPER_CONNECTION =
            "scm:git:ssh://github.com/htecgroup/android-core.git"
    }

    val configName: String
    val artifactId: String
    val group: String
    val version: String
    val skipPublishing: Boolean
    val description: String
}

private fun Project.publication(
    componentName: String,
    config: MavenPublishingConfig,
    sourceSet: Set<File>?
) {
    afterEvaluate {
        extensions.configure("publishing", Action<PublishingExtension> {
            publications {
                create(config.configName, MavenPublication::class.java) {
                    from(components.getByName(componentName))
                    artifactId = config.artifactId
                    groupId = config.group
                    version = config.version

                    if (config.version.endsWith("LOCAL") || !config.skipPublishing) {
                        pom {
                            name.set(config.artifactId)
                            description.set(config.description)
                            url.set(MavenPublishingConfig.PUBLISH_URL)

                            licenses {
                                license {
                                    name.set(MavenPublishingConfig.PUBLISH_LICENSE_NAME)
                                    url.set(MavenPublishingConfig.PUBLISH_LICENSE_URL)
                                }
                            }

                            if (sourceSet != null) {
                                artifacts {
                                    if (artifactId === Config.Domain.artifactId) {
                                        // for Android modules (AAR), Gradle will automatically
                                        // create sources jar files in maven repository
                                        artifact(
                                            project.task<Jar>("androidSourcesJar") {
                                                archiveClassifier.set("sources")
                                                from(sourceSet)
                                            }
                                        )
                                    }
                                    artifact(
                                        project.task<Jar>("javadocJar") {
                                            archiveClassifier.set("javadoc")
                                            from(tasks.getByName("dokkaJavadoc").outputs)
                                        }
                                    )
                                }
                            }

                            developers {
                                developer {
                                    id.set("stefansimonovic-htec")
                                    name.set("Stefan Simonovic")
                                    email.set("stefan.simonovic@htecgroup.com")
                                }
                                developer {
                                    id.set("bojanb-htec")
                                    name.set("Bojan Bogojevic")
                                    email.set("bojan.bogojevic@htecgroup.com")
                                }
                                developer {
                                    id.set("stefan-sentic")
                                    name.set("Stefan Sentic")
                                    email.set("stefan.sentic@htecgroup.com")
                                }
                                developer {
                                    id.set("aleksandravojinovic-htec")
                                    name.set("Aleksandra Vojinovic")
                                    email.set("aleksandra.vojinovic@htecgroup.com")
                                }
                                developer {
                                    id.set("bobanst")
                                    name.set("Boban Stajic")
                                    email.set("boban.stajic@htecgroup.com")
                                }
                            }

                            scm {
                                connection.set(MavenPublishingConfig.PUBLISH_SCM_CONNECTION)
                                developerConnection.set(MavenPublishingConfig.PUBLISH_SCM_DEVELOPER_CONNECTION)
                                url.set(MavenPublishingConfig.PUBLISH_SCM_URL)
                            }
                        }

                    }
                }

                repositories {
                    if (config.version.endsWith("LOCAL")) {
                        mavenLocal { }
                    }
                }
            }
        })
    }

    if (!config.version.endsWith("LOCAL")) {
        configureSigning()
    }
}

private fun Project.configureSigning() {
    plugins.apply(SigningPlugin::class.java)

    configure<SigningExtension> {
        sign(
            ((project as ExtensionAware).extensions
                .getByName("publishing") as org.gradle.api.publish.PublishingExtension)
                .publications
        )
    }
}

fun Project.configureReleasePublication(config: MavenPublishingConfig, sourceSet: Set<File>) {
    publication("release", config, sourceSet)
}

fun Project.configureJavaPublication(config: MavenPublishingConfig, sourceSet: Set<File>) {
    publication("java", config, sourceSet)
}

fun Project.configureJavaPlatformPublication(config: MavenPublishingConfig) {
    publication("javaPlatform", config, null)
}
