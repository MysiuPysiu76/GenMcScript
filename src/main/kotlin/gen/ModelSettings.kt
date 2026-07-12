package gen

import settings.Settings
import kotlin.properties.Delegates

class ModelSettings(settings : Settings) {

    var namespace : String = settings.namespace
    var path : String = settings.path
    var autoplace : Boolean = settings.autoplace
    var version : Int = version(settings.version)

    lateinit var type : ModelType
    lateinit var material : String
    lateinit var pattern : String
    lateinit var source : String
    lateinit var category : String
    var s by Delegates.notNull<Boolean>()
    var stonecutting by Delegates.notNull<Boolean>()
    var mcnamespace by Delegates.notNull<Boolean>()

    fun version(version : String): Int {
        return when (version) {
            "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4" -> 1
            "1.20.5", "1.20.6" -> 2
            "1.21", "1.21.1" -> 3
            "1.21.2", "1.21.3" -> 4
            else -> 4
        }
    }
}
