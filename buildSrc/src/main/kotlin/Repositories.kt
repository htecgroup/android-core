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

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

object Repositories {

	const val jitpack = "https://jitpack.io"
	const val nexusRepository = "https://s01.oss.sonatype.org/service/local/"
	const val snapshotsRepository = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}

fun RepositoryHandler.jitpack(): MavenArtifactRepository =
	maven { setUrl(Repositories.jitpack) }
fun RepositoryHandler.sonatypeSnapshot(): MavenArtifactRepository =
	maven { setUrl(Repositories.snapshotsRepository) }
