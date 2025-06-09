package com.example.models

import org.bson.codecs.pojo.annotations.BsonId

@kotlinx.serialization.Serializable
data class DB_User(
    var username: String,
    var password: String,
)