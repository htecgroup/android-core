package versioning

import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

open class BumpPatch @Inject constructor(
    execOps: ExecOperations
) : BaseVersioningTask(execOps) {

    @TaskAction
    fun execute() {
        bumpPatchVersion()
        commitModuleVersionChange()
    }
}
