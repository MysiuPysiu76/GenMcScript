
import com.github.ajalt.clikt.core.FileNotFound
import java.io.File

object FilesUtils {

    fun createDirs(s: ModelSettings) {
        getDir("blockstates", s).mkdirs()
        getDir("models/block", s).mkdirs()
        getDir("models/item", s).mkdirs()
        getDir("recipes", s).mkdirs()
        getDir("loot_tables", s).mkdirs()
    }

    fun getDir(name: String, path: String, namespace: String, autoplace: Boolean): File {
        return if (autoplace) mapOf(
            "blockstates" to File(path, "assets/$namespace/blockstates"),
            "models/block" to File(path, "assets/$namespace/models/block"),
            "models/item" to File(path, "assets/$namespace/models/item"),
            "recipes" to File(path, "data/$namespace/recipes"),
            "loot_tables" to File(path, "data/$namespace/loot_tables/blocks"),
        )[name]!!
        else mapOf(
            "blockstates" to File(path, "generated/blockstates"),
            "models/block" to File(path, "generated/models/block"),
            "models/item" to File(path, "generated/models/item"),
            "recipes" to File(path, "generated/recipes"),
            "loot_tables" to File(path, "generated/loot_tables/blocks"),
        )[name]!!
    }

    private fun getDir(name: String, s: ModelSettings): File {
        return getDir(name, s.path, s.namespace, s.autoplace)
    }

    fun readFile(filename: String): String {
        val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw FileNotFound("file not found: $filename")
        return stream.bufferedReader().use { it.readText() }
    }

}
