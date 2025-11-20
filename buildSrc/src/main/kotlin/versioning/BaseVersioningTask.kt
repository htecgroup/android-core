package versioning

import Config.Core
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class BaseVersioningTask : DefaultTask() {

    @get:Inject
    abstract val execOps: ExecOperations

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val rootDirectory: DirectoryProperty

    @Input lateinit var module: Core

    init {
        rootDirectory.set(project.rootProject.layout.projectDirectory)
    }

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

        execOps.exec {
            commandLine(
                "sh",
                "${rootDirectory.asFile.get()}/buildSrc/src/main/kotlin/versioning/script/gitModuleVersionUpdate.sh",
                module.artifactId,
                versionProperties.version.substringBefore('-')
            )
        }
    }

    protected fun commitBomVersionChange() {
        val versionProperties = VersionProperties(module)

        execOps.exec {
            commandLine(
                "sh",
                "${rootDirectory.asFile.get()}/buildSrc/src/main/kotlin/versioning/script/gitBomVersionUpdate.sh",
                versionProperties.version.substringBefore('-')
            )
        }
    }
}
