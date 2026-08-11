
import com.github.ajalt.clikt.core.FileNotFound
import gen.ModelSettings
import java.io.File

object FilesUtils {

    fun createDirs(s: ModelSettings) {
        val isS = if (s.version <= 2 ) "s" else ""
        getDir("blockstates", s).mkdirs()
        getDir("models/block", s).mkdirs()
        getDir("models/item", s).mkdirs()
        getDir("recipe$isS", s).mkdirs()
        getDir("loot_table$isS", s).mkdirs()
    }

    fun getDir(name: String, path: String, namespace: String, autoplace: Boolean, version: Int): File {
        val isS = if (version <= 2 ) "s" else ""
        val recipeDir = if (autoplace) "data/$namespace/recipe$isS" else "generated/recipe$isS"
        val lootDir = if (autoplace) "data/$namespace/loot_table$isS/blocks" else "generated/loot_table$isS/blocks"
        return if (autoplace) mapOf(
            "blockstates" to File(path, "assets/$namespace/blockstates"),
            "models/block" to File(path, "assets/$namespace/models/block"),
            "models/item" to File(path, "assets/$namespace/models/item"),
            "items" to File(path, "assets/$namespace/items"),
            "recipe$isS" to File(path, recipeDir),
            "recipes" to File(path, recipeDir),
            "loot_table$isS" to File(path, lootDir),
            "loot_tables" to File(path, lootDir),
        )[name]!!
        else mapOf(
            "blockstates" to File(path, "generated/blockstates"),
            "models/block" to File(path, "generated/models/block"),
            "models/item" to File(path, "generated/models/item"),
            "items" to File(path, "generated/items"),
            "recipe$isS" to File(path, recipeDir),
            "recipes" to File(path, recipeDir),
            "loot_table$isS" to File(path, lootDir),
            "loot_tables" to File(path, lootDir),
        )[name]!!
    }

    private fun getDir(name: String, s: ModelSettings): File {
        return getDir(name, s.path, s.namespace, s.autoplace, s.version)
    }

    fun readFile(filename: String): String {
        val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw FileNotFound("file not found: $filename")
        return stream.bufferedReader().use { it.readText() }
    }

}
