package com.example.playground.kmm.shared.entity

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
    )

@Serializable
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
    )

@Serializable
data class Album(
    val userId: Int,
    val id: Int,
    val title: String
    )
