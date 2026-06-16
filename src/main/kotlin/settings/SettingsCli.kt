package settings

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class SettingsCli : CliktCommand(name = "settings", help = "Settings") {

    private val namespace by option("-n", "--namespace", help = "Set namespace")
    private val path by option("-p", "--path", help = "Target directory where generated blockstate, model, recipe and loot_table JSON files will be written")
    private val autoplace by option("-a", "-ap", "--autoplace", help = "Set auto place")
    private val reset by option("-r", "--reset", help = "Reset settings").flag()

    override fun run() {

        if (reset) {
            SettingsManager.reset()
            return
        }

        if (namespace != null) {
            SettingsManager.save("namespace", namespace!!)
        }

        if (path != null) {
            SettingsManager.save("path", path!!)
        }

        if (autoplace != null) {
            SettingsManager.save("autoplace", autoplace.toString().toBoolean())
        }

    }

}
