package update

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import settings.SettingsManager
import gen.ModelSettings
import java.io.File

class FileUpdater {

    fun recipes(type: String): List<File> {
        val settings = SettingsManager.read()
        val json = Json { ignoreUnknownKeys = true }

        val dirName = if (ModelSettings(settings).version <= 2) "recipes" else "recipe"

        val directoriesToScan = listOf(
            File(settings.path, "data/${settings.namespace}/$dirName"),
            File(settings.path, "data/minecraft/$dirName")
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

    fun v2() {
        println()
        divider()

        val json = json()
        var n = 0

        // Stonecutting: "ingredient": {"item": "X"} -> "ingredient": "X"
        recipes("minecraft:stonecutting").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = convertIngredientField(originalObject, "ingredient")

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
        n = 0

        // Smelting, Blasting, Smoking, Campfire: "ingredient": {"item": "X"} -> "ingredient": "X"
        for (type in listOf("minecraft:smelting", "minecraft:blasting", "minecraft:smoking", "minecraft:campfire_cooking")) {
            recipes(type).forEach { file ->
                try {
                    val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                    val updatedObject = convertIngredientField(originalObject, "ingredient")

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
            finish(type, n)
            divider()
            n = 0
        }

        // Crafting Shaped: "key": {"#": {"item": "X"}} -> "key": {"#": "X"}
        recipes("minecraft:crafting_shaped").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = convertKeyIngredients(originalObject, "key")

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
        n = 0

        // Crafting Shapeless: "ingredients": [{"item": "X"}] -> "ingredients": ["X"]
        recipes("minecraft:crafting_shapeless").forEach { file ->
            try {
                val originalObject = json.parseToJsonElement(file.readText()).jsonObject
                val updatedObject = convertIngredientsList(originalObject, "ingredients")

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
    }

    fun ingredientToString(ingredient: Any): Any {
        return when (ingredient) {
            is JsonObject -> {
                val item = ingredient["item"]
                val tag = ingredient["tag"]
                if (tag is JsonPrimitive && tag.isString) {
                    "#${tag.content}"
                } else if (item is JsonPrimitive && item.isString) {
                    item.content
                } else {
                    ingredient
                }
            }
            else -> ingredient
        }
    }

    fun convertIngredientField(root: JsonObject, fieldName: String): JsonObject? {
        val oldValue = root[fieldName] ?: return null

        if (oldValue is JsonObject) {
            val newValue = ingredientToString(oldValue)

            if (newValue is String) {
                return buildJsonObject {
                    root.forEach { (key, value) ->
                        if (key == fieldName) {
                            put(fieldName, newValue)
                        } else {
                            put(key, value)
                        }
                    }
                }
            }
        }

        return null
    }

    fun convertKeyIngredients(root: JsonObject, keyName: String): JsonObject? {
        val keyObject = root[keyName] as? JsonObject ?: return null

        var changed = false
        val newKeyObject = buildJsonObject {
            keyObject.forEach { (k, v) ->
                val converted = ingredientToString(v)
                if (converted is String) {
                    put(k, converted)
                    changed = true
                } else {
                    put(k, v)
                }
            }
        }

        if (!changed) return null

        return buildJsonObject {
            root.forEach { (key, value) ->
                if (key == keyName) {
                    put(keyName, newKeyObject)
                } else {
                    put(key, value)
                }
            }
        }
    }

    fun convertIngredientsList(root: JsonObject, listName: String): JsonObject? {
        val oldList = root[listName] as? JsonArray ?: return null

        var changed = false
        val newList = buildJsonArray {
            oldList.forEach { element ->
                val converted = ingredientToString(element)
                if (converted is String) {
                    add(JsonPrimitive(converted))
                    changed = true
                } else {
                    add(element)
                }
            }
        }

        if (!changed) return null

        return buildJsonObject {
            root.forEach { (key, value) ->
                if (key == listName) {
                    put(listName, newList)
                } else {
                    put(key, value)
                }
            }
        }
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
