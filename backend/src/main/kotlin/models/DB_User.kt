package com.example.models

@kotlinx.serialization.Serializable
data class DB_User(
    var username: String,
    var password: String,
)