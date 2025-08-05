
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option

class SettingsCli : CliktCommand(name = "settings", help = "Settings") {

    val namespace by option("-n", "--namespace", help = "Set namespace")

    override fun run() {

        if (namespace != null) {
            SettingsManager.save("namespace", namespace!!)
            return
        }

    }
}
