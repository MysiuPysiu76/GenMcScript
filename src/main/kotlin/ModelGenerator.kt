
import java.io.File
import java.util.Locale.getDefault

class ModelGenerator(val settings: ModelSettings) {

    lateinit var binds: Map<String, String>

    init {
        initBinds()
    }

    private fun initBinds() {
        var rootBlock = settings.material
        if (settings.s) rootBlock += "s"

        val secondNamespace = if (rootBlock.isEmpty()) "minecraft" else settings.namespace

        binds = mapOf(
            "root_block" to rootBlock,
            "pattern_" to settings.pattern,
            "sns" to secondNamespace,
            "mt_" to if (secondNamespace.equals(settings.namespace)) settings.material + "_" else "",
            "head_type" to settings.type.toString().lowercase(getDefault())
        )
    }

    fun block() {
        generate("blockstates", "block")
        generate("models/block", "block")
        generate("models/item", "block")
        generate("loot_tables", "block")
    }

    fun column() {
        generate("blockstates", "column")
        generate("models/block", "column")
        generate("models/item", "block")
        generate("loot_tables", "block")
    }

    fun orientable() {
        generate("blockstates", "orientable")
        generate("models/block", "orientable")
        generate("models/item", "block")
        generate("loot_tables", "block")
    }

    fun blockBottomTop() {
        generate("blockstates", "block")
        generate("models/block", "block_bottom_top")
        generate("models/item", "block")
        generate("loot_tables", "block")
    }

    fun slab() {
        generate("blockstates","slab", true)
        generate("models/block","slab", true)
        generate("models/block","slab_top", true)
        generate("models/item","slab", true)
        generate("recipes","slab", true)
        if (settings.stonecutting) generate("recipes","slab_stonecutting", true)
        generate("loot_tables","slab", true)
    }

    fun stairs() {
        generate("blockstates", "stairs", true)
        generate("models/block", "stairs", true)
        generate("models/block", "stairs_inner", true)
        generate("models/block", "stairs_outer", true)
        generate("models/item", "stairs", true)
        generate("recipes", "stairs", true)
        if (settings.stonecutting) generate("recipes", "stairs_stonecutting", true)
        generate("loot_tables", "stairs", true)
    }

    fun wall() {
        generate("blockstates", "wall", true)
        generate("models/block", "wall_inventory", true)
        generate("models/block", "wall_post", true)
        generate("models/block", "wall_side", true)
        generate("models/block", "wall_side_tall", true)
        generate("models/item", "wall", true)
        generate("recipes", "wall", true)
        if (settings.stonecutting) generate("recipes", "wall_stonecutting", true)
        generate("loot_tables", "wall", true)
    }

    fun fence() {
        generate("blockstates", "fence", true)
        generate("models/block", "fence_inventory", true)
        generate("models/block", "fence_post", true)
        generate("models/block", "fence_side", true)
        generate("models/item", "fence", true)
        generate("loot_tables", "fence", true)
        generate("recipes", "fence", true)
    }

    fun button() {
        generate("blockstates", "button", true)
        generate("models/block", "button", true)
        generate("models/block", "button_inventory", true)
        generate("models/block", "button_pressed", true)
        generate("models/item", "button", true)
        generate("loot_tables", "button", true)
        generate("recipes", "button", true)
    }

    fun pumpkinCarved() {
        val x = if (settings.material.isEmpty()) "" else "_"
        val name = "${settings.pattern}_carved_${settings.material}${x}pumpkin"
        settings.material = name

        generate("blockstates", "orientable")
        generate("models/block", "pumpkin")
        generate("models/item", "block")
        generate("loot_tables", "block")
    }

    fun pumpkinJackLantern() {
        val x = if (settings.material.isEmpty()) "" else "_"
        val name = "${settings.pattern}_${settings.material}${x}jack_o_lantern"
        settings.material = name

        generate("blockstates", "orientable")
        generate("models/block", "pumpkin")
        generate("models/item", "block")
        generate("loot_tables", "block")
        generate("recipes", "jack_o_lantern")
    }

    fun bookshelf() {
        generate("blockstates", "bookshelf", true)
        generate("models/block", "bookshelf", true)
        generate("models/item", "bookshelf", true)
        generate("loot_tables", "bookshelf", true)
        generate("recipes", "bookshelf", true)
    }

    fun head() {
        generate("blockstates", "head", true)
        generate("blockstates", "wall_head", true)
        generate("models/block", "head_0", true)
        generate("models/block", "head_1", true)
        generate("models/block", "head_2", true)
        generate("models/block", "head_3", true)
        generate("models/block", "wall_head", true)
        generate("models/block", "head_inventory", true)
        generate("models/item", "head", true)
        generate("loot_tables", "head", true)
        generate("loot_tables", "wall_head", true)
    }

    fun skull() {
        generate("blockstates", "${settings.material}_skull", "head", false)
        generate("blockstates", "${settings.material}_wall_skull", "wall_head", false)
        generate("models/block", "${settings.material}_skull_0", "head_0", false)
        generate("models/block", "${settings.material}_skull_1", "head_1", false)
        generate("models/block", "${settings.material}_skull_2", "head_2", false)
        generate("models/block", "${settings.material}_skull_3", "head_3", false)
        generate("models/block", "${settings.material}_wall_skull", "wall_head", false)
        generate("models/block", "${settings.material}_skull_inventory", "head_inventory", false)
        generate("models/item", "${settings.material}_skull", "head", false)
        generate("loot_tables", "${settings.material}_skull", "head", false)
        generate("loot_tables", "${settings.material}_wall_skull", "wall_head", false)
    }

    fun candle() {
        generate("blockstates", "${settings.pattern}_${settings.material}_candle", "candle")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_one_candle", "candle_one_candle")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_one_candle_lit", "candle_one_candle_lit")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_two_candles", "candle_two_candles")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_two_candles_lit", "candle_two_candles_lit")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_three_candles", "candle_three_candles")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_three_candles_lit", "candle_three_candles_lit")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_four_candles", "candle_four_candles")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_four_candles_lit", "candle_four_candles_lit")
        generate("models/item", "${settings.pattern}_${settings.material}_candle", "candle")
        generate("loot_tables", "${settings.pattern}_${settings.material}_candle", "candle")
    }

    fun plant() {
        generate("blockstates", "block")
        generate("models/block", "plant")
        generate("models/item", "item_block")
        generate("loot_tables", "block")
    }

    fun plantTall() {
        val material = settings.material
        generate("blockstates", "tall_${settings.material}", "plant_tall")
        generate("loot_tables", "tall_${settings.material}", "plant_tall")
        settings.material = "tall_${material}_bottom"
        generate("models/block", "tall_${material}_bottom", "plant")
        settings.material = "tall_${material}_top"
        generate("models/block", "tall_${material}_top", "plant")
        generate("models/item", "tall_${material}", "item_block")
    }

    fun item() {
        generate("models/item", "item")
    }

    private fun generate(model: String, source: String, onType: Boolean = false) {
        generate(model, settings.material, source, onType)
    }

    private fun generate(model: String, name: String, source: String, nameBasedOnType: Boolean = false) {
        val material = if (nameBasedOnType) "${settings.material}_${source}" else name
        var content = FilesUtils.readFile("/${model}/${source}.json").replace("namespace", settings.namespace).replace("material", settings.material)
        for((k, v) in binds) content = content.replace(k, v)
        File(FilesUtils.getDir(model, settings.path, settings.namespace, settings.autoplace), "${material}.json").writeText(content)
    }

}
