data class Room(
    val id: String? = null,
    val name: String,
    val users: List<String> = listOf(),
    val stories: List<String> = listOf()
)
