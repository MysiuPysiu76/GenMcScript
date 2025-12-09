
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import java.util.Locale.getDefault

class GenCli : CliktCommand(name = "gen", help = "Generate files") {

    private val type by option("-t", "--type", help = "Enter type")
    private val material by option("-m", "--material", help = "Enter material")
    private val namespace by option("-n", "--namespace", help = "Enter namespace")
    private val pattern by option("-pt", "--pattern", help = "Enter pattern")
    private val path by option("-p", "--path", help = "Path to save generated files")
    private val autoplace by option("-a", "-ap", "--autoplace", help = "Set auto place")
    private val s by option("-s", help = "Set s").flag(default = false)
    private val stonecutting by option("-sc", "--stonecutting", help = "Generate stonecutter recipe").flag(default = false)

    override fun run() {

        val settings = ModelSettings(SettingsManager.read())

        if (type.isNullOrBlank()) {
            echo("Type is required", err = true)
            return
        }

        if (material.isNullOrBlank() && !type?.contains("pumpkin")!!) {
            if (material.isNullOrBlank()) {
                echo("Material is required", err = true)
                return
            }
        }

        val typeString = type.toString().uppercase(getDefault())

        for (type in typeString.split('/')) {
            val isMaterialEmpty = material.isNullOrBlank()
            val iterator = if (isMaterialEmpty) "null" else material.toString()

            for (material in iterator.split('/')) {
                val isPatternEmpty = pattern.isNullOrBlank()
                val patternIterator = if (isPatternEmpty) "null" else pattern.toString()

                for (pattern in patternIterator.split('/')) {
                    settings.type = ModelType.valueOf(type)
                    settings.material = if (isMaterialEmpty) "" else material
                    settings.s = s
                    settings.stonecutting = stonecutting

                    if (!path.isNullOrBlank()) settings.path = path.toString()

                    if (!namespace.isNullOrBlank()) settings.namespace = namespace.toString()

                    if (!isPatternEmpty) settings.pattern = pattern
                    else settings.pattern = ""

                    if (!autoplace.isNullOrBlank()) settings.autoplace = autoplace.toString().toBoolean()

                    Generator.generate(settings)
                }
            }
        }
    }

}
