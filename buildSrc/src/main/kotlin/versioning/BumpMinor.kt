package versioning

import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

open class BumpMinor @Inject constructor(
    execOps: ExecOperations
) : BaseVersioningTask(execOps) {

    @TaskAction
    fun execute() {
        bumpMinorVersion()
        commitModuleVersionChange()
    }
}
