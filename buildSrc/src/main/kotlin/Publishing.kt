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
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

interface MavenPublishingConfig {
	val configName: String
	val artifactId: String
	val group: String
	val version: String
}

private fun Project.publication(componentName: String, config: MavenPublishingConfig) {
	afterEvaluate {
		extensions.configure("publishing", Action<PublishingExtension> {
			publications {
				create(config.configName, MavenPublication::class.java) {
					from(components.getByName(componentName))
					artifactId = config.artifactId
					groupId = config.group
					version = config.version
				}

				repositories {
					if (config.version.endsWith("LOCAL")) {
						mavenLocal {  }
					}
				}
			}
		})
	}
}

fun Project.releasePublication(config: MavenPublishingConfig) {
	publication("release", config)
}
fun Project.javaPublication(config: MavenPublishingConfig) {
	publication("java", config)
}

fun Project.javaPlatformPublication(config: MavenPublishingConfig) {
	publication("javaPlatform", config)
}