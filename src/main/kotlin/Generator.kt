
import com.github.ajalt.clikt.core.FileNotFound
import java.io.File

object Generator {

    var autoplace : Boolean = false
    var namespace : String = ""
    var path : String = ""

    fun generate(settings: ModelSettings) {
        autoplace = settings.autoplace
        namespace = settings.namespace
        path = settings.path

        createDirs()

        var name = settings.material

        when (settings.type) {
            ModelType.SLAB -> {
                File(getDir("blockstates"), "${name}_slab.json").writeText(readFile("/blockstates/slab.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"), "${name}_slab.json").writeText(readFile("/models/block/slab.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"), "${name}_slab_top.json").writeText(readFile("/models/block/slab_top.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"), "${name}_slab.json").writeText(readFile("/models/item/slab.json").replace("::", name).replace("namespace", namespace))
                File(getDir("recipes"), "${name}_slab.json").writeText(readFile("/recipes/slab.json").replace("**", name).replace("namespace", namespace))
                File(getDir("recipes"), "${name}_slab_stonecutting.json").writeText(readFile("/recipes/slab_stonecutting.json").replace("**", name).replace("namespace", namespace))
                File(getDir("loot_table"), "${name}_slab.json").writeText(readFile("/loot_tables/slab.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.STAIRS -> {
                File(getDir("blockstates"),"${name}_stairs.json").writeText(readFile("/blockstates/stairs.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_stairs.json").writeText(readFile("/models/block/stairs.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_stairs_inner.json").writeText(readFile("/models/block/stairs_inner.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_stairs_outer.json").writeText(readFile("/models/block/stairs_outer.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"),"${name}_stairs.json").writeText(readFile("/models/item/stairs.json").replace("::", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_stairs.json").writeText(readFile("/recipes/stairs.json").replace("**", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_stairs_stonecutting.json").writeText(readFile("/recipes/stairs_stonecutting.json").replace("**", name).replace("namespace", namespace))
                File(getDir("loot_table"),"${name}_stairs.json").writeText(readFile("/loot_tables/stairs.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.PUMPKIN_CARVED -> {
                var secondNamespace : String?
                var pattern = settings.pattern

                if (pattern.isEmpty()) {
                    secondNamespace = "minecraft"
                } else {
                    pattern += '_'
                    secondNamespace = namespace
                }

                File(getDir("blockstates"), "${name}_carved_${pattern}pumpkin.json").writeText(readFile("/blockstates/carved_pumpkin.json").replace("++", name).replace("namespace", namespace).replace("**", pattern))
                File(getDir("models/block"), "${name}_carved_${pattern}pumpkin.json").writeText(readFile("/models/block/carved_pumpkin.json").replace("++", name).replace("namespace", namespace).replace("**", pattern).replace("sns", secondNamespace))
                File(getDir("models/item"), "${name}_carved_${pattern}pumpkin.json").writeText(readFile("/models/item/carved_pumpkin.json").replace("++", name).replace("namespace", namespace).replace("**", pattern))
                File(getDir("loot_table"), "${name}_carved_${pattern}pumpkin.json").writeText(readFile("/loot_tables/carved_pumpkin.json").replace("++", name).replace("namespace", namespace).replace("**", pattern))
            }
            ModelType.PUMPKIN_JACK -> {
                var pattern = settings.pattern
                var secondNamespace : String?
                if (pattern.isEmpty()) {
                    secondNamespace = "minecraft"
                } else {
                    pattern += '_'
                    secondNamespace = namespace
                }

                File(getDir("blockstates"), "${name}_${pattern}jack_o_lantern.json").writeText(readFile("/blockstates/jack_o_lantern.json").replace("**", name).replace("namespace", namespace).replace("++", pattern))
                File(getDir("models/block"), "${name}_${pattern}jack_o_lantern.json").writeText(readFile("/models/block/jack_o_lantern.json").replace("**", name).replace("namespace", namespace).replace("++", pattern).replace("sns", secondNamespace))
                File(getDir("models/item"), "${name}_${pattern}jack_o_lantern.json").writeText(readFile("/models/item/jack_o_lantern.json").replace("**", name).replace("namespace", namespace).replace("++", pattern))
                File(getDir("loot_table"), "${name}_${pattern}jack_o_lantern.json").writeText(readFile("/loot_tables/jack_o_lantern.json").replace("**", name).replace("namespace", namespace).replace("++", pattern))
                File(getDir("recipes"), "${name}_${pattern}jack_o_lantern.json").writeText(readFile("/recipes/jack_o_lantern.json").replace("**", name).replace("namespace", namespace).replace("++", pattern))
            }
            ModelType.WALL -> {
                File(getDir("blockstates"),"${name}_wall.json").writeText(readFile("/blockstates/wall.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_wall_inventory.json").writeText(readFile("/models/block/wall_inventory.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_wall_post.json").writeText(readFile("/models/block/wall_post.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_wall_side.json").writeText(readFile("/models/block/wall_side.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_wall_side_tall.json").writeText(readFile("/models/block/wall_side_tall.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"),"${name}_wall.json").writeText(readFile("/models/item/wall.json").replace("::", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_wall.json").writeText(readFile("/recipes/wall.json").replace("**", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_wall_stonecutting.json").writeText(readFile("/recipes/wall_stonecutting.json").replace("**", name).replace("namespace", namespace))
                File(getDir("loot_table"),"${name}_wall.json").writeText(readFile("/loot_tables/wall.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.COLUMN -> {
                File(getDir("blockstates"), "${name}.json").writeText(readFile("/blockstates/column.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"), "${name}.json").writeText(readFile("/models/block/column.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"), "${name}.json").writeText(readFile("/models/item/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("loot_table"), "${name}.json").writeText(readFile("/loot_tables/block.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.BLOCK -> {
                File(getDir("blockstates"), "${name}.json").writeText(readFile("/blockstates/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"), "${name}.json").writeText(readFile("/models/block/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"), "${name}.json").writeText(readFile("/models/item/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("loot_table"), "${name}.json").writeText(readFile("/loot_tables/block.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.BOOKSHELF -> {
                File(getDir("blockstates"), "${name}_bookshelf.json").writeText(readFile("/blockstates/block.json").replace("::", name + "_bookshelf").replace("namespace", namespace))
                File(getDir("models/block"), "${name}_bookshelf.json").writeText(readFile("/models/block/bookshelf.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"), "${name}_bookshelf.json").writeText(readFile("/models/item/block.json").replace("::", name + "_bookshelf").replace("namespace", namespace))
                File(getDir("loot_table"), "${name}_bookshelf.json").writeText(readFile("/loot_tables/bookshelf.json").replace("**", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_bookshelf.json").writeText(readFile("/recipes/bookshelf.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.FENCE -> {
                File(getDir("blockstates"),"${name}_fence.json").writeText(readFile("/blockstates/fence.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_fence_inventory.json").writeText(readFile("/models/block/fence_inventory.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_fence_post.json").writeText(readFile("/models/block/fence_post.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_fence_side.json").writeText(readFile("/models/block/fence_side.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"),"${name}_fence.json").writeText(readFile("/models/item/fence.json").replace("::", name).replace("namespace", namespace))
                File(getDir("recipes"),"${name}_fence.json").writeText(readFile("/recipes/fence.json").replace("**", name).replace("namespace", namespace))
                File(getDir("loot_table"),"${name}_fence.json").writeText(readFile("/loot_tables/fence.json").replace("**", name).replace("namespace", namespace))
            }
            ModelType.HEAD -> {
                File(getDir("blockstates"),"${name}_head.json").writeText(readFile("/blockstates/head.json").replace("::", name).replace("namespace", namespace))
                File(getDir("blockstates"),"${name}_wall_head.json").writeText(readFile("/blockstates/wall_head.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"),"${name}_head_0.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "head_0"))
                File(getDir("models/block"),"${name}_head_1.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "head_1"))
                File(getDir("models/block"),"${name}_head_2.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "head_2"))
                File(getDir("models/block"),"${name}_head_3.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "head_3"))
                File(getDir("models/block"),"${name}_wall_head.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "wall_head"))
                File(getDir("models/block"),"${name}_head_inventory.json").writeText(readFile("/models/block/head.json").replace("::", name).replace("namespace", namespace).replace("++", "head_inventory"))
                File(getDir("models/item"),"${name}_head.json").writeText(readFile("/models/item/head.json").replace("::", name).replace("namespace", namespace))
                File(getDir("loot_table"),"${name}_head.json").writeText(readFile("/loot_tables/head.json").replace("**", name + "_head").replace("namespace", namespace).replace("==", name + "_head"))
                File(getDir("loot_table"),"${name}_wall_head.json").writeText(readFile("/loot_tables/head.json").replace("**", name + "_wall_head").replace("namespace", namespace).replace("==", name + "_head"))
            }
            ModelType.BLOCK_BOTTOM_TOP -> {
                File(getDir("blockstates"), "${name}.json").writeText(readFile("/blockstates/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/block"), "${name}.json").writeText(readFile("/models/block/block_bottom_top.json").replace("::", name).replace("namespace", namespace))
                File(getDir("models/item"), "${name}.json").writeText(readFile("/models/item/block.json").replace("::", name).replace("namespace", namespace))
                File(getDir("loot_table"), "${name}.json").writeText(readFile("/loot_tables/block.json").replace("**", name).replace("namespace", namespace))
            }
        }

    }

    fun createDirs() {

        getDir("blockstates").mkdirs()
        getDir("models/block").mkdirs()
        getDir("models/item").mkdirs()
        getDir("recipes").mkdirs()
        getDir("loot_table").mkdirs()

    }

    fun getDir(name: String): File {

        val namespace = File.separator + namespace + File.separator

        return if (autoplace) mapOf(
            "blockstates" to File(path, "assets/$namespace/blockstates"),
            "models/block" to File(path, "assets/$namespace/models/block"),
            "models/item" to File(path, "assets/$namespace/models/item"),
            "recipes" to File(path, "data/$namespace/recipes"),
            "loot_table" to File(path, "data/$namespace/loot_tables/blocks"),
        )[name]!!
        else mapOf(
            "blockstates" to File(path, "generated/blockstates"),
            "models/block" to File(path, "generated/models/block"),
            "models/item" to File(path, "generated/models/item"),
            "recipes" to File(path, "generated/recipes"),
            "loot_table" to File(path, "generated/loot_tables/blocks"),
        )[name]!!

    }

    fun readFile(filename: String): String {
        val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw FileNotFound("file not found: $filename")
        return stream.bufferedReader().use { it.readText() }
    }

}
