package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class DB_Story(
    var title: String,
    var description: String,
    var participants: List<String>,
    var votes: HashMap<String, List<Int>>,
    var timestamp: Int,
)