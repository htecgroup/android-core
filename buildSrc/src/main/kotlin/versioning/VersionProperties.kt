package versioning

import Config.Core
import java.io.File
import java.util.Properties

class VersionProperties(private val module: Core) {

    companion object {
        private const val PROPERTY_VERSION_MAJOR = "VERSION_MAJOR"
        private const val PROPERTY_VERSION_MINOR = "VERSION_MINOR"
        private const val PROPERTY_VERSION_PATCH = "VERSION_PATCH"
        private val PROPERTY_SKIP_PUBLISHING = "SKIP_PUBLISHING"
        private const val VERSION_SUFFIX = "BOM_VERSION_SUFFIX"
    }

    private val projectRoot = System.getProperty("rootDir")
    private val versionFile = File("$projectRoot/${module.artifactId}/version.properties")
    private val properties = Properties()
    private val versionSuffix = System.getenv(VERSION_SUFFIX).orEmpty()

    var versionMajor = 1
    var versionMinor = 0
    var versionPatch = 0
    var skipPublishing = true
    val version: String get() = "$versionMajor.$versionMinor.$versionPatch$versionSuffix"

    init {
        versionFile
            .takeIf { it.canRead() }
            ?.inputStream()
            ?.let { input ->
                properties.load(input)
                versionMajor = properties.getProperty(PROPERTY_VERSION_MAJOR).toInt()
                versionMinor = properties.getProperty(PROPERTY_VERSION_MINOR).toInt()
                versionPatch = properties.getProperty(PROPERTY_VERSION_PATCH).toInt()
                skipPublishing = properties.getProperty(PROPERTY_SKIP_PUBLISHING).toBoolean()
                input.close()
            }
    }

    fun store() {
        properties.put(PROPERTY_VERSION_MAJOR, versionMajor.toString())
        properties.put(PROPERTY_VERSION_MINOR, versionMinor.toString())
        properties.put(PROPERTY_VERSION_PATCH, versionPatch.toString())
        properties.put(PROPERTY_SKIP_PUBLISHING, "false")
        versionFile
            .takeIf { it.canWrite() }
            ?.bufferedWriter()
            ?.let {
                properties.store(it, null)
                it.close()
            }
    }

    fun resetPublishFlag() {
        skipPublishing = true
        properties.put(PROPERTY_SKIP_PUBLISHING, "true")
        versionFile
            .takeIf { it.canWrite() }
            ?.bufferedWriter()
            ?.let {
                properties.store(it, null)
                it.close()
            }
    }
}
