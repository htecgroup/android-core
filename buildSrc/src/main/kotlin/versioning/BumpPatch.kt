package versioning

import org.gradle.api.tasks.TaskAction

open class BumpPatch : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpPatchVersion()
        commitModuleVersionChange()
    }
}
