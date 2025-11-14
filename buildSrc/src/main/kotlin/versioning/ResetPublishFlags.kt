package versioning

import Config
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

open class ResetPublishFlags @Inject constructor(
    protected val execOps: ExecOperations
) : DefaultTask() {

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
                "${project.rootDir}/buildSrc/src/main/kotlin/versioning/script/gitResetPublishingFlags.sh"
            )
        }
    }
}
