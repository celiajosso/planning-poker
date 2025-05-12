// import io.ktor.application.*
// import io.ktor.features.ContentNegotiation
// import io.ktor.http.ContentType
// import io.ktor.http.HttpStatusCode
// import io.ktor.request.receiveMultipart
// import io.ktor.response.respond
// import io.ktor.routing.post
// import io.ktor.routing.routing
// import io.ktor.server.engine.embeddedServer
// import io.ktor.server.netty.Netty
// import io.ktor.util.KtorExperimentalAPI
// import kotlinx.serialization.json.Json
// import kotlinx.serialization.encodeToString
// import io.ktor.client.*
// import io.ktor.client.features.json.*
// import io.ktor.client.request.*
// import io.ktor.client.engine.cio.*
// import io.ktor.server.plugins.statuspages
// import java.io.File
// import com.github.kittinan.csv.CsvReader
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.withContext

// data class Issue(val title: String, val description: String, val score: String)

// @OptIn(KtorExperimentalAPI::class)
// fun main() {
//     val client = HttpClient(CIO) {
//         install(JsonFeature) {
//             serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
//         }
//     }

//     embeddedServer(Netty, port = 8080) {
//         install(ContentNegotiation)
//         install(statusPages)

//         routing {
//             post("/upload-csv") {
//                 val multipart = call.receiveMultipart()
//                 var issues: List<Issue> = emptyList()

//                 multipart.forEachPart { part ->
//                     if (part is io.ktor.http.content.PartData.FileItem) {
//                         // Sauvegarder temporairement le fichier CSV
//                         val file = File("uploads/${part.originalFileName}")
//                         part.streamProvider().use { inputStream ->
//                             file.outputStream().buffered().use { outputStream ->
//                                 inputStream.copyTo(outputStream)
//                             }
//                         }

//                         // Lire le fichier CSV
//                         issues = withContext(Dispatchers.IO) {
//                             CsvReader().read(file)
//                         }

//                         file.delete() // Supprimer le fichier temporaire après lecture
//                     }
//                 }

//                 // Importer les issues dans Jira
//                 issues.forEach { issue ->
//                     try {
//                         createIssueInJira(issue, client)
//                     } catch (e: Exception) {
//                         println("Error importing issue: $e")
//                     }
//                 }

//                 call.respond(HttpStatusCode.OK, "CSV processed and issues imported to Jira.")
//             }
//         }
//     }.start(wait = true)
// }

// // Fonction pour créer une issue dans Jira via l'API
// suspend fun createIssueInJira(issue: Issue, client: HttpClient) {
//     
//     try {
//         val response = client.post<String>(jiraUrl) {
//             header("Authorization", auth)
//             contentType(ContentType.Application.Json)
//             body = Json.encodeToString(mapOf(
//                 "fields" to mapOf(
//                     "project" to mapOf("key" to "SCRUM"),
//                     "summary" to issue.title,
//                     "description" to issue.description,
//                     "issuetype" to mapOf("name" to "Bug"),
//                     "customfield_12345" to issue.score // Remplace par ton champ personnalisé
//                 )
//             ))
//         }
//         println("Issue created: $response")
//     } catch (e: Exception) {
//         println("Error creating issue: $e")
//     }
// }











































































//end