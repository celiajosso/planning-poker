package com.example.models

import org.bson.codecs.pojo.annotations.BsonId


data class DB_User(
    @BsonId
    val id: String,
    var username: String,
    var password: String,
)