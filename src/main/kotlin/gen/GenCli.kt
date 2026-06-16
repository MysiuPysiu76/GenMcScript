package gen

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import settings.SettingsManager
import java.util.Locale

class GenCli : CliktCommand(name = "gen", help = "Generate files") {

    private val type by option("-t", "--type", help = "Enter type")
    private val material by option("-m", "--material", help = "Enter material")
    private val namespace by option("-n", "--namespace", help = "Enter namespace")
    private val pattern by option("-pt", "--pattern", help = "Enter pattern")
    private val path by option("-p", "--path", help = "Path to save generated files")
    private val autoplace by option("-a", "-ap", "--autoplace", help = "Set auto place")
    private val s by option("-ss", help = "Optional boolean flag used for specific block variants (e.g. brick slab from bricks)").flag(default = false)
    private val source by option("-s", "--source", help = "Set source for recipe")
    private val stonecutting by option("-sc", "-st", "--stonecutting", help = "Generate stonecutter recipe").flag(default = false)
    private val category by option("-c", "--category", help = "Set category for recipe")
    private val mcnamespace by option("-mc", "--mc-namespace", help = "Set namespace for something what come from minecraft").flag(default = false)

    override fun run() {

        val settings = ModelSettings(SettingsManager.read())
        type!!.lowercase(Locale.getDefault())

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

        var mcns : Boolean = mcnamespace;
        if (type?.contains("pumpkin")!! && material.isNullOrBlank()) {
            mcns = true
        }

        val typeString: String;
        if (type.equals("block_set")) {
            typeString = "block/slab/stairs/vertical_slab/wall".uppercase()
        } else {
            typeString= type.toString().uppercase()
        }

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
                    settings.source = if (source.isNullOrBlank()) "enter_item" else source.toString()
                    settings.category = if (category.isNullOrBlank()) "" else category.toString()
                    settings.mcnamespace = mcns

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