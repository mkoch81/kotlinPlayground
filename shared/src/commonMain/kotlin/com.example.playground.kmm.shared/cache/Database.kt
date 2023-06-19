package com.example.playground.kmm.shared.cache

import com.example.playground.kmm.shared.entity.AppDatabase
import com.example.playground.kmm.shared.entity.Post

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllPosts()
        }
    }

    internal fun getAllPosts(): List<Post> {
        return dbQuery.selectAllPosts(::mapPostSelecting).executeAsList()
    }

    private fun mapPostSelecting(
        userId: Long,
        id: Long,
        title: String,
        body: String
    ): Post {
        return Post(
            userId = userId.toInt(),
            id = id.toInt(),
            title = title,
            body = body
        )
    }

    internal fun createPosts(posts: List<Post>) {
        dbQuery.transaction {
            posts.forEach { post ->
                insertPost(post)
            }
        }
    }

    private fun insertPost(post: Post) {
        dbQuery.insertPost(
            userId = post.userId.toLong(),
            id = post.id.toLong(),
            title = post.title,
            body = post.body
        )
    }
}