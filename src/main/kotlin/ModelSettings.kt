import kotlin.properties.Delegates

class ModelSettings(val settings : Settings) {

    var namespace : String = settings.namespace
    var path : String = settings.path
    var autoplace : Boolean = settings.autoplace

    lateinit var type : ModelType
    lateinit var material : String
    lateinit var pattern : String
    var s by Delegates.notNull<Boolean>()
    var stonecutting by Delegates.notNull<Boolean>()

}
