package utils

import kotlinx.serialization.json.*
import java.io.File

object Tags {

    private lateinit var res: String
    private lateinit var namespace: String

    fun init(res: String, namespace: String) {
        this.res = res;
        this.namespace = namespace;
    }

    fun append(name: String, value: String) {
        if (!res.endsWith("/")) res += "/"

        val (tagNamespace, tagName) = parseName(name)

        val file = File(res, "data/$tagNamespace/tags/$tagName.json").normalize()

        val json = Json.parseToJsonElement(file.readText()).jsonObject
        val values = json["values"]!!.jsonArray.map { it.jsonPrimitive.content }.toMutableList()

        values.add("$namespace:$value")
        values.sort()

        val newJson = buildJsonObject {
            json.forEach { (key, element) ->
                if (key == "values") {
                    put("values", JsonArray(values.map(::JsonPrimitive)))
                } else {
                    put(key, element)
                }
            }
        }

        file.writeText(Json { prettyPrint = true }.encodeToString(newJson))
    }

    private fun parseName(name: String): Pair<String, String> {
        return if (name.contains(":")) {
            val (namespace, name) = name.split(":", limit = 2)
            namespace to name
        } else {
            namespace to name
        }
    }
}
