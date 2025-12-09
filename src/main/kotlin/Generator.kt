
object Generator {

    fun generate(settings: ModelSettings) {
        FilesUtils.createDirs(settings)

        val gen = ModelGenerator(settings)

        when (settings.type) {
            ModelType.BLOCK -> gen.block()
            ModelType.COLUMN -> gen.column()
            ModelType.BLOCK_BOTTOM_TOP -> gen.blockBottomTop()

            ModelType.SLAB -> gen.slab()
            ModelType.STAIRS -> gen.stairs()
            ModelType.WALL -> gen.wall()

            ModelType.FENCE -> gen.fence()
            ModelType.BUTTON -> gen.button()

            ModelType.PUMPKIN_CARVED -> gen.pumpkinCarved()
            ModelType.PUMPKIN_JACK -> gen.pumpkinJackLantern()
            ModelType.BOOKSHELF -> gen.bookshelf()
            ModelType.HEAD -> gen.head()
        }
    }

}
