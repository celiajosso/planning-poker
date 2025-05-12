
// package com.example.export
// import Story
// // import Task
// import io.ktor.http.*
// import io.ktor.server.application.*
// import io.ktor.server.response.*
// import io.ktor.server.routing.* 


// fun exportStoriesToJiraCsv(stories: List<Story>): String {
//     val header = listOf("Summary", "Issue Type", "Description", "Estimate", "Parent ID")
//     val rows = mutableListOf<List<String>>()
    
//     for (story in stories) {
//         val storyId = story.id ?: story.title.hashCode().toString()  // Simple unique ID if null
//         rows.add(listOf(
//             story.title,
//             "Story",
//             story.description,
//             story.finalEstimate ?: "",
//             ""
//         ))
//         for (task in story.tasks) {
//             rows.add(listOf(
//                 task.title,
//                 "Sub-task",
//                 "",
//                 task.estimate ?: "",
//                 storyId
//             ))
//         }
//     }

//     val csvContent = (listOf(header) + rows).joinToString("\n") { it.joinToString(",") }
//     return csvContent
// }
