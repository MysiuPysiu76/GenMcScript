
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val namespace: String,
    val path: String
)
