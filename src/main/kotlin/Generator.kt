
object Generator {

    fun generate(settings: ModelSettings) {
        FilesUtils.createDirs(settings)

        val gen = ModelGenerator(settings)

        when (settings.type) {
            ModelType.BLOCK -> gen.block()
            ModelType.COLUMN -> gen.column()
            ModelType.BLOCK_BOTTOM_TOP -> gen.blockBottomTop()
            ModelType.ORIENTABLE -> gen.orientable()

            ModelType.SLAB -> gen.slab()
            ModelType.STAIRS -> gen.stairs()
            ModelType.WALL -> gen.wall()

            ModelType.FENCE -> gen.fence()
            ModelType.BUTTON -> gen.button()
            ModelType.PRESSURE_PLATE -> gen.pressurePlate()

            ModelType.BOOKSHELF -> gen.bookshelf()

            ModelType.PLANT -> gen.plant()
            ModelType.PLANT_TALL -> gen.plantTall()
            ModelType.PLANT_POT -> gen.plantPot()

            ModelType.PUMPKIN_CARVED -> gen.pumpkinCarved()
            ModelType.PUMPKIN_JACK -> gen.pumpkinJackLantern()

            ModelType.HEAD -> gen.head()
            ModelType.SKULL -> gen.skull()

            ModelType.CANDLE -> gen.candle()

            ModelType.ITEM -> gen.item()
        }
    }

}
