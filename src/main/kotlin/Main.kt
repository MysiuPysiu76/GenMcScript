
import com.github.ajalt.clikt.core.subcommands
import java.io.File

fun main(args: Array<String>) {

    if (!args.isEmpty()) {
        GenMc().subcommands(SettingsCli()).main(args)
        return
    }

    val values = readLine() ?: ""
    val parts = values.trim().split(" ")

    val namespace = parts[0]
    val type = parts[1]
    val name = parts[2]

    File("generated/blockstates").mkdirs()
    File("generated/models/block").mkdirs()
    File("generated/models/item").mkdirs()
    File("generated/recipes").mkdirs()
    File("generated/loot_tables").mkdirs()

    if (type == "slab") {
        File("generated/blockstates/${name}_slab.json").writeText(readFile("/blockstates/slab.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/block/${name}_slab.json").writeText(readFile("/models/block/slab.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/block/${name}_slab_top.json").writeText(readFile("/models/block/slab_top.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/item/${name}_slab.json").writeText(readFile("/models/item/slab.json").replace("::", name).replace("namespace", namespace))
        File("generated/recipes/${name}_slab.json").writeText(readFile("/recipes/slab.json").replace("**", name).replace("namespace", namespace))
        File("generated/loot_tables/${name}_slab.json").writeText(readFile("/loot_tables/slab.json").replace("**", name).replace("namespace", namespace))
    } else {
        File("generated/blockstates/${name}_stairs.json").writeText(readFile("/blockstates/stairs.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/block/${name}_stairs.json").writeText(readFile("/models/block/stairs.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/block/${name}_stairs_inner.json").writeText(readFile("/models/block/stairs_inner.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/block/${name}_stairs_outer.json").writeText(readFile("/models/block/stairs_outer.json").replace("::", name).replace("namespace", namespace))
        File("generated/models/item/${name}_stairs.json").writeText(readFile("/models/item/stairs.json").replace("::", name).replace("namespace", namespace))
        File("generated/recipes/${name}_stairs.json").writeText(readFile("/recipes/stairs.json").replace("**", name).replace("namespace", namespace))
        File("generated/loot_tables/${name}_stairs.json").writeText(readFile("/loot_tables/stairs.json").replace("**", name).replace("namespace", namespace))
    }
}

fun readFile(filename: String): String {
    val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw IllegalArgumentException("Exception: File not found.")
    return stream.bufferedReader().use { it.readText() }
}
