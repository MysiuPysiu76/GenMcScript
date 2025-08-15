import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import java.io.File

class GenCli : CliktCommand(name = "gen", help = "Generate files") {

    private val type by option("-t", "--type", help = "Enter type")
    private val model by option("-m", "--model", help = "Enter model")
    private val namespaceVal by option("-n", "--namespace", help = "Enter namespace")
    private val secondName by option("-s", help = "Enter second param")

    override fun run() {

        if (type.isNullOrBlank()) {
            echo("Type is required", err = true)
            return
        }

        if (model.isNullOrBlank()) {
            echo("Model is required")
            return
        }

        if (namespaceVal.isNullOrBlank()) {
            echo("Namespace is required")
            return
        }

        val namespace = namespaceVal.toString()

        File("generated/blockstates").mkdirs()
        File("generated/models/block").mkdirs()
        File("generated/models/item").mkdirs()
        File("generated/recipes").mkdirs()
        File("generated/loot_tables").mkdirs()

        var name = model.toString()

        when (type) {
            "slab" -> {
                File("generated/blockstates/${name}_slab.json").writeText(readFile("/blockstates/slab.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/block/${name}_slab.json").writeText(readFile("/models/block/slab.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/block/${name}_slab_top.json").writeText(readFile("/models/block/slab_top.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/item/${name}_slab.json").writeText(readFile("/models/item/slab.json").replace("::", name).replace("namespace", namespace))
                File("generated/recipes/${name}_slab.json").writeText(readFile("/recipes/slab.json").replace("**", name).replace("namespace", namespace))
                File("generated/loot_tables/${name}_slab.json").writeText(readFile("/loot_tables/slab.json").replace("**", name).replace("namespace", namespace))
            }
            "stairs" -> {
                File("generated/blockstates/${name}_stairs.json").writeText(readFile("/blockstates/stairs.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/block/${name}_stairs.json").writeText(readFile("/models/block/stairs.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/block/${name}_stairs_inner.json").writeText(readFile("/models/block/stairs_inner.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/block/${name}_stairs_outer.json").writeText(readFile("/models/block/stairs_outer.json").replace("::", name).replace("namespace", namespace))
                File("generated/models/item/${name}_stairs.json").writeText(readFile("/models/item/stairs.json").replace("::", name).replace("namespace", namespace))
                File("generated/recipes/${name}_stairs.json").writeText(readFile("/recipes/stairs.json").replace("**", name).replace("namespace", namespace))
                File("generated/loot_tables/${name}_stairs.json").writeText(readFile("/loot_tables/stairs.json").replace("**", name).replace("namespace", namespace))
            }
            "carved_pumpkin" -> {
                if (name.equals(".")) name = ""

                if (secondName.isNullOrBlank()) {
                    echo("Second param is required")
                    return
                }

                val secondName = secondName.toString()
                File("generated/blockstates/${secondName}carved_${name}pumpkin.json").writeText(readFile("/blockstates/carved_pumpkin.json").replace("**", name).replace("namespace", namespace).replace("++", secondName))
                File("generated/models/block/${secondName}carved_${name}pumpkin.json").writeText(readFile("/models/block/carved_pumpkin.json").replace("**", name).replace("namespace", namespace).replace("++", secondName))
                File("generated/models/item/${secondName}carved_${name}pumpkin.json").writeText(readFile("/models/item/carved_pumpkin.json").replace("**", name).replace("namespace", namespace).replace("++", secondName))
                File("generated/loot_tables/${secondName}carved_${name}pumpkin.json").writeText(readFile("/loot_tables/carved_pumpkin.json").replace("**", name).replace("namespace", namespace).replace("++", secondName))
            }
            "lantern_pumpkin" -> {
                File("generated/blockstates/${name}_jack_o_lantern.json").writeText(readFile("/blockstates/jack_o_lantern.json").replace("**", name).replace("namespace", namespace))
                File("generated/models/block/${name}_jack_o_lantern.json").writeText(readFile("/models/block/jack_o_lantern.json").replace("**", name).replace("namespace", namespace))
                File("generated/models/item/${name}_jack_o_lantern.json").writeText(readFile("/models/item/jack_o_lantern.json").replace("**", name).replace("namespace", namespace))
                File("generated/loot_tables/${name}_jack_o_lantern.json").writeText(readFile("/loot_tables/jack_o_lantern.json").replace("**", name).replace("namespace", namespace))
                File("generated/recipes/${name}_jack_o_lantern.json").writeText(readFile("/recipes/jack_o_lantern.json").replace("**", name).replace("namespace", namespace))
            }
        }

    }

    fun readFile(filename: String): String {
        val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw IllegalArgumentException("Exception: File not found.")
        return stream.bufferedReader().use { it.readText() }
    }

}
