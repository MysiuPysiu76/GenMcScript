package update

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import settings.SettingsManager
import java.io.File

class FileUpdater {

    fun recipes(type: String): List<File> {
        val settings = SettingsManager.read()
        val json = Json { ignoreUnknownKeys = true }

        val directoriesToScan = listOf(
            File(settings.path, "data/${settings.namespace}/recipes"),
            File(settings.path, "data/minecraft/recipes")
        )

        return directoriesToScan
            .filter { it.exists() && it.isDirectory }
            .flatMap { dir ->
                dir.walkTopDown()
                    .filter { it.isFile && it.extension == "json" }
                    .filter { file ->
                        try {
                            val obj = json.parseToJsonElement(file.readText()).jsonObject
                            obj["type"]?.jsonPrimitive?.content == type
                        } catch (_: Exception) {
                            false
                        }
                    }
                    .toList()
            }
    }

    fun json(): Json {
        return Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    fun v1() {
        println()
        divider()

        val json = json()
        var n = 0

        recipes("minecraft:smelting").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = convertStringToObject(originalObject, "result", "id")

                if (updatedObject != null) {
                    file.writeText(json.encodeToString(JsonObject.serializer(), updatedObject))
                    info(file.name)
                    n++
                }
            } catch (e: Exception) {
                error(file.name, e)
            }
        }

        if (n != 0) divider()
        finish("minecraft:smelting", n)
        divider()
        n = 0

        recipes("minecraft:crafting_shapeless").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = renameKeyInObject(originalObject, "result", "item", "id")

                if (updatedObject != null) {
                    file.writeText(json.encodeToString(JsonObject.serializer(), updatedObject))
                    info(file.name)
                    n++
                }
            } catch (e: Exception) {
                error(file.name, e)
            }
        }

        if (n != 0) divider()
        finish("minecraft:crafting_shapeless", n)
        divider()
        n = 0

        recipes("minecraft:stonecutting").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val step1 = convertStringToObject(originalObject, "result", "id")
                val updatedObject = step1?.let { moveFieldIntoObject(it, "result", "count") }

                if (updatedObject != null) {
                    file.writeText(json.encodeToString(JsonObject.serializer(), updatedObject))
                    info(file.name)
                    n++
                }
            } catch (e: Exception) {
                error(file.name, e)
            }
        }

        if (n != 0) divider()
        finish("minecraft:stonecutting", n)
        divider()

        recipes("minecraft:crafting_shaped").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = renameKeyInObject(originalObject, "result", "item", "id")

                if (updatedObject != null) {
                    file.writeText(json.encodeToString(JsonObject.serializer(), updatedObject))
                    info(file.name)
                    n++
                }
            } catch (e: Exception) {
                error(file.name, e)
            }
        }

        if (n != 0) divider()
        finish("minecraft:crafting_shaped", n)
        divider()
    }

    fun convertStringToObject(root: JsonObject, name: String, filed: String): JsonObject? {
        val oldResult = root[name]

        if (oldResult is JsonPrimitive && oldResult.isString) {
            val previousStringValue = oldResult.content

            val newResultObject = buildJsonObject {
                put(filed, previousStringValue)
            }

            return buildJsonObject {
                root.forEach { (key, value) ->
                    if (key == name) {
                        put(name, newResultObject)
                    } else {
                        put(key, value)
                    }
                }
            }
        }

        return null
    }

    fun renameKeyInObject(root: JsonObject, rootKey: String, oldKey: String, newKey: String): JsonObject? {
        val targetObject = root[rootKey]

        if (targetObject is JsonObject && targetObject.containsKey(oldKey)) {

            val updatedTargetObject = buildJsonObject {
                targetObject.forEach { (key, value) ->
                    if (key == oldKey) {
                        put(newKey, value)
                    } else {
                        put(key, value)
                    }
                }
            }

            return buildJsonObject {
                root.forEach { (key, value) ->
                    if (key == rootKey) {
                        put(rootKey, updatedTargetObject)
                    } else {
                        put(key, value)
                    }
                }
            }
        }

        return null
    }

    fun moveFieldIntoObject(root: JsonObject, targetKey: String, countKey: String): JsonObject {
        val targetObject = root[targetKey]
        val oldCount = root[countKey]?.jsonPrimitive?.intOrNull ?: 1

        if (targetObject is JsonObject) {
            val updatedTargetObject = buildJsonObject {
                targetObject.forEach { (key, value) -> put(key, value) }
                put(countKey, oldCount)
            }

            return buildJsonObject {
                root.forEach { (key, value) ->
                    when (key) {
                        targetKey -> put(targetKey, updatedTargetObject)
                        countKey -> { }
                        else -> put(key, value)
                    }
                }
            }
        }
        return root
    }

    fun info(name: String) {
        println(" [Info] Updated: $name")
    }

    fun error(name: String, e: Exception) {
        println(" [Error] Error while transforming $name: ${e.message}")
    }

    fun finish(type: String, n: Int) {
        println(" [Finished] Updated: $type, $n files")
    }

    fun divider() {
        println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    }

}
