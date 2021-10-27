package com.example.mypoetry.Model

data class Poet(
    val `data`: Data,
    val ipAddress: String,
    val status: String,
    val token: String,
    val warning: Any
)

data class Data(
    val cacheAt: String,
    val content: String,
    val id: String,
    val matchTags: List<String>,
    val origin: Origin,
    val popularity: Int,
    val recommendedReason: String
)

data class Origin(
    val author: String,
    val content: List<String>,
    val dynasty: String,
    val title: String,
    val translate: List<String>
)