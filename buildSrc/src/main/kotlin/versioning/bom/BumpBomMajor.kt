package versioning

import org.gradle.api.tasks.TaskAction

open class BumpBomMajor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMajorVersion()
        commitBomVersionChange()
    }
}
