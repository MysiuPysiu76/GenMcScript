package models

import utils.FilesUtils
import utils.Tags
import java.io.File
import java.util.Locale
import kotlin.collections.iterator
import kotlin.text.isNullOrBlank
import kotlin.text.isNullOrEmpty

class ModelGenerator(val settings: ModelSettings) {

    val binds: MutableMap<String, String> by lazy {
        var rootBlock = settings.material
        if (settings.s) rootBlock += "s"

        val secondNamespace = if (settings.mcnamespace) "minecraft" else settings.namespace
        val stripped = if (settings.material.contains("stripped")) "" else "stripped"
        val camp_type = if (settings.material.contains("soul")) "soul_fire_base_blocks" else "coals"

        mutableMapOf(
            "root_block" to rootBlock,
            "pattern_" to settings.pattern,
            "sns" to secondNamespace,
            "mt_" to if (secondNamespace.equals(settings.namespace)) settings.material + "_" else "",
            "recipe_source" to settings.source,
            "recipe_category" to if (!settings.category.isNullOrEmpty()) settings.category else "building",
            "head_type" to settings.type.toString().lowercase(Locale.getDefault()),
            "stp" to stripped,
            "is_souls" to camp_type
        )
    }

    fun block() {
        generate("blockstates", "block")
        generate("models/block", "block")
        if (settings.version > 4) {
            generate("items","block")
        } else {
            generate("models/item","block")
        }
        generate("loot_tables", "block")
    }

    fun column() {
        generate("blockstates", "column")
        generate("models/block", "column")
        if (settings.version > 4) {
            generate("items","block", true)
        } else {
            generate("models/item","block", true)
        }
        generate("loot_tables", "block")
    }

    fun orientable() {
        generate("blockstates", "orientable")
        generate("models/block", "orientable")
        if (settings.version > 4) {
            generate("items","block", true)
        } else {
            generate("models/item","block", true)
        }
        generate("loot_tables", "block")
    }

    fun blockBottomTop() {
        generate("blockstates", "block")
        generate("models/block", "block_bottom_top")
        if (settings.version > 4) {
            generate("items","block", true)
        } else {
            generate("models/item","block", true)
        }
        generate("loot_tables", "block")
    }

    fun slab() {
        generate("blockstates","slab", true)
        generate("models/block","slab", true)
        generate("models/block","slab_top", true)
        if (settings.version > 4) {
            generate("items","slab", true)
        } else {
            generate("models/item","slab", true)
        }
        generate("recipes","slab", true)
        if (settings.stonecutting) generate("recipes","slab_stonecutting", true)
        generate("loot_tables","slab", true)
        if (settings.isWood) {
            Tags.append("minecraft:block/wooden_slabs", "${settings.material}_slab")
        } else {
            Tags.append("minecraft:block/slabs", "${settings.material}_slab")
        }
    }

    fun verticalSlab() {
        generate("blockstates","vertical_slab", true)
        generate("models/block","vertical_slab", true)
        if (settings.version > 4) {
            generate("items","vertical_slab", true)
        } else {
            generate("models/item","vertical_slab", true)
        }
        generate("recipes","vertical_slab", true)
        if (settings.stonecutting) generate("recipes","vertical_slab_stonecutting", true)
        generate("loot_tables","vertical_slab", true)
        if (settings.isWood) Tags.append("block/wooden_vertical_slabs", "${settings.material}_vertical_slab")
        if (settings.isStone) Tags.append("block/stone_vertical_slabs", "${settings.material}_vertical_slab")
    }

    fun stairs() {
        generate("blockstates", "stairs", true)
        generate("models/block", "stairs", true)
        generate("models/block", "stairs_inner", true)
        generate("models/block", "stairs_outer", true)
        if (settings.version > 4) {
            generate("items","stairs", true)
        } else {
            generate("models/item","stairs", true)
        }
        generate("recipes", "stairs", true)
        if (settings.stonecutting) generate("recipes", "stairs_stonecutting", true)
        generate("loot_tables", "stairs", true)
        if (settings.isWood) {
            Tags.append("minecraft:block/wooden_stairs", "${settings.material}_stairs")
        } else {
            Tags.append("minecraft:block/stairs", "${settings.material}_stairs")
        }
    }

    fun wall() {
        generate("blockstates", "wall", true)
        generate("models/block", "wall_inventory", true)
        generate("models/block", "wall_post", true)
        generate("models/block", "wall_side", true)
        generate("models/block", "wall_side_tall", true)
        if (settings.version > 4) {
            generate("items","wall", true)
        } else {
            generate("models/item","wall", true)
        }
        generate("recipes", "wall", true)
        if (settings.stonecutting) generate("recipes", "wall_stonecutting", true)
        generate("loot_tables", "wall", true)
        Tags.append("minecraft:block/walls", "${settings.material}_wall")
    }

    fun fence() {
        generate("blockstates", "fence", true)
        generate("models/block", "fence_inventory", true)
        generate("models/block", "fence_post", true)
        generate("models/block", "fence_side", true)
        if (settings.version > 4) {
            generate("items","fence", true)
        } else {
            generate("models/item","fence", true)
        }
        generate("loot_tables", "fence", true)
        generate("recipes", "fence", true)
        Tags.append("minecraft:block/wooden_fences", "${settings.material}_fence")
    }

    fun button() {
        generate("blockstates", "button", true)
        generate("models/block", "button", true)
        generate("models/block", "button_inventory", true)
        generate("models/block", "button_pressed", true)
        if (settings.version > 4) {
            generate("items","button", true)
        } else {
            generate("models/item","button", true)
        }
        generate("loot_tables", "button", true)
        generate("recipes", "button", true)
    }

    fun pressurePlate() {
        generate("blockstates", "pressure_plate", true)
        generate("models/block", "pressure_plate", true)
        generate("models/block", "pressure_plate_down", true)
        if (settings.version > 4) {
            generate("items","pressure_plate", true)
        } else {
            generate("models/item","pressure_plate", true)
        }
        generate("loot_tables", "pressure_plate", true)
        generate("recipes", "pressure_plate", true)
    }

    fun pumpkinCarved() {
        val x = if (settings.material.isEmpty()) "" else "_"
        val name = "${settings.pattern}_carved_${settings.material}${x}pumpkin"
        settings.material = name

        if (settings.material.isBlank()) settings.mcnamespace = true

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
        Tags.append("block/jack_o_lanterns", "${settings.material}_jack_o_lantern")
    }

    fun bookshelf() {
        generate("blockstates", "bookshelf", true)
        generate("models/block", "bookshelf", true)
        if (settings.version > 4) {
            generate("items","bookshelf", true)
        } else {
            generate("models/item","bookshelf", true)
        }
        generate("loot_tables", "bookshelf", true)
        generate("recipes", "bookshelf", true)
        Tags.append("block/bookshelves", "${settings.material}_bookshelf")
    }

    fun campfire() {
        generate("blockstates", "campfire", true)
        generate("items", "campfire", true)
        generate("models/block", "campfire", true)
        generate("models/block", "campfire_off", true)
        generate("models/item", "campfire", true)
        generate("loot_tables", "campfire", true)
        generate("recipes", "campfire", true)
        if (settings.material.contains("soul")) {
            Tags.append("block/soul_campfires", "${settings.material}_campfire")
            Tags.append("item/soul_campfires", "${settings.material}_campfire")
        } else {
            Tags.append("block/normal_campfires", "${settings.material}_campfire")
            Tags.append("item/campfires", "${settings.material}_campfire")
        }
    }

    fun hollow() {
        generate("blockstates", "hollow_${settings.material}_log", "hollow")
        generate("models/block", "hollow_${settings.material}_log", "hollow")
        if (settings.version > 4) {
            generate("items","hollow_${settings.material}_log", "hollow")
        } else {
            generate("models/item","hollow_${settings.material}_log", "hollow")
        }
        generate("loot_tables", "hollow_${settings.material}_log", "hollow")
        generate("recipes", "hollow_${settings.material}_log", "hollow")
        Tags.append("block/hollow_logs", "hollow_${settings.material}_log")
    }

    fun chiseledBookshelf() {
        generate("blockstates", "chiseled_bookshelf", true)
        generate("models/block", "chiseled_bookshelf", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_bottom_left", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_bottom_mid", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_bottom_right", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_top_left", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_top_mid", true)
        generate("models/block", "chiseled_bookshelf_empty_slot_top_right", true)
        generate("models/block", "chiseled_bookshelf_inventory", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_bottom_left", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_bottom_mid", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_bottom_right", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_top_left", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_top_mid", true)
        generate("models/block", "chiseled_bookshelf_occupied_slot_top_right", true)
        if (settings.version > 4) {
            generate("items","chiseled_bookshelf", true)
        } else {
            generate("models/item","chiseled_bookshelf", true)
        }
        generate("loot_tables", "chiseled_bookshelf", true)
        generate("recipes", "chiseled_bookshelf", true)
        Tags.append("block/chiseled_bookshelves", "${settings.material}_chiseled_bookshelf")
    }

    fun ladder() {
        generate("blockstates", "ladder", true)
        generate("models/block", "ladder", true)
        if (settings.version > 4) {
            generate("items","ladder", true)
        }
        generate("models/item","ladder", true)
        generate("loot_tables", "ladder", true)
        generate("recipes", "ladder", true)
        Tags.append("block/wooden_ladders", "${settings.material}_ladder")
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
        generate("blockstates", "${settings.material}_skull", "head")
        generate("blockstates", "${settings.material}_wall_skull", "wall_head")
        generate("models/block", "${settings.material}_skull_0", "head_0")
        generate("models/block", "${settings.material}_skull_1", "head_1")
        generate("models/block", "${settings.material}_skull_2", "head_2")
        generate("models/block", "${settings.material}_skull_3", "head_3")
        generate("models/block", "${settings.material}_wall_skull", "wall_head")
        generate("models/block", "${settings.material}_skull_inventory", "head_inventory")
        generate("models/item", "${settings.material}_skull", "head")
        generate("loot_tables", "${settings.material}_skull", "head")
        generate("loot_tables", "${settings.material}_wall_skull", "wall_head")
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
        if (settings.material.contains("soul")) {
            Tags.append("block/soul_candles", "soul_${settings.material}_candle")
            Tags.append("item/soul_candles", "soul_${settings.material}_candle")
        } else {
            Tags.append("minecraft:block/candles", "${settings.material}_candle")
            Tags.append("minecraft:item/candles", "${settings.material}_candle")
        }
    }

    fun candleCake() {
        generate("blockstates", "${settings.pattern}_${settings.material}_candle_cake", "candle_cake")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_cake", "candle_cake")
        generate("models/block", "${settings.pattern}_${settings.material}_candle_cake_lit", "candle_cake_lit")
        generate("loot_tables", "${settings.pattern}_${settings.material}_candle_cake", "candle_cake")
        if (settings.material.contains("soul")) {
            Tags.append("block/soul_candle_cakes", "soul_${settings.material}_candle_cake")
        } else {
            Tags.append("minecraft:block/candle_cakes", "${settings.material}_candle_cake")
        }
    }

    fun plant() {
        generate("blockstates", "block")
        generate("items", "plant")
        generate("models/block", "plant")
        generate("models/item", "item_block")
        generate("loot_tables", "block")
        if (settings.material.contains("cactus")) Tags.append("block/cactus_flowers", settings.material)
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
        Tags.append("minecraft:block/tall_plants", "tall_${material}")
    }

    fun plantPot() {
        val material = "potted_${settings.material}"
        generate("blockstates", material, "potted_plant")
        generate("models/block", material, "potted_plant")
        generate("loot_tables", material, "potted_plant")
        Tags.append("minecraft:block/flower_pots", material)
    }

    fun plantBedPot() {
        val material = "potted_${settings.material}"
        generate("blockstates", material, "potted_plant")
        generate("models/block", material, "potted_plant_bed")
        generate("loot_tables", material, "potted_plant")
        Tags.append("minecraft:block/flower_pots", material)
    }

    fun item() {
        generate("models/item", "item")
        if (settings.version > 4) {
            generate("items","item")
        }
    }

    fun recipe() {
        generate("recipes", "recipe")
    }

    fun recipeShapeless() {
        generate("recipes", "recipe_shapeless")
    }

    fun recipeFurnace() {
        generate("recipes", "recipe_furnace")
    }

    fun recipeStonecutter() {
        generate("recipes", "recipe_stonecutter")
    }

    fun recipeWoodcutting() {
        generate("recipes", "woodcutting/tag")
    }

    fun woodSet() {
        val wood = settings.material
        val sns = if (settings.mcnamespace) "minecraft" else settings.namespace

        woodcutterRecipes(mapOf(
            "sns:{}_planks" to 4,
            "sns:{}_slab" to 8,
            "sns:{}_stairs" to 4,
            "namespace:{}_vertical_slab" to 8,

            "sns:{}_fence" to 4,
            "sns:{}_fence_gate" to 4,
            "sns:{}_door" to 4,
            "sns:{}_trapdoor" to 4,
            "sns:{}_button" to 4,
            "sns:{}_pressure_plate" to 4,
            "sns:{}_sign" to 4,
            "sns:{}_hanging_sign" to 4,

            "namespace:hollow_stripped_{}_log" to 1,
            "namespace:stripped_{}_wood_fence" to 2,
            "namespace:{}_ladder" to 4,

            "namespace:{}_mosaic" to 4,
            "namespace:{}_mosaic_slab" to 8,
            "namespace:{}_mosaic_stairs" to 4,
            "namespace:{}_mosaic_vertical_slab" to 8,

            "namespace:vertical_{}_planks" to 4,
            "namespace:vertical_{}_plank_slab" to 8,
            "namespace:vertical_{}_plank_stairs" to 4,
            "namespace:vertical_{}_plank_vertical_slab" to 8,
        ), "tag_logs", "logs")

        woodcutterRecipes(mapOf(
            "sns:stripped_{}_log" to 1,
            "sns:stripped_{}_wood" to 1,
            "namespace:hollow_{}_log" to 1,
            "namespace:{}_wood_fence" to 2,
        ), "tag_wood", "wood")

        woodcutterRecipes(mapOf(
            "sns:{}_slab" to 2,
            "sns:{}_stairs" to 1,
            "namespace:{}_vertical_slab" to 2,

            "sns:{}_fence" to 1,
            "sns:{}_fence_gate" to 1,
            "sns:{}_door" to 1,
            "sns:{}_trapdoor" to 1,
            "sns:{}_button" to 1,
            "sns:{}_pressure_plate" to 1,
            "sns:{}_sign" to 1,

            "namespace:{}_ladder" to 1,
            "namespace:{}_mosaic" to 1,
            "namespace:{}_mosaic_slab" to 2,
            "namespace:{}_mosaic_stairs" to 1,
            "namespace:{}_mosaic_vertical_slab" to 2,

            "namespace:vertical_{}_plank_slab" to 2,
            "namespace:vertical_{}_plank_stairs" to 1,
            "namespace:vertical_{}_plank_vertical_slab" to 2,
        ), "tag_planks", "planks")

        woodcutterRecipes(mapOf(
            "namespace:{}_mosaic_slab" to 2,
            "namespace:{}_mosaic_stairs" to 1,
            "namespace:{}_mosaic_vertical_slab" to 2,
        ), "item_mosaic", "mosaic")

        woodcutterRecipe("${wood}_wood_from_log", "${wood}_log", "${wood}_wood", 1, sns)
        woodcutterRecipe("stripped_${wood}_wood_from_stripped_log", "stripped_${wood}_log", "stripped_${wood}_wood", 1, sns)
        woodcutterRecipe("hollow_stripped_${wood}_log_from_hollow_log", "hollow_${wood}_log", "hollow_stripped_${wood}_log", 1, settings.namespace)
        woodcutterRecipe("${wood}_planks_from_vertical_planks", "vertical_${wood}_planks", "${wood}_planks", 1, settings.namespace, sns)
        woodcutterRecipe("vertical_${wood}_planks_from_planks", "${wood}_planks", "vertical_${wood}_planks", 1, sns, settings.namespace)
    }

    fun fire() {
        generate("blockstates", "fire", true)
        generate("models/block", "fire_floor0", true)
        generate("models/block", "fire_floor1", true)
        generate("models/block", "fire_side0", true)
        generate("models/block", "fire_side1", true)
        generate("models/block", "fire_side_alt0", true)
        generate("models/block", "fire_side_alt1", true)
    }

    private fun woodcutterRecipes(items: Map<String, Int>, source: String, from: String) {
        val wood = settings.material
        val sns = if (settings.mcnamespace) "minecraft" else settings.namespace
        binds["_wood_"] = wood

        for ((k, v) in items) {
            binds["_count_"] = v.toString()
            val newName = k.replace("{}", wood).replace("namespace", settings.namespace).replace("sns", sns)
            binds["item_name"] = newName
            generate("recipes", "${newName.substringAfter(':')}_from_$from", "woodcutting/$source")
        }
    }

    private fun woodcutterRecipe(fileName: String, from: String, to: String, count: Int, sns: String) {
       woodcutterRecipe(fileName, from, to, count, sns, sns)
    }

    private fun woodcutterRecipe(fileName: String, from: String, to: String, count: Int, sns: String, sns2: String) {
        binds["_count_"] = count.toString()
        binds["_from_"] = from
        binds["_from-ns_"] = sns
        binds["_to_"] = to
        binds["_to-ns_"] = sns2

        generate("recipes", fileName, "recipe_woodcutter")
    }

    fun recipeBlasting() {
        generate("recipes", "recipe_furnace")
        generate("recipes", "${settings.material}_blasting", "recipe_blasting")
    }

    fun recipeSmoking() {
        generate("recipes", "recipe_furnace")
        generate("recipes", "${settings.material}_smoking", "recipe_smoking")
    }

    fun recipeCampfire() {
        if (settings.category.isNullOrBlank()) settings.category = "food"
        generate("recipes", "recipe_furnace")
        generate("recipes", "${settings.material}_smoking", "recipe_smoking")
        generate("recipes", "${settings.material}_campfire","recipe_campfire")
    }

    private fun generate(model: String, source: String, onType: Boolean = false) {
        generate(model, settings.material, source, onType)
    }
    
    private fun generate(model: String, name: String, source: String, nameBasedOnType: Boolean = false) {
        val material = if (nameBasedOnType) "${settings.material}_${source}" else name
        val sourceFile = if (model.equals("recipes")) "v${settings.version}/$source" else source
        var content = FilesUtils.readFile("/${model}/${sourceFile}.json").replace("namespace", settings.namespace).replace("material", settings.material)
        for((k, v) in binds) content = content.replace(k, v)
        File(FilesUtils.getDir(model, settings.path, settings.namespace, settings.autoplace, settings.version), "${material}.json").writeText(content)
    }

}
