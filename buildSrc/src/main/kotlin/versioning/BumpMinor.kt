package versioning

import org.gradle.api.tasks.TaskAction

open class BumpMinor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMinorVersion()
        commitModuleVersionChange()
    }
}
