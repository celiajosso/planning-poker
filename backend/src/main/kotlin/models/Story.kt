data class Story(
    val id: String? = null,
    val title: String,
    val description: String,
    val roomId: String,
    val votes: List<Vote> = listOf(),
    val finalEstimate: String? = null
)

data class Vote(
    val userId: String,
    val value: String
)
