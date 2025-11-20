package versioning

import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

open class BumpMajor @Inject constructor(
    execOps: ExecOperations
) : BaseVersioningTask(execOps) {

    @TaskAction
    fun execute() {
        bumpMajorVersion()
        commitModuleVersionChange()
    }
}
