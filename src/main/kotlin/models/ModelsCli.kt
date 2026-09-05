package models

import com.github.ajalt.clikt.core.CliktCommand

class ModelsCli : CliktCommand(name = "models", help = "List Available models") {

    override fun run() {
        printCategory("Blocks", ModelType.BLOCK, ModelType.COLUMN, ModelType.BLOCK_BOTTOM_TOP, ModelType.ORIENTABLE)
        printCategory("Variants", ModelType.SLAB, ModelType.VERTICAL_SLAB, ModelType.STAIRS, ModelType.WALL, ModelType.FENCE, ModelType.BUTTON, ModelType.PRESSURE_PLATE, ModelType.BOOKSHELF, ModelType.CHISELED_BOOKSHELF, ModelType.LADDER, ModelType.HOLLOW, ModelType.CAMPFIRE)
        printCategory("Recipes", ModelType.RECIPE, ModelType.RECIPE_SHAPELESS, ModelType.RECIPE_FURNACE, ModelType.RECIPE_STONECUTTER, ModelType.RECIPE_WOODCUTTING, ModelType.WOOD_SET, ModelType.RECIPE_SMOKING, ModelType.RECIPE_BLASTING, ModelType.RECIPE_CAMPFIRE)
        printCategory("Plants", ModelType.PLANT, ModelType.PLANT_TALL, ModelType.PLANT_POT, ModelType.PLANT_BED_POT)
        printCategory("Other", ModelType.FIRE, ModelType.PUMPKIN_CARVED, ModelType.PUMPKIN_JACK, ModelType.ITEM, ModelType.CANDLE, ModelType.CANDLE_CAKE, ModelType.HEAD, ModelType.SKULL,)
    }

    private fun printCategory(name: String, vararg types: ModelType) {
        println("[$name]:")
        types.forEach { print(it.name.lowercase() + " ") }
        println()
    }

}
