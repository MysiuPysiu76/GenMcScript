
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option

class SettingsCli : CliktCommand(name = "settings", help = "Settings") {

    val namespace by option("-n", "--namespace", help = "Set namespace")
    val path by option("-p", "--path", help = "Target directory where generated blockstate, model, recipe and loot_table JSON files will be written")

    override fun run() {

        if (namespace != null) {
            SettingsManager.save("namespace", namespace!!)
            return
        }

        if (path != null) {
            SettingsManager.save("path", path!!)
            return
        }

    }
}
