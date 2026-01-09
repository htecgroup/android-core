package versioning

import Config
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class ResetPublishFlags @Inject constructor(
    protected val execOps: ExecOperations
) : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val rootDirectory: DirectoryProperty

    init {
        rootDirectory.set(project.rootProject.layout.projectDirectory)
    }

    @TaskAction
    fun execute() {
        VersionProperties(Config.Bom)
            .resetPublishFlag()
        VersionProperties(Config.Domain)
            .resetPublishFlag()
        VersionProperties(Config.Data)
            .resetPublishFlag()
        VersionProperties(Config.Presentation)
            .resetPublishFlag()
        VersionProperties(Config.PresentationDatabinding)
            .resetPublishFlag()
        VersionProperties(Config.Test)
            .resetPublishFlag()
        commitResetPublishingFlags()
    }

    protected fun commitResetPublishingFlags() {
        execOps.exec {
            commandLine(
                "sh",
                "${rootDirectory.asFile.get()}/buildSrc/src/main/kotlin/versioning/script/gitResetPublishingFlags.sh"
            )
        }
    }
}
