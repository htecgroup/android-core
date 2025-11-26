package versioning

import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class BumpBomMinor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMinorVersion()
        commitBomVersionChange()
    }
}
