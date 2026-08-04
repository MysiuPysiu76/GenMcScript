package info

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import settings.SettingsManager
import java.io.File

object InfoReader {

    private val excludeDirs = setOf(".gradle", "build", ".git", ".idea", ".cache")

    private fun shouldEnter(dir: File) = dir.name !in excludeDirs

    fun classesTopLevelCount(path: File): Int {
        if (!path.exists() || !path.isDirectory) return 0
        return path.walk().onEnter(::shouldEnter).filter { it.isFile && it.extension.equals("java", ignoreCase = true) }.count()
    }

    fun blocks(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/assets/$namespace/blockstates")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().onEnter(::shouldEnter).filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun items(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/assets/$namespace/models/item")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().onEnter(::shouldEnter).filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun recipesCount(file: File, namespace: String): Int {
        val dir = File(file.parent, "resources/data/$namespace/recipe")
        if (!dir.exists() || !dir.isDirectory) return 0
        return dir.walk().onEnter(::shouldEnter).filter { it.isFile && it.extension.equals("json", ignoreCase = true) }.count()
    }

    fun recipesByType(file: File, namespace: String): Map<String, Int> {
        return setOf(namespace, "minecraft")
            .flatMap { recipesOfType(File(file.parent, "resources/data/$it/recipe")) }
            .groupingBy { it }
            .eachCount()
    }

    private fun recipesOfType(dir: File): List<String> {
        if (!dir.exists() || !dir.isDirectory) return emptyList()
        return dir.walk().onEnter(::shouldEnter)
            .filter { it.isFile && it.extension.equals("json", ignoreCase = true) }
            .mapNotNull { recipeFile ->
                val type = runCatching {
                    val element = Json.parseToJsonElement(recipeFile.readText())
                    (element as? JsonObject)?.get("type")?.jsonPrimitive?.contentOrNull
                }.getOrNull()
                type ?: "unknown"
            }
            .toList()
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

        return file.walkTopDown().onEnter(::shouldEnter)
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
