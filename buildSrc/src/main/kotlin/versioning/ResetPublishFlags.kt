package versioning

import Config
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class ResetPublishFlags : DefaultTask() {

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
        project.exec {
            commandLine(
                "sh",
                "${project.rootDir}/buildSrc/src/main/kotlin/versioning/script/gitResetPublishingFlags.sh"
            )
        }
    }
}