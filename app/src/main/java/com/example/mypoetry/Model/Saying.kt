package com.example.mypoetry.Model

data class Saying(
    val code: Int,
    val `data`: SayingData,
    val log_id: Long,
    val msg: String,
    val time: Int
)

data class SayingData(
    val album: String,
    val author: String,
    val description: String,
    val hot_comment: List<HotComment>,
    val image: String,
    val published_date: String,
    val song_id: Int,
    val title: String
)

data class HotComment(
    val avatar_url: String,
    val comment_id: Int,
    val content: String,
    val liked_count: Int,
    val nickname: String,
    val published_date: String,
    val song_id: Int,
    val user_id: Int
)