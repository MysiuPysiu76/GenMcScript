
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import java.util.Locale.getDefault

class GenCli : CliktCommand(name = "gen", help = "Generate files") {

    private val type by option("-t", "--type", help = "Enter type")
    private val material by option("-m", "--material", help = "Enter material")
    private val namespace by option("-n", "--namespace", help = "Enter namespace")
    private val pattern by option("-pt", "--pattern", help = "Enter pattern")
    private val path by option("-p", "--path", help = "Path to save generated files")
    private val autoplace by option("-a", "-ap", "--autoplace", help = "Set auto place")

    override fun run() {

        val settings = ModelSettings(SettingsManager.read())

        if (type.isNullOrBlank()) {
            echo("Type is required", err = true)
            return
        }

        if (material.isNullOrBlank()) {
            echo("Material is required", err = true)
            return
        }

        settings.type = ModelType.valueOf(type.toString().uppercase(getDefault()))
        settings.material = material.toString()

        if (!path.isNullOrBlank()) settings.path = path.toString()

        if (!namespace.isNullOrBlank()) settings.namespace = namespace.toString()

        if (!pattern.isNullOrBlank()) settings.pattern = pattern.toString()
        else settings.pattern = ""

        if (!autoplace.isNullOrBlank()) settings.autoplace = autoplace.toString().toBoolean()

        Generator.generate(settings)

    }

}
