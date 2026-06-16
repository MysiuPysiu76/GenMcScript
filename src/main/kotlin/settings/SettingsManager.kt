package settings

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.io.File

object SettingsManager {

    private val file = File(".gms.config.json")

    fun read(): Settings {
        return Json.decodeFromString(Settings.serializer(), file.readText())
    }

    fun save(key: String, value: Any) {
        val jsonFormat = Json { this.prettyPrint = true }

        val jsonObject: JsonObject = if (file.exists()) {
            val content = file.readText()
            val parsed = jsonFormat.parseToJsonElement(content)
            if (parsed is JsonObject) parsed else JsonObject(emptyMap())
        } else {
            JsonObject(emptyMap())
        }

        val parsedValue: JsonElement = JsonPrimitive(value.toString())

        val updatedMap = jsonObject.toMutableMap()
        updatedMap[key] = parsedValue
        val newJsonObject = JsonObject(updatedMap)

        file.writeText(jsonFormat.encodeToString(newJsonObject))
    }

    fun reset() {
        file.delete()
    }

}
