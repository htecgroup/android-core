package versioning

import org.gradle.api.tasks.TaskAction

open class BumpBomPatch : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpPatchVersion()
        commitBomVersionChange()
    }
}
