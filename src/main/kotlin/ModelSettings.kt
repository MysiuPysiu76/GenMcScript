import kotlin.properties.Delegates

class ModelSettings(val settings : Settings) {

    var namespace : String = settings.namespace
    var path : String = settings.path
    var autoplace : Boolean = settings.autoplace

    lateinit var type : ModelType
    lateinit var material : String
    lateinit var pattern : String
    lateinit var source : String
    lateinit var category : String
    var s by Delegates.notNull<Boolean>()
    var stonecutting by Delegates.notNull<Boolean>()
    var mcnamespace by Delegates.notNull<Boolean>()

}
