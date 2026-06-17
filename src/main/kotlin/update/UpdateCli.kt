package update

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option

class UpdateCli : CliktCommand(name = "update", help = "Update game data and assets") {

    private val version by option("-v", help = "Updater version")

    override fun run() {

        if (version != null) {
            Updater.update(version!!.toInt())
        } else {
            echo("Updater Version is required")
        }

    }

}
