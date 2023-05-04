package versioning

import org.gradle.api.tasks.TaskAction

open class BumpMajor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMajorVersion()
        commitModuleVersionChange()
    }
}
