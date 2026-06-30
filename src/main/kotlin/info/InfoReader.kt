package info

import settings.SettingsManager
import java.io.File

object InfoReader {

    private val excludeDirs = setOf(".gradle", "build", ".git", ".idea")

    fun classesTopLevelCount(path: File): Int {
        if (!path.exists() || !path.isDirectory) return 0
        return path.walk().filter { it.isFile && it.extension.equals("java", ignoreCase = true) }.count()
    }

    fun blocks(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/assets/$namespace/blockstates")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun items(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/assets/$namespace/models/item")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun recipesCount(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/data/$namespace/recipe")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun javaLines(file: File): Long = countLines(file, "java")

    fun jsonLines(file: File): Long = countLines(file, "json")

    private fun countLines(file: File, extension: String): Long {
        if (!file.exists()) return 0

        if (file.isFile) {
            return if (file.extension.equals(extension, ignoreCase = true)) {
                file.useLines { lines -> lines.count().toLong() }
            } else {
                0
            }
        }

        return file.walkTopDown()
            .filter { it.isFile && it.extension.equals(extension, ignoreCase = true) }
            .sumOf { it.useLines { lines -> lines.count().toLong() } }
    }

    fun pretty(number: Long): String {
        return when {
            number >= 1_000_000_000 -> "%.1fB".format(number / 1_000_000_000.0)
            number >= 1_000_000 -> "%.1fM".format(number / 1_000_000.0)
            number >= 1_000 -> "%.1fK".format(number / 1_000.0)
            else -> number.toString()
        }.replace(".0", "")
    }

    fun getProjectPath(): File {
        val settings = SettingsManager.read()
        val isArchitectury = settings.path.contains("common")
        val targetFolder = if (isArchitectury) "common" else "src"

        var file = File(settings.path)
        while (file.parentFile != null) {
            if (file.name == targetFolder) {
                return file.parentFile
            }
            file = file.parentFile
        }

        return File(settings.path)
    }

    fun getResPath(): File {
        return File(SettingsManager.read().path)
    }

}
