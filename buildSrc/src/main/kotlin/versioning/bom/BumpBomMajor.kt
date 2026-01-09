package versioning

import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class BumpBomMajor : BaseVersioningTask() {

    @TaskAction
    fun execute() {
        bumpMajorVersion()
        commitBomVersionChange()
    }
}
