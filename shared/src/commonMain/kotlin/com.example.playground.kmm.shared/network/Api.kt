package com.example.playground.kmm.shared.network

import com.example.playground.kmm.shared.entity.Album
import com.example.playground.kmm.shared.entity.Comment
import com.example.playground.kmm.shared.entity.Post

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Api {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllPosts(): Array<Post> {
        return httpClient.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    suspend fun getAllComments(): Array<Comment> {
        return httpClient.get("https://jsonplaceholder.typicode.com/comments").body()
    }

    suspend fun getAllAlbums(): Array<Album> {
        return httpClient.get("https://jsonplaceholder.typicode.com/albums").body()
    }
}
