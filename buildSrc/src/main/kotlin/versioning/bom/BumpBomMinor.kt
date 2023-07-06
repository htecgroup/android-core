package versioning

import org.gradle.api.tasks.TaskAction

open class BumpBomMinor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMinorVersion()
        commitBomVersionChange()
    }
}
