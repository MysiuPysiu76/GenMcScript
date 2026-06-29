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
        println("Minecraft Info:")
        println(" Blocks: ${InfoReader.blocks(resPath, SettingsManager.read().namespace)}")
        println(" Items: ${InfoReader.items(resPath, SettingsManager.read().namespace)}")
        println(" Recipes: ${InfoReader.recipesCount(resPath, SettingsManager.read().namespace)}")

        divider()
    }

    fun divider() {
        println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
    }
}
