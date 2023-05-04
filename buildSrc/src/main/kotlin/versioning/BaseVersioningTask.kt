package versioning

import Config.Core
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

abstract class BaseVersioningTask() : DefaultTask() {

    @Input lateinit var module: Core

    protected fun bumpPatchVersion() {
        VersionProperties(module)
            .apply { versionPatch += 1 }
            .store()
    }

    protected fun bumpMinorVersion() {
        VersionProperties(module)
            .apply { versionPatch = 0 }
            .apply { versionMinor += 1 }
            .store()
    }

    protected fun bumpMajorVersion() {
        VersionProperties(module)
            .apply { versionPatch = 0 }
            .apply { versionMinor = 0 }
            .apply { versionMajor += 1 }
            .store()
    }

    protected fun commitModuleVersionChange() {
        val versionProperties = VersionProperties(module)

        project.exec {
            commandLine(
                "sh",
                "${project.rootDir}/buildSrc/src/main/kotlin/versioning/script/gitModuleVersionUpdate.sh",
                module.artifactId,
                versionProperties.version.substringBefore('-')
            )
        }
    }

    protected fun commitBomVersionChange() {
        val versionProperties = VersionProperties(module)

        project.exec {
            commandLine(
                "sh",
                "${project.rootDir}/buildSrc/src/main/kotlin/versioning/script/gitBomVersionUpdate.sh",
                versionProperties.version.substringBefore('-')
            )
        }
    }
}
