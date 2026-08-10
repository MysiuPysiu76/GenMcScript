package update

object Updater {

    fun update(version: Int) {

        val updater = FileUpdater()

        when (version) {
            1 -> updater.v1()
            2 -> updater.v2()
            3 -> updater.v3()
        }
    }

}
