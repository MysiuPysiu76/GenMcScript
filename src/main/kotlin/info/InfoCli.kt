package info

import com.github.ajalt.clikt.core.CliktCommand
import settings.SettingsManager
import java.io.File

class InfoCli : CliktCommand(name = "info", help = "Read all information from your mod") {

    private val path = InfoReader.getProjectPath()
    private val resPath = InfoReader.getResPath()

    override fun run() {
        divider()

        println("General Info:")
        println(" Project Path: $path")
        println(" Top level Java Classes: ${InfoReader.classesTopLevelCount(path)}")
        println(" Java Code Lines: ${InfoReader.pretty(InfoReader.javaLines(path))}")
        println(" Json Code Lines: ${InfoReader.pretty(InfoReader.jsonLines(path))}")

        val namespace = SettingsManager.read().namespace

        println("Minecraft Info:")
        println(" Blocks: ${InfoReader.blocks(resPath, namespace)}")
        println(" Items: ${InfoReader.items(resPath, namespace)}")
        println(" Textures: ${InfoReader.textures(resPath, namespace)}")
        println(" Recipes: ${InfoReader.recipesCount(resPath, namespace)}")
        InfoReader.recipesByType(resPath, namespace).entries
            .sortedWith(
                compareBy<Map.Entry<String, Int>> { if (it.key.substringBefore(':') == "minecraft") 0 else 1 }
                    .thenByDescending { it.value }
            )
            .forEach { (type, count) ->
                println("   $type: $count")
            }

        divider()
    }

    fun divider() {
        println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    }
}
